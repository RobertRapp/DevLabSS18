package startServices;

import java.sql.Timestamp;

import com.speechTokens.EvE.agents.NoKeywordAgent;
import com.speechTokens.EvE.agents.SentenceAgent;
import com.speechTokens.EvE.agents.SeveralKeywordsAgent;
import com.speechTokens.EvE.agents.SingleKeywordAgent;
import com.speechTokens.EvE.agents.TokenizeAgent;
import com.speechTokens.semantic.simulation.SemanticData;
import com.speechTokens.tokenizer.Chunker;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.agent.interestprofile.predicates.statement.IsFromTopic;
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
import eventprocessing.utils.model.EventUtils;
import eventprocessing.utils.model.OWLResultUtils;
import hdm.developmentlab.ebi.eve_implementation.activityService.ActivityAgent;
import hdm.developmentlab.ebi.eve_implementation.activityService.RequestAgent;
import hdm.developmentlab.ebi.eve_implementation.protocolService.ProtocolAgent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.User;
import semanticService.SemanticAgent;

/**
 * Startpunkt der Anwendung.
 * 
 * hier werden die Agenten initialisiert und die Sparkumgebung ausgefÃƒÂ¼hrt.
 * 
 * @author RobertRapp
 *
 */
public class StartServices {


		
		 // FÃƒÂ¼r die Versendung der DemoEvents an das Topic nÃ¶tig.
	private static Despatcher despatcher = null;
	// wandelt die Events in Nachrichten um.
	private static final MessageMapper messageMapper = new MessageMapper();
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static AbstractFactory agentFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getAgentFactory());
	
	
	public static void main(String[] args) throws NoValidAgentException, InterruptedException
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
		//ST
		AbstractAgent sentenceAgent = new SentenceAgent(); 
		AbstractAgent tokenAgent = new TokenizeAgent(); 
		AbstractAgent applicationAgent = new ActivityAgent(); 
		AbstractAgent requestAgent = new RequestAgent();
		AbstractAgent protcolAgent = new ProtocolAgent();
		AbstractAgent singleKeyWordAgent = new SingleKeywordAgent();
		AbstractAgent noKeywordAgent = new NoKeywordAgent();
		AbstractAgent severalKeywordsAgent = new SeveralKeywordsAgent();
		AbstractAgent semanticChunksIP = new SemanticAgent();
		
		//DR AGENT -------------------------------------------
		/*
		 * Alle Zeilen die linksbündig sind müssen bearbeitet werden.
		 */
//			AbstractAgent drAgent  = new AbstractAgent() {
//private static final long serialVersionUID = 606360123599610899L;
//						@Override
//						protected void doOnInit() {
//this.setId("drAgent");
//						AbstractInterestProfile ip = new User();
//						//ip.add(new IsFromTopic(this.getId()));
//						ip.add(new IsEventType("SentenceEvent"));
//						try {this.add(ip);
//						} catch (NoValidInterestProfileException e) {e.printStackTrace();}
//						try {
//							//this.add(this.getId());
//							this.add("ChunkGeneration");
//						} catch (NoValidConsumingTopicException e) {e.printStackTrace();}}};
////drAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "drAgent"));
////drAgent.setProducerSettings(new ProducerSettings("localhost", "9092"));
//		
//		//DR AGENT Ende -------------------------------------------
		

		
		tokenAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "tokenagent"));
		sentenceAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "sentence"));
		applicationAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "application"));
		singleKeyWordAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "singleKeyWord"));
		noKeywordAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "noKeyword"));
		severalKeywordsAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "severalKeywords"));
		requestAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "requestAgent")); 
		protcolAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "protcolAgent"));
		semanticChunksIP.setConsumerSettings(new ConsumerSettings("localhost", "9092", "semanticChunksIP"));
		
		
		tokenAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		sentenceAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		//drAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		applicationAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		singleKeyWordAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		noKeywordAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		severalKeywordsAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		requestAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		protcolAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		semanticChunksIP.setProducerSettings(new ProducerSettings("localhost","9092"));
		
		StreamingExecution.add(tokenAgent);
		StreamingExecution.add(sentenceAgent);
		//StreamingExecution.add(drAgent);
		StreamingExecution.add(applicationAgent);
		StreamingExecution.add(singleKeyWordAgent);
		StreamingExecution.add(noKeywordAgent);
		StreamingExecution.add(severalKeywordsAgent);
		StreamingExecution.add(requestAgent);
		StreamingExecution.add(protcolAgent);
		StreamingExecution.add(semanticChunksIP);
		System.out.println("in StartService");
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
			
			for (int i = 8; i < 9; i++) {
					System.out.println(14);
					// TODO: A response can be added in the future which is caught in the JS Script and shown on the website
					// TODO: Parameter entsprechend im NodeJS anpassen
					String JsSentence = ""; 
					String userID = "";
					System.out.println(15);
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
						JsSentence = "cost from the milestone from niklas";
						
						userID = "lisa@gmail.com";
						break;
					}
			
					System.out.println(16);				
					String sessionID = "Session1";
					// To execute the other class and its dependencies it is important to add these dependencies under "Deployment Assembly"
					System.out.println(JsSentence);
					AbstractEvent wat = eventFactory.createEvent("AtomicEvent");
					System.out.println(17);
					wat.setType("WatsonEvent");
					wat.add(new Property<String>("Sentence", JsSentence));
					wat.add(new Property<String>("UserID", userID));// Hier die Properties an das neue Event Ã¼bergebenÃ¼bergeben
					wat.add(new Property<String>("SentenceID", "5"));// Hier die Properties an das neue Event Ã¼bergebenÃ¼bergeben
					wat.add(new Property<Timestamp>("Timestamp", wat.getCreationDate()));
					wat.add(new Property<String>("SessionID", sessionID));
					//String message = messageMapper.toJSON(wat);
					System.out.println(18);
					publish(wat, "ChunkGeneration");
					//despatcher.deliver(message, "ChunkGeneration");
					System.out.println(19);
					Thread.sleep(1000);
					

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
	
