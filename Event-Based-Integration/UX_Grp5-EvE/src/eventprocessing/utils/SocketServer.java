package eventprocessing.utils;


import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

//import eventprocessing.agent.GuiAgent.*;

public class SocketServer {

	 public static void main(String[] args)
	    {
	        Server server = new Server();
	       // server.setStopTimeout(1000000L);
	        
	        ServerConnector connector = new ServerConnector(server);
	        connector.setPort(80);
	        server.addConnector(connector);
	        

	        // Setup the basic application "context" for this application at "/"
	        // This is also known as the handler tree (in jetty speak)
	        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
	        context.setContextPath("/");
	        server.setHandler(context);

	        try
	        {
	            // Initialize javax.websocket layer
	            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(context);

	            // Add WebSocket endpoint to javax.websocket layer
	            //Session session = wscontainer.connectToServer(server, "")
	            wscontainer.addEndpoint(Websocket.class);
	            wscontainer.setDefaultMaxSessionIdleTimeout(1500000L);

	            server.start();
	            server.dump(System.err);
	            server.join();
	        }
	        catch (Throwable t)
	        {
	            t.printStackTrace(System.err);
	        }
	    }
}
