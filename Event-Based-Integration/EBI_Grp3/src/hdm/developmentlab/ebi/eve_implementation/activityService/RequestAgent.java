package hdm.developmentlab.ebi.eve_implementation.activityService;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.NullPredicateException;
import eventprocessing.agent.interestprofile.predicates.logical.Or;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenDocumentType;


public class RequestAgent extends AbstractAgent {

	
	private static final long serialVersionUID = 1L;

	protected void doOnInit() {
		
		this.setId("RequestAgent");

		/*
		 * Angabe der Topics, die konsumiert werden sollen. Es können mehrere Topics
		 * angegeben werden.
		 */
		try {
			this.add("TokenGeneration");
			this.add("SessionContext");
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}
		
		/*
		 * Fügt dem Agenten ein InteressenProfil hinzu. Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		try {
			AbstractInterestProfile ip = new TokenDocumentType();
			try {
				ip.add(new Or(new IsEventType("TokenEvent"), new IsEventType("SessionContext")));
			} catch (NullPredicateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.add(ip);
		
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
	}
	}
	

	

