package startServices;

<<<<<<< HEAD
import java.util.logging.Level;

import eventprocessing.agent.AbstractAgent;
=======
import java.sql.Timestamp;

import com.speechTokens.EvE.agents.NoKeywordAgent;
import com.speechTokens.EvE.agents.SentenceAgent;
import com.speechTokens.EvE.agents.SeveralKeywordsAgent;
import com.speechTokens.EvE.agents.SingleKeywordAgent;
import com.speechTokens.EvE.agents.TokenizeAgent;
import com.speechTokens.semantic.simulation.SemanticData;
import com.speechTokens.tokenizer.Chunker;

import documentProposalService.DocumentProposalAgent;
import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.AgentException;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.DocProposal.DocProposalAgent;
import eventprocessing.agent.GuiAgent.GuiAgent;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.agent.interestprofile.predicates.statement.IsFromTopic;
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.consume.spark.streaming.NoValidAgentException;
import eventprocessing.consume.spark.streaming.StreamingExecution;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.produce.kafka.Despatcher;
import eventprocessing.produce.kafka.ProducerSettings;
<<<<<<< HEAD
=======
import eventprocessing.utils.SocketServer;
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.mapping.MessageMapper;
<<<<<<< HEAD
import hdm.developmentlab.ebi.eve_implementation.events.TimeReference;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.SessionContextAgent;
=======
import eventprocessing.utils.model.EventUtils;
import eventprocessing.utils.model.OWLResultUtils;
import hdm.developmentlab.ebi.eve_implementation.activityService.ActivityAgent;
import hdm.developmentlab.ebi.eve_implementation.activityService.RequestAgent;
import hdm.developmentlab.ebi.eve_implementation.protocolService.ProtocolAgent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.SessionContextAgent;
import saveDocumentService.SaveDocumentAgent;
import semanticService.SemanticAgent;
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8

/**
 * Startpunkt der Anwendung.
 * 
<<<<<<< HEAD
 * hier werden die Agenten initialisiert und die Sparkumgebung ausgefÃ¼hrt.
=======
 * hier werden die Agenten initialisiert und die Sparkumgebung ausgefÃƒÂ¼hrt.
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
 * 
 * @author RobertRapp
 *
 */
public class StartServicesGUITest {


		
<<<<<<< HEAD
		 // FÃ¼r die Versendung der DemoEvents an das Topic nötig.
=======
		 // FÃƒÂ¼r die Versendung der DemoEvents an das Topic nÃ¶tig.
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
	private static Despatcher despatcher = null;
	// wandelt die Events in Nachrichten um.
	private static final MessageMapper messageMapper = new MessageMapper();
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static AbstractFactory agentFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getAgentFactory());
	
	
	public static void main(String[] args) throws NoValidAgentException, InterruptedException
	 {
<<<<<<< HEAD
		despatcher = new Despatcher(new ProducerSettings("10.142.0.2","9092"));
		AbstractAgent sessionContextAgent = new SessionContextAgent();
		
		sessionContextAgent.setConsumerSettings(new ConsumerSettings("10.142.0.2","9092", "GUiTest"));
		sessionContextAgent.setProducerSettings(new ProducerSettings("10.142.0.2","9092"));
		
			
	
	
		//StreamingExecution.add(activityService);
		//StreamingExecution.add(protocolService);
		StreamingExecution.add(sessionContextAgent);
=======
		despatcher = new Despatcher(new ProducerSettings("localhost","9092"));
		

		AbstractAgent sentenceAgent = new SentenceAgent(); //
		AbstractAgent tokenAgent = new TokenizeAgent(); 
		AbstractAgent applicationAgent = new ActivityAgent(); 
		AbstractAgent requestAgent = new RequestAgent(); 
		AbstractAgent protcolAgent = new ProtocolAgent();
		AbstractAgent singleKeyWordAgent = new SingleKeywordAgent();
		AbstractAgent noKeywordAgent = new NoKeywordAgent();
		AbstractAgent severalKeywordsAgent = new SeveralKeywordsAgent();
		AbstractAgent semanticChunksIP = new SemanticAgent();
		AbstractAgent sessionstateAgent = new SessionContextAgent();
		AbstractAgent documentProposalAgent = new DocumentProposalAgent();
		AbstractAgent guiAgent = new GuiAgent();
		AbstractAgent docProposalAgent = new DocProposalAgent();
		AbstractAgent saveDocumentAgent = new SaveDocumentAgent();
	
		tokenAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "111"));
		sentenceAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "112"));
		applicationAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "311"));
		singleKeyWordAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "411"));
		noKeywordAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "115"));
		severalKeywordsAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "611"));
		requestAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "117")); 
		protcolAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "811"));
		semanticChunksIP.setConsumerSettings(new ConsumerSettings("localhost", "9092", "911"));
		sessionstateAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "1110"));
		documentProposalAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "1111"));
		guiAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "1211"));
		docProposalAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "1113"));
		saveDocumentAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "5645645"));

		tokenAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		sentenceAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		applicationAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		singleKeyWordAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		noKeywordAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		severalKeywordsAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		requestAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		protcolAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		semanticChunksIP.setProducerSettings(new ProducerSettings("localhost","9092"));
		sessionstateAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		documentProposalAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		guiAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		docProposalAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		saveDocumentAgent.setProducerSettings(new ProducerSettings("localhost","9092"));
		
			try {
				StreamingExecution.add(tokenAgent);
				StreamingExecution.add(sentenceAgent);
				StreamingExecution.add(applicationAgent);
				StreamingExecution.add(singleKeyWordAgent);
				StreamingExecution.add(noKeywordAgent);
				StreamingExecution.add(severalKeywordsAgent);
				StreamingExecution.add(requestAgent);
				StreamingExecution.add(protcolAgent);
				StreamingExecution.add(semanticChunksIP);
				StreamingExecution.add(sessionstateAgent);
				StreamingExecution.add(guiAgent);
				StreamingExecution.add(docProposalAgent);
				StreamingExecution.add(documentProposalAgent);
				StreamingExecution.add(saveDocumentAgent);
			} catch (AgentException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8

		
		Runnable myRunnable = new Runnable() {
			public void run() {
				try {
<<<<<<< HEAD
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
			
			for (int i = 0; i < 20; i++) {
				
				
				AbstractEvent event = eventFactory.createEvent("AtomicEvent");
				event.setType("DocProposalEvent");
				Property<String> projekt = new Property<String>("projekt", "Highnet");
				Property<String> thema = new Property<String>("thema", "Kosten");
				Property<String> user = new Property<String>("user", "Robert Rapp"+i);
				Property<String> user2 = new Property<String>("user", "Detlef Gabe"+i);
				Property<TimeReference> timereference = new Property<TimeReference>("timereference", TimeReference.INSTANCE);
				event.add(projekt);			
				event.add(thema);			
				event.add(user);			
				event.add(user2);			
				event.add(timereference);			
				
				if( i == 10) {
					Property<String> context = new Property<String>("contextupdate", "Das Token ändert den Kontext");
					event.add(context);
				}
				publish(event,"test");
				
				java.util.logging.Logger logger = LoggerFactory.getLogger("StartServices!");				
				logger.log(Level.WARNING, "SESSIONSTATE AUF SESSIONSTATE GEPUSHT");
				
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
=======
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
			
	}	 
	
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
