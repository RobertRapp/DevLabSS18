package eventprocessing.agent.UserInteraction;

import eventprocessing.agent.AbstractAgent;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.DocProposal.ConsumerSettingsGui;
import eventprocessing.agent.DocProposal.ProducerSettingsGui;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.demo.agents.diagnosis.ConsumerSettingsDiagnosis;
import eventprocessing.demo.agents.diagnosis.ProducerSettingsDiagnosis;
import eventprocessing.produce.kafka.ProducerSettings;

/**
 *
 */
public class UserInteraction extends AbstractAgent {

	private static final long serialVersionUID = 5361140545621342116L;

	@Override
	protected void doOnInit() {
		this.setId("UserInteractionAgent");
		/*
		 * Fügt dem Agenten ein InteressenProfil hinzu Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		try {
			AbstractInterestProfile ip = new UserInteractionInterestProfile();
			ip.add(new IsEventType("UserInteractionEvent"));
			//ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSpeedEvent()));
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		/*
		 * Angabe der Topics, die konsumiert werden sollen. Es können mehrere Topics
		 * angegeben werden.
		
		
		
		try {
			this.add(ShowcaseValues.INSTANCE.getDiagnosisTopic());
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}
		 */
		try {
			this.add("UserInteraction");
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}
		
		
	}
}
