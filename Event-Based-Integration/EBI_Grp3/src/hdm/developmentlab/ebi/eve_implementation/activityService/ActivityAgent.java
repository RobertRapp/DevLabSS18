package hdm.developmentlab.ebi.eve_implementation.activityService;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.GetEverything;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenApplicationIP;


public class ActivityAgent extends AbstractAgent {

	
	private static final long serialVersionUID = 1L;

	protected void doOnInit() {
		
		this.setId("ActivityAgent");
		/*
		 * Angabe der Topics, die konsumiert werden sollen. Es können mehrere Topics
		 * angegeben werden.
		 */
		try {
			this.add("TokenGeneration");
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}
		
		/*
		 * Fügt dem Agenten ein InteressenProfil hinzu. Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		try {
			AbstractInterestProfile ip = new TokenApplicationIP();
			ip.add(new GetEverything());
			//ip.add(new IsEventType("DocumentEvent"));
			this.add(ip);
		
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
	}
	}
	

	

