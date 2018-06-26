package hdm.developmentlab.ebi.eve_implementation.activityService;

import java.util.ArrayList;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;
import eventprocessing.agent.interestprofile.predicates.statement.HasProperty;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.demo.agents.diagnosis.DiagnosisInterestProfile;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenApplicationType;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenDocumentType;
import hdm.developmentlab.ebi.eve_implementation.events.SessionEvent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.SessionState;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.TimeReference;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.User;


public class ActivityAgent extends AbstractAgent {

	
	private static final long serialVersionUID = 1L;

	protected void doOnInit() {
		

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
			AbstractInterestProfile ip = new TokenDocumentType();
			ip.add(new IsEventType("TokenEvent"));
			this.add(ip);
		
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
	}
	}
	

	

