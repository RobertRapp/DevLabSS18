package startServices;

import java.util.logging.Level;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.AgentException;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
<<<<<<< HEAD
=======
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
import eventprocessing.agent.interestprofile.predicates.statement.IsFromTopic;
import eventprocessing.agent.state.ReadyState;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.consume.spark.streaming.SparkContextValues;
import eventprocessing.consume.spark.streaming.window.NoValidWindowSettingsException;
import eventprocessing.consume.spark.streaming.window.Window;
import eventprocessing.event.AbstractEvent;
import eventprocessing.produce.kafka.Despatcher;
import eventprocessing.produce.kafka.ProducerSettings;
import eventprocessing.utils.SystemUtils;
<<<<<<< HEAD
=======
import eventprocessing.utils.TimeUtils;
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
import eventprocessing.utils.mapping.MessageMapper;

public class AdhocAgent extends AbstractAgent{
	
private static final long serialVersionUID = 606360123599610899L;
private String name;
private String zielTopic;
private boolean localhostFragezeichen = true;

 public AdhocAgent(String name, String zielTopic, boolean localhosFragezeichen) {
	 
	this.name = name;
	this.zielTopic = zielTopic;
<<<<<<< HEAD
	this.localhostFragezeichen = localhosFragezeichen;
	
	/**
	 * Wenn kein Windows angelegt wurde, wird ein Default-Window gesetzt. Der
	 * Default-Wert orientiert sich an der batch duration des SparkContext.
	 */
	if (window == null) {
		try {
			this.window = new Window(SparkContextValues.INSTANCE.getBatchDuration());
		} catch (NoValidWindowSettingsException e2) {
			LOGGER.log(Level.WARNING, () -> String.format("failed to set window for %s%sException: %s",
					this.getId(), SystemUtils.getFileSeparator(), e2));
			try {
				throw new AgentException("no window was set");
			} catch (AgentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Registriert den Agenten im Netzwerk.
	// AnnounceAgent();
	// KafkaClient client = new KafkaClient(consumerSettings);
	// this.getSubscribedTopics().forEach(topic -> {
	// client.createTopic(topic);
	// });

	//this.state = new ReadyState();
	
	
=======
	this.localhostFragezeichen = localhosFragezeichen;	
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
 }

	@Override
	protected void doOnInit() {
this.setId(name);
	AbstractInterestProfile ip = new AbstractInterestProfile() {
private static final long serialVersionUID = 6063600497599610899L;
	@Override
<<<<<<< HEAD
	protected void doOnReceive(AbstractEvent event) {try {	
		System.out.println("Agent: "+name+" onReceive");
		LOGGER.log(Level.WARNING, event.toString());
		LOGGER.log(Level.WARNING, event.toString());
		LOGGER.log(Level.WARNING, event.toString());
=======
	protected void doOnReceive(AbstractEvent event) { 
	try {			
	
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
this.getAgent().send(event, zielTopic); //nächsterAgent
	} catch (NoValidEventException e) {e.printStackTrace();
	} catch (NoValidTargetTopicException e) {e.printStackTrace();
	}}};
<<<<<<< HEAD
	ip.add(new IsFromTopic(name));
=======
	ip.add(new IsEventType("WatsonEvent"));
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
	try {this.add(ip);
	} catch (NoValidInterestProfileException e) {e.printStackTrace();}
	try {
		this.add(name);
<<<<<<< HEAD
	} catch (NoValidConsumingTopicException e) {e.printStackTrace();};
	
	if(localhostFragezeichen) {
		
=======
	} catch (NoValidConsumingTopicException e) {e.printStackTrace();};	
	if(localhostFragezeichen) {		
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
		this.setConsumerSettings(new ConsumerSettings("localhost", "9092", "group-"+this.getId()));
		this.setProducerSettings(new ProducerSettings("localhost", "9092"));
	}else {
		this.setConsumerSettings(new ConsumerSettings("10.142.0.2", "9092", "group-"+this.getId()));
		this.setProducerSettings(new ProducerSettings("10.142.0.2", "9092"));
<<<<<<< HEAD
	}
	
	
=======
	}	
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
}
}