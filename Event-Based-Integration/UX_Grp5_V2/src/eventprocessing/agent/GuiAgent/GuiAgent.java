package eventprocessing.agent.GuiAgent;

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

import org.json.JSONArray;
import org.json.JSONObject;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;



@ServerEndpoint("/socket")
public class GuiAgent extends AbstractAgent {

	private static final long serialVersionUID = 5414649241552569623L;
	private static Set<Session> userSessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
	private static HashMap<String, String> users = new HashMap<>();
    static ScheduledExecutorService timer = 
	       Executors.newSingleThreadScheduledExecutor(); 

	@Override
	protected void doOnInit() {
		
		this.setId("GuiAgent");
		//System.out.println("GuiAgent");
		/*
		 * Fügt dem Agenten ein InteressenProfil hinzu Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		try {
			AbstractInterestProfile ip = new GuiInterestProfileDocProposal();
			ip.add(new IsEventType("JsonDocEvent"));
			//ip.add(new GetEverything());
			//ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSpeedEvent()));
			this.add(ip);
			//System.out.println("ip geadded");
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
//		
//		try {
//			AbstractInterestProfile ip = new GuiInterestProfileUserInteraction();
//			ip.add(new IsEventType("UserInteractionEvent"));
//			//ip.add(new GetEverything());
//			//ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSpeedEvent()));
//			this.add(ip);
//			//System.out.println("ip geadded");
//		} catch (NoValidInterestProfileException e1) {
//			e1.printStackTrace();
//		}
//		
//		try {
//			AbstractInterestProfile ip = new GuiInterestProfileSessionEnd();
//			ip.add(new IsEventType("SessionEndEvent"));
//			//ip.add(new GetEverything());
//			//ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSpeedEvent()));
//			this.add(ip);
//			//System.out.println("ip geadded");
//		} catch (NoValidInterestProfileException e1) {
//			e1.printStackTrace();
//		}
//		
//	
		try {
			this.add("Gui");
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}

	}
	 @OnOpen
	    public void onOpen(Session userSession) {
	    System.out.println("Neue Verbindung aufgebaut..." + userSession.getId());
	    userSessions.add(userSession);
//
//	      if (userSessions.size()==1){   
//	        timer.scheduleAtFixedRate(
//	        	() -> broadcast(json.toString()),0,8,TimeUnit.SECONDS);    
//	      }
	     }    
	 	

	    @OnClose
	    public void onClose(Session userSession) {
	    System.out.println("Verbindung getrennt..."+ users.get(userSession.getId()));
	    userSessions.remove(userSession);
	    users.remove(userSession.getId());
	    
	    JSONObject usersJSON = new JSONObject();
		JSONArray usersArray = new JSONArray();
		usersJSON.put("type", "refreshUserList");
		 for (Map.Entry user : users.entrySet()) {
			 usersArray.put(user.getValue());
	         System.out.println("Key: "+user.getKey() + " & Value: " + user.getValue());
	          
	        }
 usersJSON.put("users", usersArray);
	System.out.println(usersJSON);
	broadcast(usersJSON.toString());
	    }
	 
	    @OnMessage
	    public void onMessage(String message, Session userSession) {
	    JSONObject requestJSON = new JSONObject(message);	

	    switch (requestJSON.getString("type")) {
	    	case "join":
	    		users.put(userSession.getId(), requestJSON.getString("username"));
	    	    JSONObject usersJSON = new JSONObject();
	    		JSONArray usersArray = new JSONArray();
	    		usersJSON.put("type", "refreshUserList");
	    		 for (Map.Entry user : users.entrySet()) {
	    			 usersArray.put(user.getValue());
	    			 
	    	         System.out.println("Key: "+user.getKey() + " & Value: " + user.getValue());
	    	          
	    	        }
	        usersJSON.put("users", usersArray);
	    	System.out.println(usersJSON);
	    	broadcast(usersJSON.toString());
			break;
		    case "clickedOnDocument":
		    	JSONObject request = new JSONObject();
		    	request.put("type", "clickedOnDocument");
		    	request.put("docID", requestJSON.getString("docID"));
		    	broadcast(request.toString());
		    break;
	    }
	   
//	    users.put(userSession.getId(), message);
	    }

	@OnError
	public void onError(Throwable e){
		e.printStackTrace();
	}
	

	public  void broadcast(String msg) {
	    System.out.println("Broadcast Nachricht an alle:" + msg);
	    for (Session session : userSessions) {
	        session.getAsyncRemote().sendText(msg);
	        System.out.println("Sesion ID: " + session.getId());
	    }
	}
}
