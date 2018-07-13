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
		
	
		try {
			this.add("Gui");
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}

	}

}
