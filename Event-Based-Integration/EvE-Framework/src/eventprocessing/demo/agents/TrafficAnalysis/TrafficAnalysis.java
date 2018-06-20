package eventprocessing.demo.agents.TrafficAnalysis;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.consume.spark.streaming.window.NoValidWindowSettingsException;
import eventprocessing.consume.spark.streaming.window.Window;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.produce.kafka.ProducerSettings;

/**
 * Ist für die Korrelation der Ereignisse zuständig. Aktuell hält er alle
 * eingehenden Ereignisse vor und wartet bis sich je ein Event pro Sensor
 * (Identikiation anhand der ID) in seiner Liste befindet. Hier wird davon
 * ausgegangen, dass das eintreffen des Events vom zweiten Sensor zum ersten
 * Sensor gehört. Die aggregierten Events werden dann weiter zum
 * <code>Diagnosis</code> Agenten geschickt, um eine Bewertung vorzunehmen.
 * 
 * @author IngoT
 *
 */
public class TrafficAnalysis extends AbstractAgent {

	private static final long serialVersionUID = -8805731402674955616L;

	/**
	 * muss einmalig ausgeführt werden. Damit der Agent "startet"
	 */
	@Override
	protected void doOnInit() {
		// Setzt die Id des Agenten
		this.setId("TrafficAgent");

		/*
		 * Fügt dem Agenten ein InteressenProfil hinzu Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		try {
			AbstractInterestProfile ip = new SpeedometerInterestProfile();
			ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSensorEvent()));
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		/*
		 * Angabe der Topics, die konsumiert werden sollen. Es können mehrere Topics
		 * angegeben werden.
		 */
		try {
			this.add(ShowcaseValues.INSTANCE.getTrafficDataTopic());
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}

		/*
		 * Setzt für diesen Agenten ein Window. Wenn kein Window gesetzt wird, wird ein
		 * Default-Window erzeugt, welches sich an der Batch-Duration orientiert.
		 * <code>SparkContextValues</code>
		 */
		try {
			this.setWindow(new Window(20000, 20000));
		} catch (NoValidWindowSettingsException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		ConsumerSettings csettings = new ConsumerSettings();
		csettings.add(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				ConsumerSettingsTrafficAnalysis.INSTANCE.getIPv4Bootstrap() + ":"
						+ ConsumerSettingsTrafficAnalysis.INSTANCE.getPortBootstrap());
		csettings.add(ConsumerConfig.GROUP_ID_CONFIG, ConsumerSettingsTrafficAnalysis.INSTANCE.getGroupId());
		csettings.add(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				ConsumerSettingsTrafficAnalysis.INSTANCE.getKeyDeserializer());
		csettings.add(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				ConsumerSettingsTrafficAnalysis.INSTANCE.getValueDeserializer());
		csettings.add(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG,
				ConsumerSettingsTrafficAnalysis.INSTANCE.getPartitionAssignmentStrategy());
		this.setConsumerSettings(csettings);

		ProducerSettings pSettings = new ProducerSettings();
		// IPv4-Adresse des Kafkaservers. Port ist Standardmäßig 9092
		pSettings.add(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				ProducerSettingsTrafficAnalysis.INSTANCE.getIPv4Bootstrap() + ":"
						+ ProducerSettingsTrafficAnalysis.INSTANCE.getPortBootstrap());
		// Bestätigung für alle gesendeten Nachrichten anfordern
		pSettings.add(ProducerConfig.ACKS_CONFIG, ProducerSettingsTrafficAnalysis.INSTANCE.getAcks());
		// Wie Lang darf die Nachricht sein
		pSettings.add(ProducerConfig.BATCH_SIZE_CONFIG, ProducerSettingsTrafficAnalysis.INSTANCE.getBatchSize());
		// In welchen zeitlichen Abstand werden die Nachrichten vor der Übertragung
		// geschnitten
		pSettings.add(ProducerConfig.LINGER_MS_CONFIG, ProducerSettingsTrafficAnalysis.INSTANCE.getLingerMS());
		// sollen Fehlgeschlagene Versuche wiederholt werden?
		pSettings.add(ProducerConfig.RETRIES_CONFIG, ProducerSettingsTrafficAnalysis.INSTANCE.getRetries());
		// Wie viel darf im Arbeitsspeicher verbleiben
		pSettings.add(ProducerConfig.BUFFER_MEMORY_CONFIG, ProducerSettingsTrafficAnalysis.INSTANCE.getBufferMemory());
		// Für die Serialisierung der Key-/Value Paare
		pSettings.add(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				ProducerSettingsTrafficAnalysis.INSTANCE.getKeySerializer());
		pSettings.add(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				ProducerSettingsTrafficAnalysis.INSTANCE.getValueSerializer());
		this.setProducerSettings(pSettings);
	}
}
