package startServices;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.spark.SparkException;
import org.apache.spark.TaskKilledException;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.exceptions.NoValidAgentException;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.input.kafka.ConsumerSettings;
import eventprocessing.input.spark.streaming.StreamingExecution;
import eventprocessing.output.kafka.Despatcher;
import eventprocessing.output.kafka.settings.ProducerSettings;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.mapping.MessageMapper;
import hdm.developmentlab.ebi.eve_implementation.activityService.ActivityAgent;
import hdm.developmentlab.ebi.eve_implementation.events.TokenEvent;
import hdm.developmentlab.ebi.eve_implementation.protocolService.ProtocolAgent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.SessionContextAgent;

/**
 * Startpunkt der Anwendung.
 * 
 * hier werden die Agenten initialisiert und die Sparkumgebung ausgefÃ¼hrt.
 * 
 * @author RobertRapp
 *
 */
public class StartServices {

	// FÃ¼r die Versendung der DemoEvents an das Topic nötig.
	private static final Despatcher despatcher = new Despatcher();
	// wandelt die Events in Nachrichten um.
	private static final MessageMapper messageMapper = new MessageMapper();
	
	
	
	public static void main(String[] args)
			throws TaskKilledException, SparkException, InterruptedException, NoValidAgentException {
		
		AbstractAgent activityService = (AbstractAgent) new ActivityAgent();
		
		AbstractAgent protocolService = (AbstractAgent) new ProtocolAgent();
		AbstractAgent sessionContext = (AbstractAgent) new SessionContextAgent();
		
		ConsumerSettings cs = new ConsumerSettings("10.142.0.2", "9092");
		
		activityService.setConsumerSettings(cs);
		protocolService.setConsumerSettings(cs);
		sessionContext.setConsumerSettings(cs);
		
		/*
		 * Alle Agenten die benötigt werden, werden hier erzeugt.
		 */
		//AbstractFactory agentFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getAgentFactory());
		// Erstellung der Agenten
	

		/*
		 * Die Agenten werden der Sparkumgebung hinzugefÃ¼gt Nur die Agenten die
		 * hinzugefÃ¼gt wurden, werden bei der Streamverarbeitung berÃ¼cksichtigt.
		 */
		StreamingExecution.add(activityService);
		StreamingExecution.add(protocolService);
		StreamingExecution.add(sessionContext);

		/*
		 * DemoEvents werden an die Topics gesendet. Das Versenden lÃ¤uft in einem
		 * separierten Thread ab.
		 */
		Runnable myRunnable = new Runnable() {
			public void run() {
				try {
					publishDemoEvents();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		// Thread wird erzeugt und gestartet
		Thread thread = new Thread(myRunnable);
		thread.start();

		/*
		 * Die Anwendung wird gestartet. Es sind bereits alle Agenten instanziiert die
		 * benÃ¶tigt werden.
		 */
		StreamingExecution.start();
	}

	/**
	 * Versendet die Events an das Topic
	 * 
	 * @param event,
	 *            welches an das Topic gesendet werden soll
	 * @param topic,
	 *            das Topic, an das die Nachrichten gesendet werden soll
	 */
	private static void publish(AbstractEvent event, String topic) {
		String message = messageMapper.toJSON(event);
		
		despatcher.deliver(message, topic);
	}

	/**
	 * Es werden eine vorgegebene Anzahl von Events erzeugt, die dann an die beiden
	 * Topics Sensor-1 und Sensor-2 versendet werden. Nach jeder Versendung wird der
	 * Thread fÃ¼r eine Sekunde angehalten, damit die Events nicht alle nahezu
	 * zeitgleich versendet werden.
	 * 
	 * @param amoutOfEvents,
	 *            Anzahl der Events pro Topic
	 * @throws InterruptedException
	 */
	private static void publishDemoEvents() throws InterruptedException {
			
			TokenEvent event = (TokenEvent) new TokenEvent();			
			event.setSessionID("1");
			Property<String> app = new Property<String>("application", "drive");
			event.add(app);					
			publish(event,"TokenGeneration");		
						
			TokenEvent event2 = (TokenEvent) new TokenEvent();
			event2.setSessionID("1");
			Property<String> taskdocument = new Property<String>("documentcategory", "TaskDocument");
			event2.add(taskdocument);			
					
			publish(event2,"TokenGeneration");

			TokenEvent event3 = (TokenEvent) new TokenEvent();
			event.setSessionID("2");
			Property<Long> sessionStart = new Property<Long>("sessionStart", System.currentTimeMillis());
			event3.add(sessionStart);
					
			publish(event3,"test");
			Logger l = LoggerFactory.getLogger("PUBLISHDEMOEVENTS");
			l.log(Level.WARNING, "Event wurde direkt durch Dispatcher auf Test gepusht");
			
			
			
			 
	}
}