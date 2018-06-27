package startServices;

import java.util.logging.Level;


import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.GetEverything;
import eventprocessing.agent.interestprofile.predicates.statement.IsFromTopic;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.consume.spark.streaming.NoValidAgentException;
import eventprocessing.consume.spark.streaming.StreamingExecution;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.AtomicEvent;
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
public class StartServicesDocRequest {


		
		 // FÃ¼r die Versendung der DemoEvents an das Topic nötig.
	private static Despatcher despatcher = null;
	// wandelt die Events in Nachrichten um.
	private static final MessageMapper messageMapper = new MessageMapper();
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static AbstractFactory agentFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getAgentFactory());
	
	
	public static void main(String[] args) throws NoValidAgentException, InterruptedException
	 {
		despatcher = new Despatcher(new ProducerSettings("localhost","9092"));
		AbstractAgent requestAgent = new RequestAgent();
		
		requestAgent.setConsumerSettings(new ConsumerSettings("localhost","9092", "g"));
		requestAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		
		
		//StreamingExecution.add(activityService);
		//StreamingExecution.add(protocolService);
		StreamingExecution.add(requestAgent);

		
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
		java.util.logging.Logger logger = LoggerFactory.getLogger("StartServices!");				
		String message = messageMapper.toJSON(event);	
		if(message != null && topic != null) {
			despatcher.deliver(message, topic);	
		}
		
	}

	
	private static void publishDemoEvents() throws InterruptedException {		
			
			for (int i = 0; i < 1; i++) {
				
				
				AbstractEvent sessioncontext = eventFactory.createEvent("AtomicEvent");
				sessioncontext.setType("SessionContext");
				Property<String> project = new Property<String>("project", "2222222222Highnet");
				Property<String> topic = new Property<String>("topic", "Kosten");
				Property<String> user = new Property<String>("user", "Robert Rapp"+i);
				Property<String> user2 = new Property<String>("users", "Detlef Gabe"+i);
				Property<String> latestAct = new Property<String>("latestActivity", "Activity Folfe");
				Property<TimeReference> timereference = new Property<TimeReference>("timereference", null);
				sessioncontext.add(project);			
				sessioncontext.add(topic);			
				sessioncontext.add(user);		
				sessioncontext.add(latestAct);
				sessioncontext.add(user2);			
				sessioncontext.add(timereference);			
				
				publish(sessioncontext,"SessionContext");
				
				AbstractEvent event = eventFactory.createEvent("AtomicEvent");
				event.setType("TokenEvent");
				Property<String> project2 = new Property<String>("project", "Highnet");
				Property<String> thema = new Property<String>("topic", "Kosten");
				Property<String> user3 = new Property<String>("user", "Robert Rapp"+i);
				Property<String> user4 = new Property<String>("user", "Detlef Gabe"+i);
				event.add(project2);			
				event.add(thema);			
				event.add(user3);			
				event.add(user4);				
				
				publish(event,"TokenGeneration");
				

				
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