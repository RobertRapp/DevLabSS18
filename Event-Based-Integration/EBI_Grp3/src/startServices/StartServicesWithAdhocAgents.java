package startServices;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.speechTokens.EvE.agents.SeveralKeywordsAgent;
import com.speechTokens.tokenizer.Chunker;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.agent.AgentException;
import eventprocessing.consume.spark.streaming.StreamingExecution;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.produce.kafka.Despatcher;
import eventprocessing.produce.kafka.ProducerSettings;
import eventprocessing.utils.TimeUtils;
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
	
	public static void main(String[] args) throws AgentException, InterruptedException
	 {
		
		despatcher = new Despatcher(new ProducerSettings("localhost","9092"));
//		AbstractAgent request = new RequestAgent();
//		AbstractAgent singlekeywordAgent = new SeveralKeywordsAgent();
//		request.setConsumerSettings(new ConsumerSettings("10.142.0.2", "9092", "req"));
//		request.setProducerSettings(new ProducerSettings("10.142.0.2","9092"));
//		singlekeywordAgent.setConsumerSettings(new ConsumerSettings("10.142.0.2", "9092", "sk"));
//		singlekeywordAgent.setProducerSettings(new ProducerSettings("10.142.0.2","9092"));
//		StreamingExecution.add(request);				
//		StreamingExecution.add(singlekeywordAgent);
		StreamingExecution.add(getAdhocAgent("Agent1", "Agent2", true));
		StreamingExecution.add(getAdhocAgent("Agent2", "Agent3", true));
		StreamingExecution.add(getAdhocAgent("Agent3", "Agent4", true));
//		StreamingExecution.add(getAdhocAgent("Agent4", "Agent5", true));
//		StreamingExecution.add(getAdhocAgent("Agent5", "Agent6", true));
//		StreamingExecution.add(getAdhocAgent("Agent6", "Agent7", true));
//		StreamingExecution.add(getAdhocAgent("Agent7", "Agent8", true));
//		StreamingExecution.add(getAdhocAgent("Agent8", "Agent9", true));
//		StreamingExecution.add(getAdhocAgent("Agent9", "Agent10", true));
//		StreamingExecution.add(getAdhocAgent("Agent10", "Agent11", true));
		
		Runnable myRunnable = new Runnable() {
			public void run() {
				try {
					publishDemoEvents(null, "Agent1");
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
				
		String message = messageMapper.toJSON(event);	
		if(message != null && topic != null) {
			despatcher.deliver(message, topic);	
		}
	}
	
	
	private static void publishDemoEvents(AbstractEvent event, String zielTopic) throws InterruptedException {		
		AbstractEvent wat = eventFactory.createEvent("AtomicEvent");					
		wat.setType("WatsonEvent");
		wat.add(new Property<String>("IrgendeinText", "Das Wetter ist heute nicht sonnig"));
		wat.add(new Property<Long>("gesendetUm", TimeUtils.getCurrentTime().getTime()));
		wat.setSource(zielTopic);	
		for (int i = 0; i < 1; i++) {	
				wat.setId(i);
				publish(wat, zielTopic);
				Thread.sleep(50);					

			}
		}
	

}	 
	
