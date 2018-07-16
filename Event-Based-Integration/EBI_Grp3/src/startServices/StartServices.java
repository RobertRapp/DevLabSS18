package startServices;

import com.speechTokens.EvE.agents.NoKeywordAgent;
import com.speechTokens.EvE.agents.SentenceAgent;
import com.speechTokens.EvE.agents.SeveralKeywordsAgent;
import com.speechTokens.EvE.agents.SingleKeywordAgent;
import com.speechTokens.EvE.agents.TokenizeAgent;

import documentProposalService.DocumentProposalAgent;
import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.AgentException;
import eventprocessing.agent.DocProposal.DocProposalAgent;
import eventprocessing.agent.GuiAgent.GuiAgent;
import eventprocessing.consume.kafka.ConsumerSettings;
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
import hdm.developmentlab.ebi.eve_implementation.activityService.ActivityAgent;
import hdm.developmentlab.ebi.eve_implementation.activityService.RequestAgent;
import hdm.developmentlab.ebi.eve_implementation.protocolService.ProtocolAgent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.SessionContextAgent;
import saveDocumentService.SaveDocumentAgent;
import semanticService.SemanticAgent;
import startFuseki.StartFusekiAndOntology;

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
	
	
	public static void main(String[] args) throws AgentException, InterruptedException
	 {
		//IP-Adresse des Kafka-Servers					
		String kafkahost = "10.142.0.2"; 
			   if(args[1].length() > 7) kafkahost = args[1]; //Überschreibt Default wenn ein Wert gegeben ist. 
		
		
		switch (args[0].toLowerCase()) {
		case "tomcat":
			System.out.println("Parameter tomcat aufgerufen: semanticAgent, saveDocumentAgent, documentProposalAgent und der FusekiServer gestartet");
			//TOMCAT 
			AbstractAgent semanticAgent = new SemanticAgent();
			AbstractAgent saveDocAgent = new SaveDocumentAgent();
			AbstractAgent documentProposalAgent = new DocumentProposalAgent();
			
			
			//TOMCAT CONSUMER / PRODUCER SETTINGS
			
			semanticAgent.setConsumerSettings(new ConsumerSettings(kafkahost, "9092", "9"));			
			documentProposalAgent.setConsumerSettings(new ConsumerSettings(kafkahost, "9092", "11"));		
			saveDocAgent.setConsumerSettings(new ConsumerSettings(kafkahost, "9092", "14"));			
			saveDocAgent.setProducerSettings(new ProducerSettings(kafkahost, "9092"));
			documentProposalAgent.setProducerSettings(new ProducerSettings(kafkahost,"9092"));
			semanticAgent.setProducerSettings(new ProducerSettings(kafkahost,"9092"));
			
			//TOMCAT
			StreamingExecution.add(semanticAgent);			
			StreamingExecution.add(documentProposalAgent);
			StreamingExecution.add(saveDocAgent);
			
			Runnable ontologyServer = new Runnable() {
				public void run() {
					StartFusekiAndOntology.main(null);	
				}
			};
			
			Runnable tomcat = new Runnable() {
				public void run() {
					try {
						StreamingExecution.start();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} 
				}
			};
			
			Thread thread = new Thread(tomcat);					
			thread.start();	
			Thread ontologythread = new Thread(ontologyServer);
			ontologythread.start();
			break;
		case "ux":
			//UX
			System.out.print("ux als Parameter eingegeben. singleKeyWordAgent, noKeywordAgent, severalKeywordsAgent,"
					+ " sentenceAgent, tokenAgent und applicationAgent gestartet.");
			
			AbstractAgent singleKeyWordAgent = new SingleKeywordAgent();
			AbstractAgent noKeywordAgent = new NoKeywordAgent();
			AbstractAgent severalKeywordsAgent = new SeveralKeywordsAgent();	
			AbstractAgent sentenceAgent = new SentenceAgent();
			AbstractAgent tokenAgent = new TokenizeAgent(); //
			AbstractAgent applicationAgent = new ActivityAgent();

			//UX CONSUMER / PRODUCER SETTINGS
			
			tokenAgent.setConsumerSettings(new ConsumerSettings(kafkahost, "9092", "1"));
			sentenceAgent.setConsumerSettings(new ConsumerSettings(kafkahost, "9092", "2"));
			applicationAgent.setConsumerSettings(new ConsumerSettings(kafkahost, "9092", "3"));
			singleKeyWordAgent.setConsumerSettings(new ConsumerSettings(kafkahost, "9092", "4"));
			noKeywordAgent.setConsumerSettings(new ConsumerSettings(kafkahost, "9092", "5"));
			severalKeywordsAgent.setConsumerSettings(new ConsumerSettings(kafkahost, "9092", "6"));
			
			tokenAgent.setProducerSettings(new ProducerSettings(kafkahost,"9092"));
			sentenceAgent.setProducerSettings(new ProducerSettings(kafkahost,"9092"));		
			applicationAgent.setProducerSettings(new ProducerSettings(kafkahost,"9092"));
			singleKeyWordAgent.setProducerSettings(new ProducerSettings(kafkahost,"9092"));
			noKeywordAgent.setProducerSettings(new ProducerSettings(kafkahost,"9092"));
			severalKeywordsAgent.setProducerSettings(new ProducerSettings(kafkahost,"9092"));
			
			//UX
			StreamingExecution.add(tokenAgent);
			StreamingExecution.add(sentenceAgent);
			StreamingExecution.add(applicationAgent);
			StreamingExecution.add(singleKeyWordAgent);
			StreamingExecution.add(noKeywordAgent);
			StreamingExecution.add(severalKeywordsAgent);	
			
			Runnable ux = new Runnable() {
				public void run() {
					try {
						StreamingExecution.start();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} 
				}
			};
			Thread thread1 = new Thread(ux);					
			thread1.start();	
			
			break;

		case "spark":
			//SPARK
			System.out.print("Spark als Parameter eingegeben. sessionStateAgent, GuiAgent, ProtocolAgent,"
					+ " RequestAgent, DocProposalAgent gestartet.");
			AbstractAgent sessionstateAgent = new SessionContextAgent();
			AbstractAgent guiAgent = new GuiAgent();
			AbstractAgent protcolAgent = new ProtocolAgent();
			AbstractAgent requestAgent = new RequestAgent();
			AbstractAgent docProposalAgent = new DocProposalAgent();
			
			requestAgent.setConsumerSettings(new ConsumerSettings(kafkahost, "9092", "7")); 
			protcolAgent.setConsumerSettings(new ConsumerSettings(kafkahost, "9092", "8"));
			guiAgent.setConsumerSettings(new ConsumerSettings(kafkahost, "9092", "12"));
			docProposalAgent.setConsumerSettings(new ConsumerSettings(kafkahost, "9092", "13"));
			
			requestAgent.setProducerSettings(new ProducerSettings(kafkahost,"9092"));
			protcolAgent.setProducerSettings(new ProducerSettings(kafkahost,"9092"));
			guiAgent.setProducerSettings(new ProducerSettings(kafkahost,"9092"));
			docProposalAgent.setProducerSettings(new ProducerSettings(kafkahost,"9092"));			
			sessionstateAgent.setConsumerSettings(new ConsumerSettings(kafkahost, "9092", "10"));
			sessionstateAgent.setProducerSettings(new ProducerSettings(kafkahost,"9092"));
			
			//SPARK
			StreamingExecution.add(guiAgent);
			StreamingExecution.add(docProposalAgent);
			StreamingExecution.add(requestAgent);
			StreamingExecution.add(protcolAgent);
			StreamingExecution.add(sessionstateAgent);			
			
			Runnable spark = new Runnable() {
				public void run() {
					try {
						StreamingExecution.start();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} 
				}
			};
					
			// Thread wird erzeugt und gestartet
			Thread thread3 = new Thread(spark);					
			thread3.start();		
			System.out.println("Spark Thread Status: "+thread3.getState());
			break;
			
		case "websocket":
			Thread thread2;
			System.out.println("Websocket als Parameter -- starte Websocket");
			Runnable webSocketserver = new Runnable() {
				public void run() {
					SocketServer.main(null); 					
				}
			};
			thread2 = new Thread(webSocketserver);
			thread2.start();
			System.out.println("Websocket Thread Status: "+thread2.getState());
			break;
		default:
			System.out.println("ACHTUNG: Es muss je nach Server tomcat, ux oder spark als args Parameter angegeben werden.");
			
			break;
		}
				
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
						JsSentence = "house project tasks leading to the milestone ahead?";
						
						userID = "lisa@gmail.com";
						break;
					}
			

					

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
	
