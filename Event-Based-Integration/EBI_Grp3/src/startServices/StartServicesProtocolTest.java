package startServices;

import java.util.logging.Level;

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
		
		protocolAgent.setConsumerSettings(new ConsumerSettings("localhost","9092", "base"));
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
				System.out.println(i);
				AbstractEvent sessionStart = eventFactory.createEvent("AtomicEvent");
				sessionStart.setType("sessionStart");
				Property<String> sessionname = new Property<>();
				sessionname.setKey("name");
				sessionname.setValue("Session XAZ");
				sessionStart.add(sessionname);
				System.out.println("publish sessionstart");
				System.out.println(sessionStart);
				publish(sessionStart,"SessionInfo");
				Thread.sleep(7000);
				
				AbstractEvent user = eventFactory.createEvent("AtomicEvent");
				user.setType("user");
				System.out.println("publish user");
				Property<String> name = new Property<>();
				name.setKey("name");
				name.setValue("Nikolaus Eblenkamp");
				user.add(name);
				publish(user,"UserInfo");
				Thread.sleep(7000);
				
				AbstractEvent user2 = eventFactory.createEvent("AtomicEvent");
				user2.setType("user");
				Property<String> name2 = new Property<>();
				name2.setKey("name");
				name2.setValue("Gero Menz");
				user2.add(name2);
				System.out.println("publish user2");
				publish(user2,"UserInfo");
				Thread.sleep(7000);
				
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
				System.out.println("publish prop doc");
				publish(proposedDoc1,"proposedDoc");
				Thread.sleep(7000);
				
				AbstractEvent proposedDoc2 = eventFactory.createEvent("AtomicEvent");
				proposedDoc2.setType("proposedDoc");
				Property<String> dokumentName2 = new Property<>();
				Property<String> erstellDatum2 = new Property<>();
				dokumentName2.setKey("dokumentName");
				dokumentName2.setValue("WordDOkument");
				erstellDatum2.setKey("Erstelldatum");
				erstellDatum2.setValue("2012");
				proposedDoc2.add(dokumentName2);
				proposedDoc2.add(erstellDatum2);
				publish(proposedDoc2,"proposedDoc");
				Thread.sleep(7000);
				
				AbstractEvent clickedDoc = eventFactory.createEvent("AtomicEvent");
				clickedDoc.setType("clickedDoc");
				Property<String> dokumentName3 = new Property<>();
				Property<String> erstellDatum3 = new Property<>();
				dokumentName3.setKey("dokumentName");
				dokumentName3.setValue("WordDOkument");
				erstellDatum3.setKey("Erstelldatum");
				erstellDatum3.setValue("2020");
				clickedDoc.add(dokumentName3);
				clickedDoc.add(erstellDatum3);
				publish(clickedDoc,"clickedDoc");
				Thread.sleep(7000);
				
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
				Thread.sleep(7000);
				
				AbstractEvent tokenEvent2 = eventFactory.createEvent("AtomicEvent");
				tokenEvent2.setType("TokenEvent");
				Property<String> topic2 = new Property<>();
				Property<String> project2 = new Property<>();
				topic2.setKey("topic");
				topic2.setValue("Bier");
				project2.setKey("project");
				project2.setValue("Rathke Projekt");
				tokenEvent2.add(topic2);
				tokenEvent2.add(project2);
				publish(tokenEvent2,"TokenGeneration");
				Thread.sleep(7000);
				
				AbstractEvent sessionEnd = eventFactory.createEvent("AtomicEvent");
				sessionEnd.setType("sessionEnd");
				publish(sessionEnd,"SessionInfo");

				
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