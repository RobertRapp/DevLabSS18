package startServices;

import java.util.logging.Level;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.AgentException;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
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
import eventprocessing.utils.TimeUtils;
import eventprocessing.utils.mapping.MessageMapper;

public class AdhocAgent extends AbstractAgent{
	
private static final long serialVersionUID = 606360123599610899L;
private String name;
private String zielTopic;
private boolean localhostFragezeichen = true;

 public AdhocAgent(String name, String zielTopic, boolean localhosFragezeichen) {
	 
	this.name = name;
	this.zielTopic = zielTopic;
	this.localhostFragezeichen = localhosFragezeichen;	
 }

	@Override
	protected void doOnInit() {
this.setId(name);
	AbstractInterestProfile ip = new AbstractInterestProfile() {
private static final long serialVersionUID = 6063600497599610899L;
	@Override
	protected void doOnReceive(AbstractEvent event) { 
	try {			
	
this.getAgent().send(event, zielTopic); //nächsterAgent
	} catch (NoValidEventException e) {e.printStackTrace();
	} catch (NoValidTargetTopicException e) {e.printStackTrace();
	}}};
	ip.add(new IsEventType("WatsonEvent"));
	try {this.add(ip);
	} catch (NoValidInterestProfileException e) {e.printStackTrace();}
	try {
		this.add(name);
	} catch (NoValidConsumingTopicException e) {e.printStackTrace();};	
	if(localhostFragezeichen) {		
		this.setConsumerSettings(new ConsumerSettings("localhost", "9092", "group-"+this.getId()));
		this.setProducerSettings(new ProducerSettings("localhost", "9092"));
	}else {
		this.setConsumerSettings(new ConsumerSettings("10.142.0.2", "9092", "group-"+this.getId()));
		this.setProducerSettings(new ProducerSettings("10.142.0.2", "9092"));
	}	
}
}