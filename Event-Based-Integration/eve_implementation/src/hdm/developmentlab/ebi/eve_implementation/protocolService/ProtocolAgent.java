package hdm.developmentlab.ebi.eve_implementation.protocolService;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.exceptions.NoValidConsumingTopicException;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.dispatch.NoValidInterestProfileException;
import eventprocessing.input.kafka.ConsumerSettings;
import eventprocessing.interestprofile.AbstractInterestProfile;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenApplicationType;
import hdm.developmentlab.ebi.eve_implementation.protocolService.interestprofiles.Sessions;

public class ProtocolAgent extends AbstractAgent {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doOnInit() {

		/*
		 * Angabe der Topics, die konsumiert werden sollen. Es können mehrere Topics
		 * angegeben werden.
		 */
		try {
			this.add("Sessions");
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}
		
		/*
		 * Fügt dem Agenten ein InteressenProfil hinzu. Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		try {
			AbstractInterestProfile ip = new Sessions();
			//ip.add(new IsEventType(ShowcaseValues.INSTANCE.Token()));
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
	}
}
