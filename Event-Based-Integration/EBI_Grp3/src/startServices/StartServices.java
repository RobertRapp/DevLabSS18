package startServices;

import java.sql.Timestamp;
import java.util.logging.Level;

import com.speechTokens.EvE.agents.SentenceAgent;
import com.speechTokens.EvE.agents.TokenizeAgent;

import edu.stanford.nlp.simple.Sentence;
import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
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
import hdm.developmentlab.ebi.eve_implementation.activityService.ActivityAgent;
import hdm.developmentlab.ebi.eve_implementation.events.TimeReference;
import hdm.developmentlab.ebi.eve_implementation.protocolService.ProtocolAgent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.SessionContextAgent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.User;

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
	
	
	public static void main(String[] args) throws NoValidAgentException, InterruptedException
	 {
		despatcher = new Despatcher(new ProducerSettings("localhost","9092"));
		
		/*
		 * 
		 * Erstellung aller Agents:
		 */
		
		//EBI
		AbstractAgent sessionContextAgent1 = new SessionContextAgent();
		
		AbstractAgent protocolAgent1 = new ProtocolAgent();
		AbstractAgent activityAgent1 = new ActivityAgent();
		
		
		System.out.println(2);
		
		//ST
		
		AbstractAgent sentenceAgent = new SentenceAgent(); 
		AbstractAgent tokenAgent = new TokenizeAgent(); 
		System.out.println(3);
		//DR
//		AbstractAgent drAgent  = new AbstractAgent() {
//			
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			protected void doOnInit() {
//				this.setId("DRAgent");
//				AbstractInterestProfile ip = new User();
//				ip.add(new IsEventType("SentenceEvent"));
//				try {
//					this.add(ip);
//				} catch (NoValidInterestProfileException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				try {
//					this.add("ChunkGeneration");
//				} catch (NoValidConsumingTopicException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}
//		};
		System.out.println(4);
		//GUI
		//AbstractAgent documentPro = new  DocProposal();
		//AbstractAgent guiAgent = new GuiAgent();
		
		/*
		 * ConsumerSettings
		 */
		//EBI 
		System.out.println(5);
		 sessionContextAgent1.setConsumerSettings(new ConsumerSettings("localhost", "9092", "context"));
//		 sessionContextAgent2.setConsumerSettings(new ConsumerSettings("localhost", "9092", "context"));
//		 sessionContextAgent3.setConsumerSettings(new ConsumerSettings("localhost", "9092", "context"));
//		 protocolAgent1.setConsumerSettings(new ConsumerSettings("localhost", "9092", "protocol"));
		 activityAgent1.setConsumerSettings(new ConsumerSettings("localhost", "9092", "acti"));
//		 activityAgent2.setConsumerSettings(new ConsumerSettings("localhost", "9092", "acti"));
//		
		 System.out.println(6);
		//ST
		 //tokenAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "token"));
		 sentenceAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "sentence"));
		//DR
		 //drAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "dr"));
		//GUI
		 //documentPro.setConsumerSettings(new ConsumerSettings("localhost", "9092", "docPro"));
		 //guiAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "gui"));
		
		 System.out.println(7);
		/*
		 * ProducerSettings
		 */
		ProducerSettings pSet = new ProducerSettings("localhost","9092");
		System.out.println(8);
		//EBI 
		 sessionContextAgent1.setProducerSettings(pSet);
//		 sessionContextAgent2.setProducerSettings(pSet);
//		 sessionContextAgent3.setProducerSettings(pSet);
		 protocolAgent1.setProducerSettings(pSet);
		 activityAgent1.setProducerSettings(pSet);
//		 activityAgent2.setProducerSettings(pSet);
		 System.out.println(9);
		//ST
		 //tokenAgent.setProducerSettings(pSet);
		 sentenceAgent.setProducerSettings(pSet);
		//DR
		 //drAgent.setProducerSettings(pSet);
		//GUI
		 //documentPro.setProducerSettings(pSet);
		 //guiAgent.setProducerSettings(pSet);	
	
		 /*
		  * Hinzufügen StreamingExecution
		  * 
		  */
		//EBI
		//StreamingExecution.add(sessionContextAgent1);
		//StreamingExecution.add(sessionContextAgent2);
		//StreamingExecution.add(sessionContextAgent3);
		StreamingExecution.add(protocolAgent1);
		StreamingExecution.add(activityAgent1);
//		StreamingExecution.add(activityAgent2);
		System.out.println(9);
		//ST
		StreamingExecution.add(tokenAgent);
		StreamingExecution.add(sentenceAgent);
		//DR
		//StreamingExecution.add(drAgent);
		//GUI
		//StreamingExecution.add(documentPro);
		//StreamingExecution.add(guiAgent);
		
		System.out.println(10);
		
		Runnable myRunnable = new Runnable() {
			public void run() {
				try {
					publishDemoEvents();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		System.out.println(11);
		// Thread wird erzeugt und gestartet
		Thread thread = new Thread(myRunnable);
		thread.start();

		System.out.println(12);
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
		System.out.println(13);
			for (int i = 0; i < 1; i++) {
				System.out.println(14);
				// TODO: A response can be added in the future which is caught in the JS Script and shown on the website
				// TODO: Parameter entsprechend im NodeJS anpassen
				String JsSentence = ""; 
				String userID = "";
				System.out.println(15);
				switch (i) {
				case 0:
					 JsSentence = "Let´s talk about current activities concerning the HighNet project."; 
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
					JsSentence = "Let me check my calendar …. How about next Thursday at 16 hours your time?"; 
					 userID = "lisa@gmail.com";
					break;
				case 7:
					JsSentence = "Perfect. See you then. Bye."; 
					 userID = "haruki@gmail.com";
				break;

				default:
					JsSentence = "Highnet, Daimler, costs, milestone, calendar, Google Drive, Google Calendar, Google Docs, Powerpoint, Word";
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
				wat.add(new Property<String>("UserID", userID));// Hier die Properties an das neue Event übergebenübergeben
				wat.add(new Property<Timestamp>("Timestamp", wat.getCreationDate()));
				wat.add(new Property<String>("SessionID", sessionID));
				//String message = messageMapper.toJSON(wat);
				System.out.println(18);
				//publish(wat, "ChunkGeneration");
				//despatcher.deliver(message, "ChunkGeneration");
				System.out.println(19);
				Thread.sleep(1000);
				
			}
			

	}	 
	
}