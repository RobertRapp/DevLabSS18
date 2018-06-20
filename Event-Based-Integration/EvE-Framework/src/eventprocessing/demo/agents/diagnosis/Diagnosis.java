package eventprocessing.demo.agents.diagnosis;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.produce.kafka.ProducerSettings;

/**
 * Dieser Agent ist für die Diagnose des Verkehrs zuständig. Er wertet die
 * Verkehrsdaten aus und leitet daraus entsprechende Handlungen ab.
 * 
 * @author IngoT
 *
 */
public class Diagnosis extends AbstractAgent {

	private static final long serialVersionUID = 5361140545621342116L;

	@Override
	protected void doOnInit() {
		// Setzt die Id für den Agenten
		this.setId("DiagnosisAgent");
		/*
		 * Fügt dem Agenten ein InteressenProfil hinzu Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		try {
			AbstractInterestProfile ip = new DiagnosisInterestProfile();
			ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSpeedEvent()));
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		/*
		 * Angabe der Topics, die konsumiert werden sollen. Es können mehrere Topics
		 * angegeben werden.
		 */
		try {
			this.add(ShowcaseValues.INSTANCE.getDiagnosisTopic());
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}
		ConsumerSettings csettings = new ConsumerSettings();
		csettings.add(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ConsumerSettingsDiagnosis.INSTANCE.getIPv4Bootstrap() + ":" + ConsumerSettingsDiagnosis.INSTANCE.getPortBootstrap());
		csettings.add(ConsumerConfig.GROUP_ID_CONFIG, ConsumerSettingsDiagnosis.INSTANCE.getGroupId());
		csettings.add(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ConsumerSettingsDiagnosis.INSTANCE.getKeyDeserializer());
		csettings.add(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ConsumerSettingsDiagnosis.INSTANCE.getValueDeserializer());
		csettings.add(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, ConsumerSettingsDiagnosis.INSTANCE.getPartitionAssignmentStrategy());
		this.setConsumerSettings(csettings);

		ProducerSettings pSettings = new ProducerSettings();
		// IPv4-Adresse des Kafkaservers. Port ist Standardmäßig 9092
		pSettings.add(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getIPv4Bootstrap() + ":" + ProducerSettingsDiagnosis.INSTANCE.getPortBootstrap());
		// Bestätigung für alle gesendeten Nachrichten anfordern
		pSettings.add(ProducerConfig.ACKS_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getAcks());
		// Wie Lang darf die Nachricht sein
		pSettings.add(ProducerConfig.BATCH_SIZE_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getBatchSize());
		// In welchen zeitlichen Abstand werden die Nachrichten vor der Übertragung geschnitten
		pSettings.add(ProducerConfig.LINGER_MS_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getLingerMS());
		// sollen Fehlgeschlagene Versuche wiederholt werden?
		pSettings.add(ProducerConfig.RETRIES_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getRetries());
		// Wie viel darf im Arbeitsspeicher verbleiben
		pSettings.add(ProducerConfig.BUFFER_MEMORY_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getBufferMemory());
		// Für die Serialisierung der Key-/Value Paare
		pSettings.add(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getKeySerializer());
		pSettings.add(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getValueSerializer());
		this.setProducerSettings(pSettings);
		
	}
}
