package eventprocessing.demo.agents.SensorProcessing;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.logical.Or;
import eventprocessing.agent.interestprofile.predicates.statement.HasPropertyContains;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.produce.kafka.ProducerSettings;

/**
 * Er ist für die Filterung aller Ereignisse der Strasse zuständig. Er überprüft
 * ob inkonsistente Werte übermittelt wurden. Beispielsweise ob die SensorID
 * vorhanden ist.
 * 
 * Somit ist er zuständig für die Vorerarbeitung der Events, damit der
 * <code>TrafficAnalysis</code> Agent nur mit bereinigten Events konfrontiert
 * wird.
 * 
 * @author IngoT
 *
 */
public class SensorProcessing extends AbstractAgent {

	private static final long serialVersionUID = -6290605654887643604L;

	/**
	 * muss einmalig ausgeführt werden, um den Agenten zu starten.
	 */
	@Override
	protected void doOnInit() {
		this.setId("SensorAgent");
		// Fügt dem Agenten ein InteressenProfil hinzu
		try {
			AbstractInterestProfile ip = new SensorProcessingInterestProfile();
			ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSensorEvent()));
			try {
				ip.add(new Or(new HasPropertyContains("Location", ShowcaseValues.INSTANCE.getFirstSensorLocation()),
						new HasPropertyContains("Location", ShowcaseValues.INSTANCE.getSecondSensorLocation())));
			} catch (eventprocessing.agent.interestprofile.predicates.logical.NullPredicateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		// Angabe der Topics die vom Agenten abgerufen werden sollen.
		try {
			this.add(ShowcaseValues.INSTANCE.getFirstTopicSensor());
			this.add(ShowcaseValues.INSTANCE.getSecondTopicSensor());
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}

		ConsumerSettings csettings = new ConsumerSettings();
		csettings.add(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ConsumerSettingsSensorProcessing.INSTANCE.getIPv4Bootstrap() + ":" + ConsumerSettingsSensorProcessing.INSTANCE.getPortBootstrap());
		csettings.add(ConsumerConfig.GROUP_ID_CONFIG, ConsumerSettingsSensorProcessing.INSTANCE.getGroupId());
		csettings.add(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ConsumerSettingsSensorProcessing.INSTANCE.getKeyDeserializer());
		csettings.add(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ConsumerSettingsSensorProcessing.INSTANCE.getValueDeserializer());
		csettings.add(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, ConsumerSettingsSensorProcessing.INSTANCE.getPartitionAssignmentStrategy());
		this.setConsumerSettings(csettings);

		ProducerSettings pSettings = new ProducerSettings();
		// IPv4-Adresse des Kafkaservers. Port ist Standardmäßig 9092
		pSettings.add(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ProducerSettingsSensorProcessing.INSTANCE.getIPv4Bootstrap() + ":" + ProducerSettingsSensorProcessing.INSTANCE.getPortBootstrap());
		// Bestätigung für alle gesendeten Nachrichten anfordern
		pSettings.add(ProducerConfig.ACKS_CONFIG, ProducerSettingsSensorProcessing.INSTANCE.getAcks());
		// Wie Lang darf die Nachricht sein
		pSettings.add(ProducerConfig.BATCH_SIZE_CONFIG, ProducerSettingsSensorProcessing.INSTANCE.getBatchSize());
		// In welchen zeitlichen Abstand werden die Nachrichten vor der Übertragung geschnitten
		pSettings.add(ProducerConfig.LINGER_MS_CONFIG, ProducerSettingsSensorProcessing.INSTANCE.getLingerMS());
		// sollen Fehlgeschlagene Versuche wiederholt werden?
		pSettings.add(ProducerConfig.RETRIES_CONFIG, ProducerSettingsSensorProcessing.INSTANCE.getRetries());
		// Wie viel darf im Arbeitsspeicher verbleiben
		pSettings.add(ProducerConfig.BUFFER_MEMORY_CONFIG, ProducerSettingsSensorProcessing.INSTANCE.getBufferMemory());
		// Für die Serialisierung der Key-/Value Paare
		pSettings.add(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ProducerSettingsSensorProcessing.INSTANCE.getKeySerializer());
		pSettings.add(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ProducerSettingsSensorProcessing.INSTANCE.getValueSerializer());
		this.setProducerSettings(pSettings);
	}
}
