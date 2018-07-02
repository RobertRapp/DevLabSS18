package startServices;

import java.sql.Timestamp;
import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
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
import eventprocessing.utils.mapping.MessageMapper;
import hdm.developmentlab.ebi.eve_implementation.activityService.ActivityAgent;
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
		//AbstractAgent sessionContextAgent1 = new SessionContextAgent();
		//AbstractAgent sessionContextAgent2 = new SessionContextAgent();
		//AbstractAgent sessionContextAgent3 = new SessionContextAgent(); 
		AbstractAgent protocolAgent1 = new ProtocolAgent();
		//AbstractAgent activityAgent1 = new ActivityAgent();
		//AbstractAgent activityAgent2 = new ActivityAgent();	
		
		
		//ST
		/*
		AbstractAgent sentenceAgent = new SentenceAgent(); 
		AbstractAgent tokenAgent = new TokenizeAgent(); 
		*/
		//DR
		AbstractAgent drAgent  = new AbstractAgent() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 123414151242L;

			@Override
			protected void doOnInit() {
				this.setId("DRAgent");
				AbstractInterestProfile ip = new User();
				ip.add(new IsEventType("SentenceEvent"));
				try {
					this.add(ip);
				} catch (NoValidInterestProfileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					this.add("ChunkGeneration");
				} catch (NoValidConsumingTopicException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
				
		//GUI
		/*
		AbstractAgent documentPro = new  DocProposal();
		AbstractAgent guiAgent = new GuiAgent();
		*/
		
		
		/*
		 * ConsumerSettings
		 */
		//EBI 
//		 sessionContextAgent1.setConsumerSettings(new ConsumerSettings("localhost", "9092", "context"));
//		 sessionContextAgent2.setConsumerSettings(new ConsumerSettings("localhost", "9092", "context"));
//		 sessionContextAgent3.setConsumerSettings(new ConsumerSettings("localhost", "9092", "context"));
		 protocolAgent1.setConsumerSettings(new ConsumerSettings("localhost", "9092", "protocol"));
//		 activityAgent1.setConsumerSettings(new ConsumerSettings("localhost", "9092", "acti"));
//		 activityAgent2.setConsumerSettings(new ConsumerSettings("localhost", "9092", "acti"));
		
		//ST
		 //tokenAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "token"));
		 //sentenceAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "sentence"));
		//DR
		 drAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "dr"));
		//GUI
//		 documentPro.setConsumerSettings(new ConsumerSettings("localhost", "9092", "docPro"));
//		 guiAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "gui"));
		
		
		/*
		 * ProducerSettings müssen für jeden Agenten einzelnd gesetzt sein sonst geht es nicht. 
		 */
		
		
		//EBI 
//		 sessionContextAgent1.setProducerSettings(new ProducerSettings("localhost","9092"));
//		 sessionContextAgent2.setProducerSettings(new ProducerSettings("localhost","9092"));
//		 sessionContextAgent3.setProducerSettings(new ProducerSettings("localhost","9092"));
		 protocolAgent1.setProducerSettings(new ProducerSettings("localhost","9092"));
//		 activityAgent1.setProducerSettings(new ProducerSettings("localhost","9092"));
//		 activityAgent2.setProducerSettings(new ProducerSettings("localhost","9092"));
		
		//ST
		 //tokenAgent.setProducerSettings(pSet);
		 //sentenceAgent.setProducerSettings(pSet);
		//DR
		 drAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		//GUI
//		 documentPro.setProducerSettings(pSet);
//		 guiAgent.setProducerSettings(pSet);	
	
		 /*
		  * Hinzufügen StreamingExecution
		  * 
		  */
		//EBI
		//StreamingExecution.add(sessionContextAgent1);
		//StreamingExecution.add(sessionContextAgent2);
//				StreamingExecution.add(sessionContextAgent3);
		StreamingExecution.add(protocolAgent1);
//		StreamingExecution.add(activityAgent1);
//		StreamingExecution.add(activityAgent2);
		
		//ST
		//StreamingExecution.add(tokenAgent);
		//StreamingExecution.add(sentenceAgent);
		
		//DR
		StreamingExecution.add(drAgent);
		//GUI
		//StreamingExecution.add(documentPro);
		//StreamingExecution.add(guiAgent);
		

		
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
		if(message != null && topic != null) {
			despatcher.deliver(message, topic);	
		}
		
	}

	
	private static void publishDemoEvents() throws InterruptedException {		
			
			for (int i = 0; i < 9; i++) {
				
				// TODO: A response can be added in the future which is caught in the JS Script and shown on the website
				// TODO: Parameter entsprechend im NodeJS anpassen
				String JsSentence = ""; 
				String userID = "";
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
				
									
				String sessionID = "Session1";
				// To execute the other class and its dependencies it is important to add these dependencies under "Deployment Assembly"
				
				AbstractEvent wat = eventFactory.createEvent("AtomicEvent");
				wat.setType("WatsonEvent");
				wat.add(new Property<String>("sentence", JsSentence));
				wat.add(new Property<String>("UserID", userID));// Hier die Properties an das neue Event übergebenübergeben
				wat.add(new Property<Timestamp>("Timestamp", wat.getCreationDate()));
				wat.add(new Property<String>("SessionID", sessionID));
				String message = messageMapper.toJSON(wat);
				despatcher.deliver(message, "ChunkGeneration");
				
				
				Thread.sleep(1000);
				
			}
			

	}	 
	
}