package hdm.developmentlab.ebi.eve_implementation.protocolService;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.exceptions.NoValidConsumingTopicException;
import eventprocessing.dispatch.NoValidInterestProfileException;
import eventprocessing.interestprofile.AbstractInterestProfile;
import eventprocessing.interestprofile.predicates.statement.IsEventType;
import hdm.developmentlab.ebi.eve_implementation.protocolService.interestprofiles.Sessions;

import startServices.ShowcaseValues;

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
			ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSessionEvent()));
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
	}
}
