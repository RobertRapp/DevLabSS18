package startServices;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.consume.spark.streaming.NoValidAgentException;
import eventprocessing.consume.spark.streaming.StreamingExecution;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.produce.kafka.Despatcher;
import eventprocessing.produce.kafka.ProducerSettings;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.mapping.MessageMapper;
import hdm.developmentlab.ebi.eve_implementation.activityService.RequestAgent;
import hdm.developmentlab.ebi.eve_implementation.events.TimeReference;
import hdm.developmentlab.ebi.eve_implementation.protocolService.ProtocolAgent;

/**
 * Startpunkt der Anwendung.
 * 
 * hier werden die Agenten initialisiert und die Sparkumgebung ausgefÃ¼hrt.
 * 
 * @author RobertRapp
 *
 */
public class StartServicesProtocolTest {


		
		 // FÃ¼r die Versendung der DemoEvents an das Topic nötig.
	private static Despatcher despatcher = null;
	// wandelt die Events in Nachrichten um.
	private static final MessageMapper messageMapper = new MessageMapper();
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static AbstractFactory agentFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getAgentFactory());
	
	
	public static void main(String[] args) throws NoValidAgentException, InterruptedException
	 {
		despatcher = new Despatcher(new ProducerSettings("localhost","9092"));
		AbstractAgent protocolAgent = new ProtocolAgent();
		
		protocolAgent.setConsumerSettings(new ConsumerSettings("localhost","9092", "g"));
		protocolAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		
		
		//StreamingExecution.add(activityService);
		//StreamingExecution.add(protocolService);
		StreamingExecution.add(protocolAgent);

		
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
		LoggerFactory.getLogger("StartServices!");				
		String message = messageMapper.toJSON(event);	
		if(message != null && topic != null) {
			despatcher.deliver(message, topic);	
		}
		
	}

	
	private static void publishDemoEvents() throws InterruptedException {		
			
			for (int i = 0; i < 1; i++) {
				
				AbstractEvent sessionStart = eventFactory.createEvent("AtomicEvent");
				sessionStart.setType("sessionStart");
				publish(sessionStart,"SessionInfo");
				Thread.sleep(1100);
				AbstractEvent user = eventFactory.createEvent("AtomicEvent");
				user.setType("User");
				publish(user,"UserInfo");
				Thread.sleep(1500);
				AbstractEvent proposedDoc1 = eventFactory.createEvent("AtomicEvent");
				proposedDoc1.setType("proposedDoc");
				Property<String> dokumentName = new Property<>();
				Property<String> erstellDatum = new Property<>();
				dokumentName.setKey("dokumentName");
				dokumentName.setValue("WordDOkument");
				erstellDatum.setKey("Erstelldatum");
				erstellDatum.setValue("2012");
				proposedDoc1.add(dokumentName);
				proposedDoc1.add(erstellDatum);
				publish(proposedDoc1,"proposedDoc");
				Thread.sleep(5000);
				
				AbstractEvent proposedDoc2 = eventFactory.createEvent("AtomicEvent");
				proposedDoc2.setType("proposedDoc");
				Property<String> dokumentName3 = new Property<>();
				Property<String> erstellDatum3 = new Property<>();
				dokumentName3.setKey("dokumentName");
				dokumentName3.setValue("WordDOkument");
				erstellDatum.setKey("Erstelldatum");
				erstellDatum.setValue("2012");
				proposedDoc2.add(dokumentName3);
				proposedDoc2.add(erstellDatum);
				publish(proposedDoc2,"proposedDoc");
				Thread.sleep(7599);
				
				AbstractEvent clickedDoc = eventFactory.createEvent("AtomicEvent");
				clickedDoc.setType("clickedDoc");
				Property<String> dokumentName2 = new Property<>();
				Property<String> erstellDatum2 = new Property<>();
				dokumentName2.setKey("dokumentName");
				dokumentName2.setValue("WordDOkument");
				erstellDatum.setKey("Erstelldatum");
				erstellDatum.setValue("2012");
				clickedDoc.add(dokumentName2);
				clickedDoc.add(erstellDatum);
				publish(clickedDoc,"clickedDoc");
				Thread.sleep(3422);
				
				AbstractEvent tokenEvent = eventFactory.createEvent("AtomicEvent");
				tokenEvent.setType("TokenEvent");
				Property<String> topic = new Property<>();
				Property<String> project = new Property<>();
				topic.setKey("topic");
				topic.setValue("Fussball");
				project.setKey("project");
				project.setValue("Thies Projekt");
				tokenEvent.add(topic);
				tokenEvent.add(project);
				publish(tokenEvent,"TokenGeneration");
				Thread.sleep(2311);
				
				AbstractEvent sessionEnd = eventFactory.createEvent("AtomicEvent");
				sessionEnd.setType("sessionEnd");
				publish(sessionEnd,"SessionInfo");

				
//				AbstractEvent event2 = eventFactory.createEvent("AtomicEvent");
//				event2.setType("SpeedEvent");
//				Property<String> repo = new Property<String>("REPORT", "EVENT GEHT INS DIAGNOSIS IP");
//				event2.add(repo);				
//				publish(event2,"SessionState");					
//				logger.log(Level.WARNING, "SESSIONSTATE AUF SESSIONSTATE GEPUSHT");				
				Thread.sleep(1000);
				
			}
			

			/*
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