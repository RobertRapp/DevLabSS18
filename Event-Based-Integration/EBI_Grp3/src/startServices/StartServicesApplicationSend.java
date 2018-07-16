package startServices;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.consume.kafka.ConsumerSettings;
<<<<<<< HEAD
import eventprocessing.consume.spark.streaming.NoValidAgentException;
=======
import eventprocessing.agent.AgentException;
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
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
import eventprocessing.utils.model.EventUtils;
import hdm.developmentlab.ebi.eve_implementation.activityService.ActivityAgent;

/**
 * Startpunkt der Anwendung.
 * 
 * hier werden die Agenten initialisiert und die Sparkumgebung ausgefÃ¼hrt.
 * 
 * @author RobertRapp
 *
 */
public class StartServicesApplicationSend {


		
		 // FÃ¼r die Versendung der DemoEvents an das Topic nötig.
	private static Despatcher despatcher = null;
	// wandelt die Events in Nachrichten um.
	private static final MessageMapper messageMapper = new MessageMapper();
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static AbstractFactory agentFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getAgentFactory());
	
	
<<<<<<< HEAD
	public static void main(String[] args) throws NoValidAgentException, InterruptedException
	 {
		despatcher = new Despatcher(new ProducerSettings("localhost","9092"));
		AbstractAgent activityAgent = new ActivityAgent();
		
		activityAgent.setConsumerSettings(new ConsumerSettings("localhost","9092", "g"));
		activityAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
=======
	public static void main(String[] args) throws AgentException, InterruptedException
	 {
		despatcher = new Despatcher(new ProducerSettings("10.142.0.2","9092"));
		AbstractAgent activityAgent = new ActivityAgent();
		
		activityAgent.setConsumerSettings(new ConsumerSettings("10.142.0.2","9092", "g"));
		activityAgent.setProducerSettings(new ProducerSettings("10.142.0.2","9092"));
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
		
		
		//StreamingExecution.add(activityService);
		//StreamingExecution.add(protocolService);
		StreamingExecution.add(activityAgent);

		
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
			
			for (int i = 0; i < 5; i++) {
				
				
				AbstractEvent event = eventFactory.createEvent("AtomicEvent");
				event.setType("TokenEvent");
				Property<String> project2 = new Property<String>("project", "Highnet");
				Property<String> thema = new Property<String>("topic", "Kosten");
				Property<String> type = new Property<String>("type", "application");
				Property<String> link = new Property<String>("link", "application");
				Property<String> user4 = new Property<String>("user", "Detlef Gabe"+i);
				event.add(project2);			
				event.add(thema);			
				event.add(type);			
				event.add(link);
				event.add(user4);				
				System.out.println(EventUtils.findPropertyByKey(event, "type"));
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