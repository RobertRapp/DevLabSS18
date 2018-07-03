package startServices;

import java.sql.Timestamp;

import com.speechTokens.EvE.agents.NoKeywordAgent;
import com.speechTokens.EvE.agents.SentenceAgent;
import com.speechTokens.EvE.agents.SeveralKeywordsAgent;
import com.speechTokens.EvE.agents.SingleKeywordAgent;
import com.speechTokens.EvE.agents.TokenizeAgent;
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

/**
 * Startpunkt der Anwendung.
 * 
 * hier werden die Agenten initialisiert und die Sparkumgebung ausgefÃƒÂ¼hrt.
 * 
 * @author RobertRapp
 *
 */
public class StartServicesWithAdhocAgents {
	private static Despatcher despatcher = null;
	// wandelt die Events in Nachrichten um.
	private static final MessageMapper messageMapper = new MessageMapper();
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	
	public static void main(String[] args) throws NoValidAgentException, InterruptedException
	 {
		
		despatcher = new Despatcher(new ProducerSettings("10.142.0.2","9092"));
						
		StreamingExecution.add(getAdhocAgent("Agent1", "Agent2", false));
		StreamingExecution.add(getAdhocAgent("Agent2", "Agent3", false));
		StreamingExecution.add(getAdhocAgent("Agent3", "Agent4", false));
		StreamingExecution.add(getAdhocAgent("Agent4", "Agent5", false));
		StreamingExecution.add(getAdhocAgent("Agent5", "Agent6", false));
		StreamingExecution.add(getAdhocAgent("Agent6", "Agent7", false));
				
		Runnable myRunnable = new Runnable() {
			public void run() {
				try {
					publishDemoEvents("Agent1");
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
	
	private static AbstractAgent getAdhocAgent(String name, String zielTopic, boolean localhostFragezeichen) {
		//DR AGENT -------------------------------------------
		/*
		 * Alle Zeilen die linksbündig sind müssen bearbeitet werden. Dieser Agent konsumiert vom Topic gleich dem Name des Agenten und sendet das Event einfach weiter an angegebenes Topic.
		 */
			
			AbstractAgent drAgent = new AdhocAgent(name, zielTopic, localhostFragezeichen);
						//System.out.println(drAgent);
		return drAgent;
	}
	
	
	private static void publish(AbstractEvent event, String topic) {
		LoggerFactory.getLogger("StartServices!");				
		String message = messageMapper.toJSON(event);	
		if(message != null && topic != null) {
			despatcher.deliver(message, topic);	
		}
	}
	
	private static void publishDemoEvents(String zielTopic) throws InterruptedException {		
			
			for (int i = 8; i < 9; i++) {
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
						JsSentence = "Highnet, Daimler, costs, milestone, calendar, Google Drive, Google Calendar, google docs, powerpoint, Word";
						userID = "lisa@gmail.com";
						break;
					}
								
					AbstractEvent wat = eventFactory.createEvent("AtomicEvent");					
					wat.setType("WatsonEvent");
					wat.add(new Property<String>("Sentence", JsSentence));
					wat.add(new Property<String>("UserID", userID));
					wat.add(new Property<String>("SentenceID", "0"+i));// Hier die Properties an das neue Event Ã¼bergebenÃ¼bergeben
					wat.add(new Property<Timestamp>("Timestamp", wat.getCreationDate()));
					wat.add(new Property<String>("SessionID", "session"+i));
					wat.setSource(zielTopic);					
					publish(wat, zielTopic);
					Thread.sleep(5000);					

				}
	}

}	 
	