package startServices;

import com.speechTokens.EvE.agents.SentenceAgent;
import com.speechTokens.EvE.agents.TokenizeAgent;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.agent.AgentException;
import eventprocessing.consume.spark.streaming.StreamingExecution;
import eventprocessing.event.AbstractEvent;
import eventprocessing.produce.kafka.Despatcher;
import eventprocessing.produce.kafka.ProducerSettings;
import eventprocessing.utils.SocketServer;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.mapping.MessageMapper;
import eventprocessing.utils.model.OWLResultUtils;
import hdm.developmentlab.ebi.eve_implementation.activityService.ActivityAgent;
import hdm.developmentlab.ebi.eve_implementation.activityService.RequestAgent;

/**
 * Startpunkt der Anwendung.
 * 
 * hier werden die Agenten initialisiert und die Sparkumgebung ausgefÃƒÂ¼hrt.
 * 
 * @author RobertRapp
 *
 */
public class StartServicesPart1 {


		
		 // FÃƒÂ¼r die Versendung der DemoEvents an das Topic nÃ¶tig.
	private static Despatcher despatcher = null;
	// wandelt die Events in Nachrichten um.
	private static final MessageMapper messageMapper = new MessageMapper();
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static AbstractFactory agentFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getAgentFactory());
	
	
	public static void main(String[] args) throws AgentException, InterruptedException
	 {
		despatcher = new Despatcher(new ProducerSettings("localhost","9092"));
		
		System.out.println(OWLResultUtils.convertBindingElementInPropertySet("{\r\n" + 
				"        \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostStatement\" } ,\r\n" + 
				"        \"Classname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#ProjectControlling\" } ,\r\n" + 
				"        \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Document\" } ,\r\n" + 
				"        \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#HasAuthor\" } ,\r\n" + 
				"        \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Vanessa_Keller\" } ,\r\n" + 
				"        \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } ,\r\n" + 
				"        \"Name\": { \"type\": \"literal\" , \"value\": \"cost statement\" } ,\r\n" + 
				"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"cost; costs; expense; expenses; statement\" }\r\n" + 
				"      }"));
		
		AbstractAgent sentenceAgent = new SentenceAgent(); 
		AbstractAgent tokenAgent = new TokenizeAgent(); 
		AbstractAgent applicationAgent = new ActivityAgent(); 
		AbstractAgent requestAgent = new RequestAgent();

		
		tokenAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "1"));
		sentenceAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "2"));
		applicationAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "3"));
		requestAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		
		
		tokenAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		sentenceAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		//drAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		applicationAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		requestAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		
		
		StreamingExecution.add(tokenAgent);
		StreamingExecution.add(sentenceAgent);
		//StreamingExecution.add(drAgent);
		StreamingExecution.add(applicationAgent);
		StreamingExecution.add(requestAgent);

		
		//StreamingExecution.add(documentProposalAgent);
		
		Runnable myRunnable = new Runnable() {
			public void run() {
				try {
					StreamingExecution.start();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		};
		Runnable webSocketserver = new Runnable() {
			public void run() {
				SocketServer.main(null); 
				
				
			}
		};
		Runnable dritterthread = new Runnable() {
			public void run() {
//				try {
//					//publishDemoEvents();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} 
				
				
			}
		};
		
		// Thread wird erzeugt und gestartet
		Thread thread = new Thread(myRunnable);
		Thread thread2 = new Thread(webSocketserver);
		Thread thread3 = new Thread(dritterthread);
		
		thread.start();
		thread2.start();
		thread3.start();
	 }

	
	private static void publish(AbstractEvent event, String topic) {
		LoggerFactory.getLogger("StartServices!");				
		String message = messageMapper.toJSON(event);	
		if(message != null && topic != null) {
			despatcher.deliver(message, topic);	
		}
		
	}

	
	private static void publishDemoEvents() throws InterruptedException {		
			
			for (int i = 8; i < 9; i++) {
					
					// TODO: A response can be added in the future which is caught in the JS Script and shown on the website
					// TODO: Parameter entsprechend im NodeJS anpassen
					String JsSentence = ""; 
					String userID = "";
					
					switch (i) {
					case 0:
						 JsSentence = "Let's talk about drive current activities concerning HighNet project."; 
						 userID = "lisa@gmail.com";
						break;
					case 1:
						JsSentence = "Ok. Shall we look at the tasks leading to the milestone ahead?"; 
						 userID = "haruki@gmail.com";
						break;
					case 2:
						JsSentence = "Sure. We have been working on network issues for the diagnosis module. It is item 3 on the task list. I think, we will come up with something viable shortly."; 
						 userID = "lisa@gmail.com";
						break;
					case 3:
						JsSentence = "That sounds great. What about expenses? Do you think, you will be able to stay within the limits we aggreed upon last week?"; 
						 userID = "haruki@gmail.com";
						break;
					case 4:
						JsSentence = "That should be no problem. I'll leave a detailed report on Google drive."; 
						 userID = "lisa@gmail.com";
						break;
					case 5:
						JsSentence = "Ok, thanks. Let's make an appointment for our next meeting."; 
						 userID = "haruki@gmail.com";
						break;
					case 6:
						JsSentence = "Let me check my calendar â€¦. How about next Thursday at 16 hours your time?"; 
						 userID = "lisa@gmail.com";
						break;
					case 7:
						JsSentence = "Perfect. See you then. Bye."; 
						 userID = "haruki@gmail.com";
					break;

					default:
						//JsSentence = "Highnet, Daimler, costs, milestone, calendar, Google Drive, Google Calendar, google docs, powerpoint, Word";
						JsSentence = "house project tasks leading to the milestone ahead?";
						
						userID = "lisa@gmail.com";
						break;
					}
			
//									
//					String sessionID = "Session1";
//					// To execute the other class and its dependencies it is important to add these dependencies under "Deployment Assembly"
//					
//					AbstractEvent wat = eventFactory.createEvent("AtomicEvent");
//					
//					wat.setType("WatsonEvent");
//					wat.add(new Property<String>("Sentence", JsSentence));
//					wat.add(new Property<String>("UserID", userID));// Hier die Properties an das neue Event Ã¼bergebenÃ¼bergeben
//					wat.add(new Property<String>("SentenceID", "5"));// Hier die Properties an das neue Event Ã¼bergebenÃ¼bergeben
//					wat.add(new Property<Timestamp>("Timestamp", wat.getCreationDate()));
//					wat.add(new Property<String>("SessionID", sessionID));
//					//String message = messageMapper.toJSON(wat);
//					
//					publish(wat, "ChunkGeneration");
//					AbstractEvent sessionStart = eventFactory.createEvent("AtomicEvent");
//					sessionStart.setType("SessionStartEvent");
//					sessionStart.add(new Property<String>("12423432434", "sessionID"));
//					publish(sessionStart, "SessionState");
//					//despatcher.deliver(message, "ChunkGeneration");
//					
//					Thread.sleep(1000);
					

				}
				

				
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
	
