package hdm.developmentlab.ebi.eve_implementation.protocolService;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenDocumentType;

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
			this.add("TokenGeneration");
			// + alle Topics also Doc Requests auch
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}
		
		/*
		 * Fügt dem Agenten ein InteressenProfil hinzu. Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		
		try {
			AbstractInterestProfile ip = new TokenDocumentType();
			ip.add(new IsEventType("TokenEvent"));
			ip.add(new IsEventType("ContextUpdate"));
			ip.add(new IsEventType("RequestEvents"));
			ip.add(new IsEventType("DocumentEvents"));
			//Wenn Session endet:
			ip.add(new IsEventType("SessionEvent"));
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
	}
}
