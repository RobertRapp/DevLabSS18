package eventprocessing.consume.kafka.runner;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.state.NoStateActionSupportException;
import eventprocessing.agent.state.NotInitializedState;
import eventprocessing.agent.state.ReadyState;
import eventprocessing.consume.kafka.runner.logging.LoggingValues;
import eventprocessing.consume.kafka.runner.logging.state.CountReceiveState;
import eventprocessing.consume.kafka.runner.logging.state.CountSendState;
import eventprocessing.consume.spark.streaming.AgentRegistry;
import eventprocessing.consume.spark.streaming.StreamingExecution;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.JsonUtils;
import eventprocessing.utils.SystemUtils;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.mapping.MessageMapper;
import eventprocessing.utils.model.EventUtils;

/**
 * der KafkaConsumer wird von den Agenten verwendet, um neben der
 * Streamingverarbeitung eine Verbindung zu den Topics aufzubauen, welche für
 * die Konfiguration relevant sind (z.B. Logging).
 * 
 * Die zusätzliche TCP-Verbindung ist nebenläufig und tangiert nicht die
 * Verarbeitungslogik der Events.
 * 
 * Der Vorteil ist der, dass keine <code>AbstractInterestProfile</code> benötigt
 * werden, die zusätzliche Ressourcen in der Streamverarbeitung bedeuten würden.
 * 
 * @author IngoT
 *
 */
public final class KafkaConsumerRunner implements Runnable {

	private static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerRunner.class.getName());
	/**
	 * Zustand der Instanz. ist sie aktiv oder nicht. Die Flag wird auf true
	 * gesetzt, wenn die Instanz, also der Thread, beendet wird.
	 */
	private final AtomicBoolean closed = new AtomicBoolean(false);
	/**
	 * der KafkaConsumerbaut eine TCP-Verbindung zu einem Broker auf. Über diesen
	 * consumer werden die Nachrichten empfangen.
	 */
	private final KafkaConsumer<String, String> consumer;
	/**
	 * Angabe in Millisekunden, wie lange auf ein IO Event gewartet werden soll
	 */
	private final int timeout = 10000;

	/**
	 * Für die Konvertierung von Messages in Events.
	 */
	private final MessageMapper messageMapper = new MessageMapper();

	/*
	 * Für die Erzeugung der Interessenprofile
	 */
	private final AbstractFactory interestProfileFactory = FactoryProducer
			.getFactory(FactoryValues.INSTANCE.getInterestProfileFactory());

	/**
	 * Der default-Konstruktor erzeugt einen neuen KafkaConsumer auf Basis der
	 * ConsumerProperties. Bei jedem Neustart des DStreams wird der bisherige
	 * KafkaConsumer geschlossen und nachdem der DSstream neugestartet wurde, wird
	 * ein neuer KafkaConsumer erstellt. Dieser Schritt ist nötig, weil bei der
	 * Abschaltung des <code>StreamingContext</code> die Verbindung automatisch
	 * abgebrochen wird.
	 */
	public KafkaConsumerRunner() {
		// Consumer wird initialisiert
		this.consumer = new KafkaConsumer<String, String>(new KafkaConsumerProperties().getProperties());
	}

	/**
	 * die auszuführende Logik, wenn der KafkaConsumer ausgeführt wird. Es wird die
	 * Verbindung zu dem Broker aufgebaut und die empfangenen Nachrichten
	 * verarbeitet.
	 */
	public void run() {
		try {
			// Topics die abonniert werden
			ConsumerRecords<String, String> records;
			synchronized (consumer) {
				consumer.subscribe(Arrays.asList(// ConversationValues.INSTANCE.getConversationTopic(),
						LoggingValues.INSTANCE.getLoggingTopic()));
			}
			// solange der KafkaConsumer nicht geschlossen wird.
			while (!closed.get()) {
				// ConsumerRecords<String, String> beinhaltet die Metadaten sowie die Nachricht
				synchronized (consumer) {
					records = consumer.poll(timeout);
				}
				// Für jeden Datensatz
				records.forEach(record -> {
					// Prüfung ob es sich um valides JSON handelt
					if (JsonUtils.isValidJson(record.value())) {
						// Umwandlung von JSON in ein Event

						AbstractEvent event = messageMapper.toEvent(record.value());
						// Prüfung ob der aktuelle Agent für den Befehl vorgesehen ist
						if (EventUtils.isType("CommandEvent", event)) {
							Property<?> property = EventUtils.findPropertyByKey(event, "AgentName");

							if (property != null) {
								AbstractAgent agent = AgentRegistry.INSTANCE.getRegistry().get(property.getValue());
								if (agent != null) {
									command(agent, event);

								}
							}
						}
					}
				});
			}
		} catch (WakeupException e) {
			// Ignore exception if closing
			if (!closed.get())
				throw e;
			// Nachdem die flag auf true gesetzt wird, wird der KafkaConsumer geschlossen.
		} finally {
			consumer.close();
		}
	}

	/**
	 * Das übergebene <code>CommandEvent</code> wird auf den Agenten angewandt. Über
	 * diesen Weg kann dem Agenten Befehle erteilt werden wie beispielsweise auf
	 * neue Topics zu hören. Ebenfalls kann er in neue <code>State</code> versetzt
	 * werden.
	 * 
	 * @param agent,
	 *            auf den der Befehl ausgeführt werden soll
	 * @param cEvent,
	 *            Event mit dem Befehl der auf dem Agenten ausgeführt werden soll
	 * @throws InterruptedException
	 */

	private void command(AbstractAgent agent, AbstractEvent event) {

		// Prüfung ob der Agent überhaupt initialisiert wurde
		if (!(agent.getState() instanceof NotInitializedState)) {
			// Auswertung des Befehls

			@SuppressWarnings("unchecked")
			Property<String> property = (Property<String>) EventUtils.findPropertyByKey(event, "Command");

			if (property != null) {
				switch (property.getValue()) {
				// Stoppt die momentan ausführende Aktion
				case "STOP":
					// Nur wenn er sich nicht im ReadyState befindet
					if (!(agent.getState() instanceof ReadyState)) {
						try {
							LOGGER.log(Level.FINEST, () -> String.format("Stop Action: %s", agent.getState()));
							// Aktion wird abgeschlossen
							agent.getState().stopAction();
						} catch (NoStateActionSupportException e) {
							LOGGER.log(Level.WARNING,
									() -> String.format("can't stop action%sCommand: %s%sState: %s",
											SystemUtils.getLineSeparator(), property.getValue(),
											SystemUtils.getLineSeparator(), agent.getState()));
						}
					}
					break;
				// Zählt alle empfangenen Events
				case "COUNT_RECEIVE":
					// Muss sich im Zustand Ready befinden.
					if (agent.getState() instanceof ReadyState) {
						CountReceiveState cr = new CountReceiveState();
						LOGGER.log(Level.FINEST, () -> String.format("Change State to: %s", cr));
						cr.doAction(agent, event);
					}
					break;
				// Zählt alle gesendeten Events
				case "COUNT_SEND":
					// Muss sich im Zustand Ready befinden
					if (agent.getState() instanceof ReadyState) {
						CountSendState cs = new CountSendState();
						LOGGER.log(Level.FINEST, () -> String.format("Change State to: %s", cs));
						cs.doAction(agent, event);
						break;
					}
				case "ADD_INTEREST_PROFILE":
					@SuppressWarnings("unchecked")
					Property<String> propInterestProfile = (Property<String>) EventUtils.findPropertyByKey(event,
							"InterestProfile");

					if (propInterestProfile != null) {
						AbstractInterestProfile interestProfile = interestProfileFactory
								.createInterestProfile(propInterestProfile.getValue());
						try {
							agent.add(interestProfile);
						} catch (NoValidInterestProfileException e) {
							LOGGER.log(Level.WARNING,
									() -> String.format("No valid InterestProfile: %s", interestProfile));
						}
						StreamingExecution.reset();
					}

					break;
				case "ADD_TOPIC":
					@SuppressWarnings("unchecked")
					Property<String> propTopic = (Property<String>) EventUtils.findPropertyByKey(event, "Topic");

					if (propTopic != null) {
						try {
							agent.add(propTopic.getValue());
						} catch (NoValidConsumingTopicException e) {
							LOGGER.log(Level.WARNING, () -> String.format("No valid Topic: %s", propTopic.getValue()));
						}
						StreamingExecution.reset();
					}
					break;

				// Bei unbekannten Befehlen wird nur geloggt.
				default:
					LOGGER.log(Level.WARNING, () -> String.format("unknown command: %s%sNo action taken",
							property.getValue(), SystemUtils.getLineSeparator()));
					break;
				}

			}
		}
	}

	/**
	 * Über diese Methode kann der KafkaConsumer heruntergefahren werden. Die Flag
	 * wird auf true gesetzt, wodurch der Thread ausläuft und ordnungsgemäß beendet
	 * wird. Über die Methode wakeup() wird der KafkaConsumer beendet.
	 */
	public void shutdown() {
		closed.set(true);
		consumer.wakeup();
	}

}