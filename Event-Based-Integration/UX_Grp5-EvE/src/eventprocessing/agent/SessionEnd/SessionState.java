package eventprocessing.agent.SessionEnd;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.demo.ShowcaseValues;

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
import eventprocessing.demo.agents.diagnosis.ConsumerSettingsDiagnosis;
import eventprocessing.demo.agents.diagnosis.ProducerSettingsDiagnosis;
import eventprocessing.produce.kafka.ProducerSettings;
import values.GUIValues;


/**
 * Dieser Agent ist für die Diagnose des Verkehrs zuständig. Er wertet die
 * Verkehrsdaten aus und leitet daraus entsprechende Handlungen ab.
 * 
 * @author IngoT
 *
 */
public class SessionState extends AbstractAgent {

	private static final long serialVersionUID = 5361140545621342113L;

	@Override
	protected void doOnInit() {
		this.setId("SessionState");
		/*
		 * Fügt dem Agenten ein InteressenProfil hinzu Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		try {
			AbstractInterestProfile ip = new SessionEndInterestProfile();
			ip.add(new IsEventType("SessionEndEvent"));
			//ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSpeedEvent()));
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
		try {
			AbstractInterestProfile ip = new SessionStartInterestProfile();
			ip.add(new IsEventType("SessionStartEvent"));
			//ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSpeedEvent()));
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		/*
		 * Angabe der Topics, die konsumiert werden sollen. Es können mehrere Topics
		 * angegeben werden.
		 */
		try {
			this.add("SessionState");
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}

	}
}
