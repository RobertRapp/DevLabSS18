package startServices;

import org.json.JSONObject;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.DocProposal.DocProposalAgent;
import eventprocessing.agent.GuiAgent.GuiAgent;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.agent.AgentException;
import eventprocessing.consume.spark.streaming.StreamingExecution;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.produce.kafka.Despatcher;
import eventprocessing.produce.kafka.ProducerSettings;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.mapping.MessageMapper;
import eventprocessing.utils.SocketServer;


/**
 * Startpunkt der Anwendung.
 * 
 * hier werden die Agenten initialisiert und die Sparkumgebung ausgefÃ¼hrt.
 * 
 *
 */
public class StartServicesDocProposals {


		
		 // FÃ¼r die Versendung der DemoEvents an das Topic nötig.
	private static Despatcher despatcher = null;
	// wandelt die Events in Nachrichten um.
	private static final MessageMapper messageMapper = new MessageMapper();
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static AbstractFactory agentFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getAgentFactory());
	
	
	public static void main(String[] args) throws AgentException, InterruptedException
	 {
		
		despatcher = new Despatcher(new ProducerSettings("10.142.0.2 ","9092"));
		
		AbstractAgent docProposalAgent = new DocProposalAgent();
		docProposalAgent.setConsumerSettings(new ConsumerSettings("10.142.0.2 ","9092", "DocProposal"));
		docProposalAgent.setProducerSettings(new ProducerSettings("10.142.0.2 ","9092"));

		AbstractAgent guiAgent = new GuiAgent();
		guiAgent.setConsumerSettings(new ConsumerSettings("10.142.0.2 ","9092", "Gui"));
		guiAgent.setProducerSettings(new ProducerSettings("10.142.0.2 ","9092"));
		
	

		StreamingExecution.add(docProposalAgent);
		StreamingExecution.add(guiAgent);

		
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
			/*	try {
					publishDemoEvents();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} */
				
				
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

	
	private static void publish(AbstractEvent event, String topic) {
		
		String message = messageMapper.toJSON(event);	
		if(message != null && topic != null) {
			despatcher.deliver(message, topic);	
		}
		
	}

	// Events für Testzwecke
	
	private static void publishDemoEvents() throws InterruptedException {		
			
			for (int i = 0; i < 10; i++) {
				
				AbstractEvent event = eventFactory.createEvent("AtomicEvent");
				event.setType("DocProposalEvent");
				event.add(new Property<>("Documentname","Dokument01"));
				event.add(new Property<>("Editor","Markus"));
				event.add(new Property<>("DocumentType","Word"));
				event.add(new Property<>("URL","https://drive.google.com/open?id=1mz03wsi6eJ9pWn1Uw3899LNJzVYVgs34"));
				event.add(new Property<>("FileID","001"));
				event.add(new Property<>("LastChangeDate","03.07.2018"));
				event.add(new Property<>("Category","Implementierung"));
				
				publish(event,"DocProposal");
				Thread.sleep(1000);	
				
			}
			
	}	 
	
}