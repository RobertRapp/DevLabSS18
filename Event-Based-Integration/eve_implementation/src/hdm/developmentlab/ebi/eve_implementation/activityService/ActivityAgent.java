package hdm.developmentlab.ebi.eve_implementation.activityService;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.exceptions.NoValidConsumingTopicException;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.demo.interestprofiles.DiagnosisInterestProfile;
import eventprocessing.dispatch.NoValidInterestProfileException;
import eventprocessing.input.kafka.ConsumerSettings;
import eventprocessing.interestprofile.AbstractInterestProfile;
import eventprocessing.interestprofile.predicates.statement.IsEventType;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenApplicationType;
import hdm.developmentlab.ebi.eve_implementation.events.*;


public class ActivityAgent extends AbstractAgent {

	

	
	protected void doOnInit() {
		
		/*
		 * FÃ¼gt dem Agenten ein InteressenProfil hinzu Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		try {
			AbstractInterestProfile ip = new TokenApplicationType();
			//ip.add(new IsEventType(ShowcaseValues.INSTANCE.Token()));
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		/*
		 * Angabe der Topics, die konsumiert werden sollen. Es kÃ¶nnen mehrere Topics
		 * angegeben werden.
		 */
		try {
			this.add("TokenGeneration");
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}
		this.setConsumerSettings(new ConsumerSettings(ShowcaseValues.INSTANCE.getIpKafka(),
				ShowcaseValues.INSTANCE.getPortKafka(), "Tokens2"));

	}
		
		
	}

	

