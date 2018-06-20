package startServices;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.spark.SparkException;
import org.apache.spark.TaskKilledException;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.consume.spark.streaming.NoValidAgentException;
import eventprocessing.consume.spark.streaming.StreamingExecution;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.demo.agents.diagnosis.ConsumerSettingsDiagnosis;
import eventprocessing.demo.agents.diagnosis.Diagnosis;
import eventprocessing.demo.agents.diagnosis.DiagnosisInterestProfile;
import eventprocessing.demo.agents.diagnosis.ProducerSettingsDiagnosis;
import eventprocessing.demo.events.SensorEvent;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.EventIdProvider;
import eventprocessing.event.Property;
import eventprocessing.produce.kafka.Despatcher;
import eventprocessing.produce.kafka.ProducerSettings;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.mapping.MessageMapper;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.SessionContextAgent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.SessionState;

/**
 * Startpunkt der Anwendung.
 * 
 * hier werden die Agenten initialisiert und die Sparkumgebung ausgefÃ¼hrt.
 * 
 * @author RobertRapp
 *
 */
public class StartServices {


		
		 // FÃ¼r die Versendung der DemoEvents an das Topic nötig.
	private static Despatcher despatcher = null;
	// wandelt die Events in Nachrichten um.
	private static final MessageMapper messageMapper = new MessageMapper();
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static AbstractFactory agentFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getAgentFactory());
	
	
	public static void main(String[] args)
			throws TaskKilledException, SparkException, InterruptedException, NoValidAgentException {
		
	
		
		//AbstractAgent activityService = (AbstractAgent) agentFactory.createAgent("ActivityAgent");		
		//AbstractAgent protocolService = (AbstractAgent) agentFactory.createAgent("ProtocolAgent");
		//AbstractAgent sessionContext = (AbstractAgent)  agentFactory.createAgent("SessionContextAgent");
		
		//AbstractAgent activityService = (AbstractAgent) new ActivityAgent();		
		//AbstractAgent protocolService = (AbstractAgent) new ProtocolAgent();
		AbstractAgent sessionContext = (AbstractAgent)  new SessionContextAgent();
		try {
			AbstractInterestProfile ip = new SessionState();
			ip.add(new IsEventType("SensorEvent"));
			sessionContext.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		try {
			sessionContext.add("SessionState");
		} catch (NoValidConsumingTopicException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
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
		
		despatcher = new Despatcher(pSettings);
		
		//activityService.setProducerSettings(pSettings);
		//protocolService.setProducerSettings(pSettings);
		sessionContext.setProducerSettings(pSettings);
		
		ConsumerSettings csettings = new ConsumerSettings();
		csettings.add(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ConsumerSettingsDiagnosis.INSTANCE.getIPv4Bootstrap() + ":" + ConsumerSettingsDiagnosis.INSTANCE.getPortBootstrap());
		csettings.add(ConsumerConfig.GROUP_ID_CONFIG, ConsumerSettingsDiagnosis.INSTANCE.getGroupId());
		csettings.add(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ConsumerSettingsDiagnosis.INSTANCE.getKeyDeserializer());
		csettings.add(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ConsumerSettingsDiagnosis.INSTANCE.getValueDeserializer());
		csettings.add(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, ConsumerSettingsDiagnosis.INSTANCE.getPartitionAssignmentStrategy());
		
		//activityService.setConsumerSettings(csettings);
		//protocolService.setConsumerSettings(csettings);
		sessionContext.setConsumerSettings(csettings);
		
		
		//AbstractFactory agentFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getAgentFactory());
		// Erstellung der Agenten
	

		
		//StreamingExecution.add(activityService);
		//StreamingExecution.add(protocolService);
		StreamingExecution.add(sessionContext);

		
		Runnable myRunnable = new Runnable() {
			public void run() {
				try {
					publishDemoEvents();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		// Thread wird erzeugt und gestartet
		Thread thread = new Thread(myRunnable);
		thread.start();

		
		StreamingExecution.start();
	}

	
	private static void publish(AbstractEvent event, String topic) {
		String message = messageMapper.toJSON(event);
		
		despatcher.deliver(message, topic);
	}

	
	private static void publishDemoEvents() throws InterruptedException {
			
		SensorEvent event = (SensorEvent) eventFactory.createEvent("SensorEvent");
			Property<Long> sessionStart = new Property<Long>("sessionStart", System.currentTimeMillis());
			event.add(sessionStart);
			event.setId(EventIdProvider.INSTANCE.getUniqueId());
			
			
			int zaehler = 10;
			for(int i = 0 ; i < zaehler; i++) {
				Thread.sleep(ShowcaseValues.INSTANCE.getThreadSleep());
				Property<Long> sessionid = new Property<Long>("sessionId", Long.valueOf(i));
				event.add(sessionid);
				publish(event,"SessionState");		
				Logger l = LoggerFactory.getLogger("StartServices");
				l.log(Level.WARNING, "Event wurde direkt durch Dispatcher auf SessionState gepusht");
			}
						
			/*
			TokenEvent event2 = (TokenEvent) new TokenEvent();
			event2.setSessionID("1");
			Property<String> taskdocument = new Property<String>("documentcategory", "TaskDocument");
			event2.add(taskdocument);			
					
			publish(event2,"TokenGeneration");

			TokenEvent event3 = (TokenEvent) new TokenEvent();
			event.setSessionID("2");
			Property<Long> sessionStart = new Property<Long>("sessionStart", System.currentTimeMillis());
			event3.add(sessionStart);
			int zaehler = 5;
			for(int i = 0 ; i < zaehler; i++) {
				Thread.sleep(ShowcaseValues.INSTANCE.getThreadSleep());
				publish(event3,"test");
				Logger l = LoggerFactory.getLogger("PUBLISHDEMOEVENTS");
				l.log(Level.WARNING, "Event wurde direkt durch Dispatcher auf Test gepusht");
			}
			
					
			publish(event3,"test");
			Logger l = LoggerFactory.getLogger("PUBLISHDEMOEVENTS");
			l.log(Level.WARNING, "Event wurde direkt durch Dispatcher auf Test gepusht");
			}
			
			*/
	}	 
	
}