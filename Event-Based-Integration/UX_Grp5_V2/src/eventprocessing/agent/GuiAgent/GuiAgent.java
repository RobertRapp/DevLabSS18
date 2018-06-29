package eventprocessing.agent.GuiAgent;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.demo.ShowcaseValues;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.DocProposal.ConsumerSettingsGui;
import eventprocessing.agent.DocProposal.ProducerSettingsGui;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.GetEverything;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.demo.agents.diagnosis.ConsumerSettingsDiagnosis;
import eventprocessing.demo.agents.diagnosis.ProducerSettingsDiagnosis;
import eventprocessing.produce.kafka.ProducerSettings;
import values.GUIValues;


/**
 * Dieser Agent ist für die Diagnose des Verkehrs zuständig. Er wertet die
 * Verkehrsdaten aus und leitet daraus entsprechende Handlungen ab.
 * 
 * @author IngoT
 *
 */
public class GuiAgent extends AbstractAgent {

	private static final long serialVersionUID = 5414649241552569623L;

	@Override
	protected void doOnInit() {
		this.setId("GuiAgent");
		//System.out.println("GuiAgent");
		/*
		 * Fügt dem Agenten ein InteressenProfil hinzu Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		try {
			AbstractInterestProfile ip = new GuiInterestProfileDocProposal();
			ip.add(new IsEventType("DocProposalEvent"));
			//ip.add(new GetEverything());
			//ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSpeedEvent()));
			this.add(ip);
			//System.out.println("ip geadded");
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
		try {
			AbstractInterestProfile ip = new GuiInterestProfileUserInteraction();
			ip.add(new IsEventType("UserInteractionEvent"));
			//ip.add(new GetEverything());
			//ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSpeedEvent()));
			this.add(ip);
			//System.out.println("ip geadded");
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
		try {
			AbstractInterestProfile ip = new GuiInterestProfileSessionEnd();
			ip.add(new IsEventType("SessionEndEvent"));
			//ip.add(new GetEverything());
			//ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSpeedEvent()));
			this.add(ip);
			//System.out.println("ip geadded");
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
		/*
		 * Angabe der Topics, die konsumiert werden sollen. Es können mehrere Topics
		 * angegeben werden.
		 */
		try {
			this.add("Gui");
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}

		
		/*
		ConsumerSettings csettings = new ConsumerSettings();
		csettings.add(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ConsumerSettingsGui.INSTANCE.getIPv4Bootstrap() + ":" + ConsumerSettingsGui.INSTANCE.getPortBootstrap());
		csettings.add(ConsumerConfig.GROUP_ID_CONFIG, ConsumerSettingsGui.INSTANCE.getGroupId());
		csettings.add(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ConsumerSettingsGui.INSTANCE.getKeyDeserializer());
		csettings.add(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ConsumerSettingsGui.INSTANCE.getValueDeserializer());
		csettings.add(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, ConsumerSettingsGui.INSTANCE.getPartitionAssignmentStrategy());
		this.setConsumerSettings(csettings);

		ProducerSettings pSettings = new ProducerSettings();
		// IPv4-Adresse des Kafkaservers. Port ist Standardmäßig 9092
		pSettings.add(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ProducerSettingsGui.INSTANCE.getIPv4Bootstrap() + ":" + ProducerSettingsGui.INSTANCE.getPortBootstrap());
		// Bestätigung für alle gesendeten Nachrichten anfordern
		pSettings.add(ProducerConfig.ACKS_CONFIG, ProducerSettingsGui.INSTANCE.getAcks());
		// Wie Lang darf die Nachricht sein
		pSettings.add(ProducerConfig.BATCH_SIZE_CONFIG, ProducerSettingsGui.INSTANCE.getBatchSize());
		// In welchen zeitlichen Abstand werden die Nachrichten vor der Übertragung geschnitten
		pSettings.add(ProducerConfig.LINGER_MS_CONFIG, ProducerSettingsGui.INSTANCE.getLingerMS());
		// sollen Fehlgeschlagene Versuche wiederholt werden?
		pSettings.add(ProducerConfig.RETRIES_CONFIG, ProducerSettingsGui.INSTANCE.getRetries());
		// Wie viel darf im Arbeitsspeicher verbleiben
		pSettings.add(ProducerConfig.BUFFER_MEMORY_CONFIG, ProducerSettingsGui.INSTANCE.getBufferMemory());
		// Für die Serialisierung der Key-/Value Paare
		pSettings.add(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ProducerSettingsGui.INSTANCE.getKeySerializer());
		pSettings.add(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ProducerSettingsGui.INSTANCE.getValueSerializer());
		this.setProducerSettings(pSettings);
*/

	}
}
