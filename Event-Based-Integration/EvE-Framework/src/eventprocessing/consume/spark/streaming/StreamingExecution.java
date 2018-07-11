package eventprocessing.consume.spark.streaming;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.apache.spark.util.CollectionAccumulator;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.AgentException;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.consume.kafka.runner.KafkaConsumerRunner;
import eventprocessing.consume.spark.functions.ExtractMessage;
import eventprocessing.consume.spark.functions.IsJson;
import eventprocessing.consume.spark.functions.IsMessageOfInterest;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.SystemUtils;
import eventprocessing.utils.factory.LoggerFactory;

/**
 * Diese Klasse enthält die Verarbeitungslogik der eingehenden DStreams. Für
 * jeden angelegten Agenten wird ein DStream erzeugt und entsprechend
 * verarbeitet.
 * 
 * @author IngoT
 *
 */
public final class StreamingExecution {

	private static transient Logger LOGGER = LoggerFactory.getLogger(StreamingExecution.class.getName());
	// Alle Agenten, die im Streamingverarbeitungsprozess berücksichtigt werden.
	private final static transient JavaSparkContext jsc = new JavaSparkContext(
			SparkContextConfig.INSTANCE.getSparkConfig());
	private static transient JavaStreamingContext jssc = null;

	/*
	 * Baut einen zusätzliches Kanal zu Kakfa auf. Dieser Kanal dient dem Empfang
	 * von Befehlen für den Agenten.
	 */
	private static transient KafkaConsumerRunner consumer = null;

	/**
	 * Fügt der Liste von Agenten einen weiteren Agenten hinzu. Beim hinzufügen wird
	 * die Initialisierung des Agenten ausgeführt.
	 * 
	 * @param agent,
	 *            der der Liste hinzugefügt werden soll
	 * @throws AgentException
	 *             wenn der übergebene Agent null ist. Er wird nicht der Liste
	 *             hinzugefügt.
	 * @throws AgentException 
	 */
	public static void add(AbstractAgent agent) throws AgentException, AgentException {
		LOGGER.log(Level.FINE, () -> String.format("try to add %s to list", agent));
		// Prüfung ob der Agent nicht null ist
		if (agent != null) {
			// Im Anschluss wird der Agent initialisiert
			agent.init();
			/*
			 * Jeder Agent bekommt einen Accumulator zugewiesen, um Informationen aus dem
			 * Executor zum Driver zu senden.
			 */
			CollectionAccumulator<AbstractEvent> accum = jsc.sc().collectionAccumulator();
			agent.setAccum(accum);
			// Und der Liste der Agenten hinzugefügt.
			AgentRegistry.INSTANCE.add(agent);
		} else {
			throw new AgentException(String.format("committed agent is invalid: %s", agent));
		}
	}

	/**
	 * Diese Methode führt die DStream-Verarbeitung aus. Der SparkStreamingContext
	 * wird gesetzt und für jeden Agenten in der <code>AgentRegistry</code> wird ein
	 * separater DStream zu Kafka aufgebaut.
	 * 
	 * Ebenfalls enthalten ist die Vearbeitungslogik der DStream-Verarbeitung.
	 * 
	 * @throws InterruptedException
	 *             fängt alle Exceptions ab, die aufkommen während auf dem
	 *             SparkStreamingContext die Methode .awaitTermination() ausgeführt
	 *             wird.
	 */
	public static void start() throws InterruptedException {
		startConsumer();
		jssc = new JavaStreamingContext(jsc, new Duration(SparkContextValues.INSTANCE.getBatchDuration()));
		// Für jeden Agenten in der Registry
		AgentRegistry.INSTANCE.getRegistry().values().parallelStream().forEach(agent -> {
			// Werden die Topics sowie die Konfiguration für das konsumieren ausgelesen
			Collection<String> topics = agent.getSubscribedTopics();
			ConsumerSettings consumerSettings = agent.getConsumerSettings();
			LOGGER.log(Level.FINE, String.format("Agent: %s subcribes to topics: %s.", agent.getId(), topics));

			// Anhand der Konfiguration und der Topics wird ein DStream aufgebaut.
			JavaInputDStream<ConsumerRecord<String, String>> agentConsumer = KafkaUtils.createDirectStream(jssc,
					LocationStrategies.PreferConsistent(),
					ConsumerStrategies.<String, String>Subscribe(topics, consumerSettings.getKafkaParameters()));

			LOGGER.log(Level.FINE, "consumer settings: " + consumerSettings);
			/*
			 * Der eingehende DStream enthält einen ConsumerRecord<String, String>. Der
			 * erste String beinhaltet die Metadaten und der zweite String die eigentliche
			 * Nachricht. Der erste Filter überprüft, ob die Nachricht in einem validen JSON
			 * vorliegt. Wenn ja, wird die Nachricht weiter verarbeitet. Wenn nein, wird die
			 * Nachricht verworfen.
			 */
			JavaDStream<ConsumerRecord<String, String>> agentStream = agentConsumer.filter(new IsJson());
			// Aus dem ConsumerRecord wird die Nachricht entnommen. Der Rest wird verworfen.
			JavaDStream<String> stream = agentStream.map(new ExtractMessage());
			LOGGER.log(Level.FINE, "context: " + stream.context().conf().toDebugString());
			JavaDStream<String> agentWindow = stream.window(new Duration(agent.getWindow().getWindowLength()),
					new Duration(agent.getWindow().getSlideInterval()));

			// Für jedes InterestProfile eines Agenten
			agent.getInterestProfiles().parallelStream().forEach(interestProfile -> {
				// wird die Nachricht überprüft, ob sie für das InterestProfile relevant ist
				agentWindow.filter(new IsMessageOfInterest(interestProfile)).foreachRDD(rdd -> {
					/*
					 * Jede RDD kann mehrere Partitionen besitzen und somit mehrere Nachrichten. Es
					 * werden alle Nachrichten durchlaufen
					 */
					rdd.foreach(message -> {
						// Jede Nachricht wird zu einem Event umgewandelt
						AbstractEvent event = agent.getMessageMapper().toEvent(message);
						// Anschließend an das InteressenProfil übergeben
						interestProfile.receive(event);
					});
				});
			});
		});
		// Startet den StreamingContext
		jssc.start();
		// jssc.addStreamingListener(new JobListener());
		// Wird solange ausgeführt, bis die Anwendung beendet wird
		jssc.awaitTermination();
	}

	/**
	 * Startet den <code>KafkaConsumerRunner</code> es wird eine neue Instanz
	 * erstellt und im Anschluss wird die Instanz in einem separaten Thread
	 * ausgeführt. Dadurch läuft die DStream-Verarbeitung getrennt vom
	 * KafkaConsumerRunner ab.
	 */
	private static void startConsumer() {
		// Erzeugung der Instanz
		consumer = new KafkaConsumerRunner();
		if (consumer != null) {
			// Erzeugt einen Thread für die Ausführung
			Thread thread = new Thread(consumer);
			// Thread wird gestartet.
			thread.start();
		}
	}

	/**
	 * Startet die DStream-Verarbeitung neu. Der <code>KafkaConsumerRunner</code>
	 * wird gestoppt und der SparkStreamingContext wird ebenfalls gestoppt. Der
	 * State von SparkStreamingContext wird auf "STOPPED" gesetzt, nachdem der
	 * Befehl stop() ausgeführt wird. Im Anschluss wird die DStream-Verarbeitung
	 * neugestartet.
	 */
	public static void reset() {
		// Setzt den Flag des Threads auf true und wird somit beendet.
		consumer.shutdown();
		/*
		 * Stoppt die DStream-Verarbeitung. Der erste Parameter steht dafür, ob der
		 * SparkContext beendet werden soll. Dieser muss auf false stehen, weil wenn die
		 * Instanz einmal gestoppt wurde, kann der SparkContext nicht mehr benutzt
		 * werden sowie neugestartet werden. Der zweite Parameter gibt an, ob gewartet
		 * werden soll, bis die aktuelle DStream-Verarbeitung fertiggestellt werden
		 * soll.
		 */
		jssc.stop(false, true);
		// Warten bis der State auf 'STOPPED' steht
		while (!(jssc.getState().toString() == "STOPPED")) {

		}
		// Startet die DStream-Verarbeitung erneut
		try {
			start();
		} catch (InterruptedException e) {
			LOGGER.log(Level.WARNING, () -> String.format("failed to restart StreamingExecution:%sException:%s",
					SystemUtils.getFileSeparator(), e));
		}
	}
}