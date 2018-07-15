package startServices;

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
import eventprocessing.agent.UserInteraction.UserInteraction;
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
import eventprocessing.utils.SocketServer;
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
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.SessionContextAgent;
import saveDocumentService.SaveDocumentAgent;
import semanticService.SemanticAgent;

/**
 * Startpunkt der Anwendung.
 * 
 * hier werden die Agenten initialisiert und die Sparkumgebung ausgefÃƒÂ¼hrt.
 * 
 * @author RobertRapp
 *
 */
public class StartServicesGUITest {


		
		 // FÃƒÂ¼r die Versendung der DemoEvents an das Topic nÃ¶tig.
	private static Despatcher despatcher = null;
	// wandelt die Events in Nachrichten um.
	private static final MessageMapper messageMapper = new MessageMapper();
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static AbstractFactory agentFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getAgentFactory());
	
	
	public static void main(String[] args) throws NoValidAgentException, InterruptedException
	 {
		despatcher = new Despatcher(new ProducerSettings("localhost","9092"));
		

		AbstractAgent sentenceAgent = new SentenceAgent(); //
		AbstractAgent tokenAgent = new TokenizeAgent(); //
		AbstractAgent applicationAgent = new ActivityAgent(); //
		AbstractAgent requestAgent = new RequestAgent(); //
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
				StreamingExecution.add(documentProposalAgent);
				StreamingExecution.add(saveDocumentAgent);
			} catch (AgentException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			

		
		tokenAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "1"));
		sentenceAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "2"));
		applicationAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "3"));
		singleKeyWordAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "4"));
		noKeywordAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "5"));
		severalKeywordsAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "6"));
		requestAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "7")); 
		protcolAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "8"));
		semanticChunksIP.setConsumerSettings(new ConsumerSettings("localhost", "9092", "9"));
		sessionstateAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "10"));
		documentProposalAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "11"));
		guiAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "12"));
		docProposalAgent.setConsumerSettings(new ConsumerSettings("localhost", "9092", "13"));
		
		
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
		} catch (AgentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
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
	