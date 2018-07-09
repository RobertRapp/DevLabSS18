package eventprocessing.agent.GuiAgent;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.ServerConnector;
import org.json.JSONArray;
import org.json.JSONObject;
import org.spark_project.jetty.server.Server;


import eventprocessing.agent.AbstractAgent;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.agent.interestprofile.predicates.statement.IsFromTopic;
import eventprocessing.produce.kafka.Despatcher;
import eventprocessing.produce.kafka.ProducerSettings;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.model.EventUtils;

import eventprocessing.utils.*;

/**
 * Dieser Agent ist für die die Verteilung der Events welche auf das Gui Topic 
 * gesendet werden, zuständig.
 * 
 * 
 *
 */


public class GuiAgent extends AbstractAgent {
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static final long serialVersionUID = 5414649241552569623L;
	private static Set<Session> userSessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
	private static HashMap<String, String> users = new HashMap<>();
	private static DocumentProposal proposal = new DocumentProposal();
	
	static ScheduledExecutorService timer = 
	       Executors.newSingleThreadScheduledExecutor(); 

	@Override
	protected void doOnInit() {
		
		this.setId("GuiAgent");
//		Server server = new Server();
//        ServerConnector connector = new ServerConnector(server);
//        connector.setPort(8090);
//        server.setConnectors(new Connector[] {connector});
//		
		
		
//		 Server server = new Server(8080);
//
//	        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//	        context.setContextPath("/");
//	        server.setHandler(context);
//
//	        // Add websocket servlet
//	        ServletHolder wsHolder = new ServletHolder("echo",new EchoSocketServlet());
//	        context.addServlet(wsHolder,"/echo");
		
		
		
		/*
		 * Fügt dem Agenten ein InteressenProfil hinzu Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		try {
			System.out.println("Gui agent initialisiert");
			AbstractInterestProfile ip = new GuiInterestProfileDocProposal();
			//ip.add(new IsFromTopic("Gui"));
			ip.add(new IsEventType("JsonDocEvent"));
			//ip.add(new GetEverything());
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
//		
//		try {
//			AbstractInterestProfile ip = new GuiInterestProfileUserInteraction();
//			ip.add(new IsEventType("UserInteractionEvent"));
//			//ip.add(new GetEverything());
//			this.add(ip);
//		} catch (NoValidInterestProfileException e1) {
//			e1.printStackTrace();
//		}
//		
//		try {
//			AbstractInterestProfile ip = new GuiInterestProfileSessionEnd();
//			ip.add(new IsEventType("SessionEndEvent"));
//			//ip.add(new GetEverything());
//			this.add(ip);
//		} catch (NoValidInterestProfileException e1) {
//			e1.printStackTrace();
//		}
//		
//		try {
//			AbstractInterestProfile ip = new GuiInterestProfileSessionStart();
//			ip.add(new IsEventType("SessionStartEvent"));
//			//ip.add(new GetEverything());
//			this.add(ip);
//		} catch (NoValidInterestProfileException e1) {
//			e1.printStackTrace();
//		}
		
	
		try {
			this.add("Gui");
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}

	}
//	 @OnOpen
//	    public void onOpen(Session userSession) {
//	    System.out.println("Neue Verbindung aufgebaut..." + userSession.getId());
//	    userSessions.add(userSession);
////
////	      if (userSessions.size()==1){   
////	        timer.scheduleAtFixedRate(
////	        	() -> broadcast(json.toString()),0,8,TimeUnit.SECONDS);    
////	      }
//	     }    
//	 	
//
//	    @OnClose
//	    public void onClose(Session userSession) {
//	    System.out.println("Verbindung getrennt..."+ users.get(userSession.getId()));
//	    userSessions.remove(userSession);
//	    users.remove(userSession.getId());
//	    
//	    JSONObject usersJSON = new JSONObject();
//		JSONArray usersArray = new JSONArray();
//		usersJSON.put("type", "refreshUserList");
//		 for (Map.Entry user : users.entrySet()) {
//			 usersArray.put(user.getValue());
//	         System.out.println("Key: "+user.getKey() + " & Value: " + user.getValue());
//	          
//	        }
// usersJSON.put("users", usersArray);
//	System.out.println(usersJSON);
//	broadcast(usersJSON.toString());
//	    }
//	 
//	    @OnMessage
//	    public void onMessage(String message, Session userSession) {
//	    JSONObject requestJSON = new JSONObject(message);	
//
//	    switch (requestJSON.getString("type")) {	    
//	    	case "join":
//	    		//Messages an Websocket
//	    		users.put(userSession.getId(), requestJSON.getString("username"));
//	    	    JSONObject usersJSON = new JSONObject();
//	    		JSONArray usersArray = new JSONArray();
//	    		usersJSON.put("type", "refreshUserList");
//	    		 for (Map.Entry user : users.entrySet()) {
//	    			 usersArray.put(user.getValue());    			 
//	    	         System.out.println("Key: "+user.getKey() + " & Value: " + user.getValue());	    	          
//	    	        }
//	        usersJSON.put("users", usersArray);
//	    	System.out.println(usersJSON);
//	    	broadcast(usersJSON.toString());
//			break;
//		    case "clickedOnDocument":
//		    	//Messages an Websocket
////		    	JSONObject request = new JSONObject();
////		    	request.put("type", "clickedOnDocument");
////		    	request.put("docID", requestJSON.getString("docID"));
////		    	broadcast(request.toString());
//		    	//Events an Kafka
//		    	AbstractEvent userInteractionEvent = eventFactory.createEvent("AtomicEvent");
//		    	userInteractionEvent.setType("UserInteractionEvent");
//		    	userInteractionEvent.add(new Property <>("userID", requestJSON.getString("userID")));
//		    	userInteractionEvent.add(new Property <>("FileID", requestJSON.getString("docID")));
//		    	userInteractionEvent.add(new Property <>("DocumentName", requestJSON.getString("docName")));
//		    	
//		    	try {
//					send(userInteractionEvent, "UserInteraction");
//				} catch (NoValidEventException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (NoValidTargetTopicException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		    break;
//		    case "watson":
//		    	//Events an Kafka
//		    	AbstractEvent watsonEvent = eventFactory.createEvent("AtomicEvent");
//		    	watsonEvent.setType("WatsonEvent");
//				watsonEvent.add(new Property<>("UserID",requestJSON.getString("userID")));
//				watsonEvent.add(new Property<>("SessionID", requestJSON.getString("sessionID")));
//				watsonEvent.add(new Property<>("Sentence", requestJSON.getString("sentence")));
//				watsonEvent.add(new Property<>("SentenceID", requestJSON.getString("sentenceID")));
//				
//				try {
//					send(watsonEvent, "ChunkGeneration");
//				} catch (NoValidEventException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (NoValidTargetTopicException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		break;
//		    case "sessionStart":
//		    	//Messages an Websocket
////				request = new JSONObject();
//		    	requestJSON.put("type", "sessionStarted");
//		    	requestJSON.put("msg", "bin drinnen");
//		    	requestJSON.put("sessionID", requestJSON.getString("sessionID"));
//				broadcastOthers(requestJSON.toString(), userSession);
//				System.out.println(requestJSON.toString());
//				//Events an Kafka
//				AbstractEvent sessionStartEvent = eventFactory.createEvent("AtomicEvent");
//		    	sessionStartEvent.setType("SessionStartEvent");
//		    	sessionStartEvent.add(new Property<>("SessionStart", TimeUtils.getCurrentTime()));
//		    	sessionStartEvent.add(new Property <>("SessionID",requestJSON.getString("sessionID")));
//		    	sessionStartEvent.add(new Property <>("UserID",requestJSON.getString("userID")));
//		    	
//		    	try {
//					send(sessionStartEvent, "SessionState");
//				} catch (NoValidEventException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (NoValidTargetTopicException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			break;	
//			case "sessionEnd":
//				//Messages an Websocket
//				System.out.println("Session "+requestJSON.getString("sessionID")+ " beendet von: "+requestJSON.getString("userID"));
//				requestJSON = new JSONObject();
//				requestJSON.put("type", "sessionEnded");
//				requestJSON.put("sessionID", requestJSON.getString("sessionID"));
//				broadcastOthers(requestJSON.toString(), userSession);
//				//Events an Kafka
//				AbstractEvent sessionEndEvent = eventFactory.createEvent("AtomicEvent");
//		    	sessionEndEvent.setType("SessionEndEvent");
//		    	sessionEndEvent.add(new Property<>("SessionEnd", TimeUtils.getCurrentTime()));
//		    	sessionEndEvent.add(new Property<>("SessionID",requestJSON.getString("sessionID")));
//		    	sessionEndEvent.add(new Property <>("UserID",requestJSON.getString("userID")));
//		    	
//		    	try {
//					send(sessionEndEvent, "SessionState");
//				} catch (NoValidEventException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (NoValidTargetTopicException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			break;						    	
//	    }
// }
//
//	@OnError
//	public void onError(Throwable e){
//		e.printStackTrace();
//	}
//	
//
//	public static void broadcast(String msg) {
//	    System.out.println("Broadcast Nachricht an alle:" + msg);
//	    for (Session session : userSessions) {
//	        session.getAsyncRemote().sendText(msg);
//	        System.out.println("Sesion ID: " + session.getId());
//	    }
//	}
//	public static void broadcastOthers(String msg, Session userSession) {
//		System.out.println("Broadcast Nachricht an alle ausser mir:" + msg);		
//		userSessions.remove(userSession);
//		for (Session session : userSessions) {
//			session.getAsyncRemote().sendText(msg);
//			System.out.println("Sesion ID: " + session.getId());
//		}
//		userSessions.add(userSession);
//	}
//	public static void addAndPublishDocsToProposalList(ArrayList<Document> d) {
//		proposal.addDocuments(d);
//		JSONObject json = proposal.toJson();
//		String jsonString = json.toString();
//		broadcast(jsonString);
//		}
}
