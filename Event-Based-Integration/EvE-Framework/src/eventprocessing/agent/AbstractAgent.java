package eventprocessing.agent;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.spark.util.CollectionAccumulator;

import eventprocessing.agent.dispatch.Dispatcher;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.state.NotInitializedState;
import eventprocessing.agent.state.ReadyState;
import eventprocessing.agent.state.State;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.consume.kafka.ConsumerSettingsDefaultValues;
import eventprocessing.consume.kafka.runner.logging.state.CountSendState;
import eventprocessing.consume.spark.streaming.SparkContextValues;
import eventprocessing.consume.spark.streaming.window.AbstractWindow;
import eventprocessing.consume.spark.streaming.window.NoValidWindowSettingsException;
import eventprocessing.consume.spark.streaming.window.Window;
import eventprocessing.event.AbstractEvent;
import eventprocessing.produce.kafka.Despatcher;
import eventprocessing.produce.kafka.ProducerSettings;
import eventprocessing.produce.kafka.ProducerSettingsDefaultValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.mapping.MessageMapper;
import eventprocessing.utils.model.ModelUtils;
import eventprocessing.utils.SystemUtils;
import eventprocessing.utils.TextUtils;

/**
 * Der AbstractAgent beinhaltet die Realisierung der Member, die für alle
 * Agenten benötigt werden. Agenten die von dieser Klasse erben, müssen die
 * abstract run und init Methode realisieren.
 * 
 * 
 * @author IngoT
 *
 */
public abstract class AbstractAgent implements Serializable {

	private static final long serialVersionUID = 200553511808766459L;
	protected static Logger LOGGER = LoggerFactory.getLogger(AbstractAgent.class.getName());
	// private static AbstractFactory eventFactory =
	// FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());

	/**
	 * Die ID wird bei der Initialisierung erzeugt
	 */
	private String id = null;
	/*
	 * Der MessageMapper formatiert die Events in ein JSON-String, damit diese an
	 * die Topics geleitet werden können.
	 */
	protected MessageMapper messageMapper = null;
	// Für den Versand der Nachrichten zuständig
	protected Despatcher despatcher = null;
	// Verteilt die eingehenden Nachrichten an die InterestProfiles
	private Dispatcher dispatcher = new Dispatcher();
	// Die Liste hält alle InterestProfiles vor
	private transient List<AbstractInterestProfile> interestProfiles = new ArrayList<AbstractInterestProfile>();
	// Vorhaltung aller Topics, die konsumiert werden sollen
	private transient Collection<String> subscribedTopics = new HashSet<String>();
	// ConsumerSettings für den Aufbau des DStreams
	private transient ConsumerSettings consumerSettings = null;
	// Benötigt der Dispatcher, um die Nachrichten an die Topics senden zu können
	private ProducerSettings producerSettings = null;
	// repräsentiert den Status des Agenten
	protected State state = null;
	/*
	 * der accumulator hält die Events vor, die von dem Agenten für die spätere
	 * Verarbeitung benötigt wird. volatile wird benötigt, damit die Informationen
	 * von den Executor/Slaves an den Driver/Master gesendet werden kann.
	 */
	private volatile CollectionAccumulator<AbstractEvent> accumulator = null;

	/*
	 * Nachdem die Filterung des DStreams vorgenommen wurde, kann anhand eines
	 * Window die Betrachtung der Events auf der zeitlichen Achse betrachtet werden.
	 * Hier wird nicht die Eventzeit berücksichtigt, sondern die Systemzeit.
	 */
	protected transient AbstractWindow window = null;

	/**
	 * Bei der Erzeugung ist der Startzustand: "Not Initialized" Dieser Zustand wird
	 * in "Ready" überführt, nachdem der Agent die .init()-Methode ausgeführt hat.
	 */
	public AbstractAgent() {
		this.state = new NotInitializedState();
	}

	/**
	 * gibt die Id des Agenten zurück
	 * 
	 * @return die Id
	 */
	public String getId() {
		return id;

	}

	/**
	 * setzt die Id für den Agenten
	 * 
	 * @param id
	 */
	public void setId(String id) {
		if (!TextUtils.isNullOrEmpty(id)) {
			this.id = id;
		}
	}

	/**
	 * Gibt den MessageMapper des Agenten zurück
	 * 
	 * @return messageMapper, für die Übersetzung von Nachrichten und Events
	 */
	public MessageMapper getMessageMapper() {
		return this.messageMapper;
	}

	/**
	 * Gibt den Dispatcher zurück
	 * 
	 * @return Dispatcher
	 */
	public Dispatcher getDispatcher() {
		return this.dispatcher;
	}

	/**
	 * Gibt den Dispatcher zurück
	 * 
	 * @return dispatcher
	 */
	public Despatcher getDespatcher() {
		return this.despatcher;
	}

	/**
	 * Gibt alle InterestProfiles zurück.
	 * 
	 * @return interestProfiles, eine Liste mit allen InterestProfiles
	 */
	public List<AbstractInterestProfile> getInterestProfiles() {
		return this.interestProfiles;
	}

	/**
	 * Fügt der Liste der InterestProfiles ein weiteres InterestProfile hinzu, das
	 * ebenfalls dem Dispatcher übergeben wird.
	 * 
	 * @param interestProfile,
	 *            welches der Liste hinzugefügt werden soll.
	 * @throws NoValidInterestProfileException,
	 *             wenn das InterestProfile null ist oder keine gültigen Predicate
	 *             enthält
	 */
	public void add(AbstractInterestProfile interestProfile) throws NoValidInterestProfileException {
		if (interestProfile != null && !(interestProfile.getPredicates().isEmpty())) {
			LOGGER.log(Level.FINE, () -> String.format("try to add: %s", interestProfile));
			/*
			 * Fügt das Interessenprofil dem Dispatcher hinzu sowie der eigenen Liste (ggf
			 * nicht nötig)
			 */
			this.dispatcher.add(interestProfile);
			this.interestProfiles.add(interestProfile);
			interestProfile.setAgent(this);
		} else
			throw new NoValidInterestProfileException(
					"The committed InterestProfile doesn't contain any predicates. The InterestProfile will not be added to the agent");
	}

	/**
	 * Gibt alle abonnierten Topics zurück.
	 */
	public Collection<String> getSubscribedTopics() {
		return this.subscribedTopics;
	}

	/**
	 * Dem Zieltopic wird automatisch die Partition 0 zugewiesen zum Beschreiben.
	 * 
	 * @param targetTopic,
	 *            Name des Zieltopics
	 * @throws NoValidConsumingTopicException
	 */
	public void add(String subscribedTopic) throws NoValidConsumingTopicException {
		if (!TextUtils.isNullOrEmpty(subscribedTopic)) {
			this.subscribedTopics.add(subscribedTopic);
		} else {
			throw new NoValidConsumingTopicException("The stated topic name is null or empty. It will be ignored.");
		}
	}

	/**
	 * Gibt die aktuellen Verbindungsinformationen für den DStream zurück
	 * 
	 * @return ConsumerSettings mit den Verbindungsinformationen
	 */
	public ConsumerSettings getConsumerSettings() {
		return this.consumerSettings;
	}

	/**
	 * Setzt die Konfiguration für den DStream.
	 * 
	 * @param consumerSettings
	 */
	public void setConsumerSettings(ConsumerSettings consumerSettings) {
		if (consumerSettings != null) {
			this.consumerSettings = consumerSettings;
		}
	}

	/**
	 * Gibt die aktuellen Verbindungsinformationen für den Versand von Nachrichten
	 * zurück
	 * 
	 * @return ProducerSettings mit den Verbindungsinformationen
	 */
	public ProducerSettings getProducerSettings() {
		return this.producerSettings;
	}

	/**
	 * Setzt die Konfiguration für den Versand von Nachrichten über den
	 * <code>EventProducer</code>
	 * 
	 * @param producerSettings
	 */
	public void setProducerSettings(ProducerSettings producerSettings) {
		if (producerSettings != null) {
			this.producerSettings = producerSettings;
		}
	}

	/**
	 * Gibt den aktuellen Zustand des Agenten zurück
	 * 
	 * @return state, aktueller Zustand
	 */
	public State getState() {
		return this.state;
	}

	/**
	 * Versetzt den Agenten in einen neuen Zustand
	 * 
	 * @param state,
	 *            neuer Zustand
	 */
	public void setState(State state) {
		if (state != null) {
			this.state = state;
		}
	}

	/**
	 * Gibt den Accumulator des Agenten zurück.
	 * 
	 * @return accum, mit allen Events
	 */
	public CollectionAccumulator<AbstractEvent> getAccum() {
		return this.accumulator;
	}

	/**
	 * Setzt den Accumulator für den Agenten.
	 * 
	 * @param accumulator
	 */
	public void setAccum(CollectionAccumulator<AbstractEvent> accumulator) {
		this.accumulator = accumulator;
	}

	/**
	 * Gibt das Window für die DStream-Verarbeitung des Agenten zurück
	 * 
	 * @return window
	 */
	public AbstractWindow getWindow() {
		return this.window;
	}

	/**
	 * Setzt für den Agenten ein Fenster für die DStream-Verarbeitung. Ein Fenster
	 * besitzt eine Länge, dass alle Events in diesem Zeitrahmen vorhält und für die
	 * Verarbeitung bereithält.
	 * 
	 * @param window
	 */
	public void setWindow(AbstractWindow window) {
		if (window != null) {
			this.window = window;
		}
	}

	/**
	 * Wenn keine ConsumerSettings gesetzt wurden, werden Default-Werte gesetzt. Die
	 * Default-Werte befinden sich in <code>ConsumerSettingsDefaultValues</code>.
	 * 
	 * @return ConsumerSettings, mit den Default-Werten
	 */
	private ConsumerSettings createDefaultConsumerSettings() {
		ConsumerSettings csettings = new ConsumerSettings();
		csettings.add(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ConsumerSettingsDefaultValues.INSTANCE.getIPv4Bootstrap()
				+ ":" + ConsumerSettingsDefaultValues.INSTANCE.getPortBootstrap());
		csettings.add(ConsumerConfig.GROUP_ID_CONFIG, ConsumerSettingsDefaultValues.INSTANCE.getGroupID());
		csettings.add(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				ConsumerSettingsDefaultValues.INSTANCE.getKeyDeserializer());
		csettings.add(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				ConsumerSettingsDefaultValues.INSTANCE.getValueDeserializer());
		csettings.add(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG,
				ConsumerSettingsDefaultValues.INSTANCE.getPartitionAssignmentStrategy());
		return csettings;
	}

	/**
	 * Wenn keine ProducerSettings gesetzt wurden, werden Default-Werte gesetzt. Die
	 * Default-Werte befinden sich in <code>ProducerSettingsValues</code>.
	 * 
	 * @return ProducerSettings, mit den Default-Werten
	 */
	private ProducerSettings createDefaultProducerSettings() {
		ProducerSettings pSettings = new ProducerSettings();
		pSettings.add(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ProducerSettingsDefaultValues.INSTANCE.getIPv4Bootstrap()
				+ ":" + ProducerSettingsDefaultValues.INSTANCE.getPortBootstrap());
		pSettings.add(ProducerConfig.ACKS_CONFIG, ProducerSettingsDefaultValues.INSTANCE.getAcks());
		pSettings.add(ProducerConfig.BATCH_SIZE_CONFIG, ProducerSettingsDefaultValues.INSTANCE.getBatchSize());
		pSettings.add(ProducerConfig.LINGER_MS_CONFIG, ProducerSettingsDefaultValues.INSTANCE.getLingerMS());
		pSettings.add(ProducerConfig.RETRIES_CONFIG, ProducerSettingsDefaultValues.INSTANCE.getRetries());
		pSettings.add(ProducerConfig.BUFFER_MEMORY_CONFIG, ProducerSettingsDefaultValues.INSTANCE.getBufferMemory());
		pSettings.add(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				ProducerSettingsDefaultValues.INSTANCE.getKeySerializer());
		pSettings.add(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				ProducerSettingsDefaultValues.INSTANCE.getValueSerializer());
		return pSettings;
	}

	/**
	 * Wird bei der Initialisierung des Agenten ausgeführt. Beinhaltet Code der für
	 * alle Agenten beim Start ausgeführt werden soll. Bisher wird die Registry
	 * eingebunden, um diese nach einer ID zu fragen.
	 * 
	 * @throws AgentException
	 */
	public final void init() throws AgentException {
		// Der Code der Subklassen wird ausgeführt
		doOnInit();
		if (consumerSettings == null) {
			ConsumerSettings csettings = createDefaultConsumerSettings();
			this.setConsumerSettings(csettings);
			// Logger Message
		}

		if (producerSettings == null) {
			ProducerSettings pSettings = createDefaultProducerSettings();
			this.setProducerSettings(pSettings);
		}
				
		despatcher = new Despatcher(producerSettings);
		messageMapper = new MessageMapper();

		/**
		 * Wenn kein Windows angelegt wurde, wird ein Default-Window gesetzt. Der
		 * Default-Wert orientiert sich an der batch duration des SparkContext.
		 */
		if (window == null) {
			try {
				this.window = new Window(SparkContextValues.INSTANCE.getBatchDuration());
			} catch (NoValidWindowSettingsException e2) {
				LOGGER.log(Level.WARNING, () -> String.format("failed to set window for %s%sException: %s",
						this.getId(), SystemUtils.getFileSeparator(), e2));
				throw new AgentException("no window was set");
			}
		}

		// Registriert den Agenten im Netzwerk.
		// AnnounceAgent();
		// KafkaClient client = new KafkaClient(consumerSettings);
		// this.getSubscribedTopics().forEach(topic -> {
		// client.createTopic(topic);
		// });

		this.state = new ReadyState();

	}

	/**
	 * Anweisungen die bei der Initialisierung ausgeführt werden sollen. Die
	 * Initialisierung erfolgt nachdem der Agent der <code>StreamExecution</code>
	 * hinzugefügt wurde
	 */
	protected abstract void doOnInit();

	/**
	 * Sendet das Event an die Zieltopics. Dazu wird das Event in ein JSON-String
	 * umgewandelt und anschließend an den Despatcher übergeben mit der Information,
	 * auf welche Topics geschrieben werden soll
	 * 
	 * @param event,
	 *            das zu sendende Event
	 * @param topic,
	 *            Topic auf das das Event geschrieben werden soll.
	 * @throws NoValidEventException,
	 *             wenn dem Event kein type zugewiesen wurde, wird die Exception
	 *             geworfen.
	 */
	public void send(AbstractEvent event, String topic)
			throws NoValidEventException, NoValidTargetTopicException {
		send(event, topic, null);
	}

	/**
	 * Sendet das Event an die Zieltopics. Dazu wird das Event in ein JSON-String
	 * umgewandelt und anschließend an den Despatcher übergeben mit der Information,
	 * auf welche Topics geschrieben werden soll
	 * 
	 * @param event,
	 *            das zu sendende Event
	 * @param topic,
	 *            Topic auf das das Event geschrieben werden soll
	 * @param partition,
	 *            Partition des Topics, auf das geschrieben werden soll
	 * @throws NoValidEventException,
	 *             wenn dem Event kein type zugewiesen wurde, wird die Exception
	 *             geworfen.
	 */
	public void send(AbstractEvent event, String topic, Integer partition)
			throws NoValidEventException, NoValidTargetTopicException {
		if (event != null) {
			if (this.getState() instanceof CountSendState) {
				((CountSendState) this.getState()).add(event);
			}
			// Der EventMapper erzeugt aus dem Event ein JSON-Object

			if (!TextUtils.isNullOrEmpty(topic)) {
				event.setSource(topic);
				// Aus dem Event wird ein JSON-String erzeugt
				String messageAsJSON = messageMapper.toJSON(event);
				// Der JSON-String sowie die Zieltopics werden übergeben.
				despatcher.deliver(messageAsJSON, topic, partition);
			} else {
				throw new NoValidTargetTopicException(String.format("the stated target is invalid: %s", topic));
			}
		} else {
			throw new NoValidEventException(
					String.format("The committed event hasn't a proper type defined: %s", event));
		}
	}

	// /**
	// * Erzeugt das <code>createAnnouncementInterestProfile</code>
	// Interessenprofil.
	// * Es verarbeitet die Antworten, die durch ein <code>AnnouncementEvent</code>
	// * ausgelöst wurden.
	// *
	// * @throws NoValidInterestProfileException,
	// * wenn das Interessenprofil null ist oder keine Predikate erzeugt
	// * werden konnten.
	// */
	// private void createAnnouncementInterestProfile(AnnouncementEvent event) {
	// try {
	// AnnouncementInterestProfile ip = new AnnouncementInterestProfile();
	// ip.add(new IsEventType("CommentEvent"));
	// this.add(ip);
	// ip.init(event);
	// } catch (NoValidInterestProfileException e) {
	// LOGGER.log(Level.WARNING, () -> String.format(e.getMessage()));
	// }
	// }
	//
	// /**
	// * Erzeugt das <code>CommentInterestProfile</code> Interessenprofil. Es
	// * verarbeitet die Anfragen nach neuen IDs, die von den anderen Agenten aus
	// dem
	// * Netzwerk gesendet werden.
	// *
	// * @throws NoValidInterestProfileException,
	// * wenn das Interessenprofil null ist oder keine Predikate erzeugt
	// * werden konnten.
	// */
	// private void createCommentInterestProfile(AnnouncementEvent event) {
	// try {
	// NameInterestProfile ip = new CommentInterestProfile();
	// ip.add(new IsEventType("AnnouncementEvent"));
	// ip.add(new HasProperty("conversationId"));
	// ip.add(new Not(new HasPropertyContains("conversationId",
	// event.getConversationId())));
	// this.add(ip);
	// } catch (NoValidInterestProfileException | NullPredicateException e) {
	// LOGGER.log(Level.WARNING, () -> String.format(e.getMessage()));
	// }
	//
	// }
	//
	// /**
	// * Der Agent meldet sich im Netzwerk an. Er erzeugt eine neue ID und teilt
	// sich
	// * den anderen Agenten im Netzwerk mit. Sollte die erzeugte ID unique sein und
	// * kein Agent im Netzwerk entsprechend zu Wort melden, wird die erzeugte ID
	// * beibehalten, ansonsten wird eine neue ID erzeugt und erneut nachgefragt.
	// */
	// private void AnnounceAgent() {
	// // Agent meldet sich im Netzwerk an
	// try {
	// AnnouncementEvent event = (AnnouncementEvent) eventFactory
	// .createEvent(FactoryValues.INSTANCE.getAnnouncementEvent());
	// // Fügt das Topic NegotationName hinzu
	// this.add(ConversationValues.INSTANCE.getConversationTopic());
	// createAnnouncementInterestProfile(event);
	// createCommentInterestProfile(event);
	// } catch (NoValidConsumingTopicException e) {
	// LOGGER.log(Level.WARNING, () -> String.format("%s ", e));
	// }
	// }
	//

	/**
	 * alle relevanten Felder für equals() und hashCode() Methode
	 * 
	 * @return Object[] mit allen relevanten Feldern.
	 */
	private Object[] getSignificantFields() {
		return new Object[] { this.getId(), this.getInterestProfiles(), this.getSubscribedTopics() };
	}

	@Override
	public int hashCode() {
		return ModelUtils.hashCodeFor(getSignificantFields());
	}

	@Override
	public boolean equals(Object obj) {
		Boolean result = ModelUtils.quickEquals(this, obj);
		if (result == null) {
			AbstractAgent that = (AbstractAgent) obj;
			result = ModelUtils.equalsFor(this.getSignificantFields(), that.getSignificantFields());
		}
		return result;
	}

	@Override
	public String toString() {
		return ModelUtils.toStringFor(this);
	}

	/**
	 * Throws CloneNotSupportedException as a Event can not be meaningfully cloned.
	 * Construct a new Event instead.
	 *
	 * @throws CloneNotSupportedException
	 *             always
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}