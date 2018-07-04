package hdm.developmentlab.ebi.eve_implementation.activityService;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.NullPredicateException;
import eventprocessing.agent.interestprofile.predicates.logical.And;
import eventprocessing.agent.interestprofile.predicates.logical.Or;
import eventprocessing.agent.interestprofile.predicates.statement.GetEverything;
import eventprocessing.agent.interestprofile.predicates.statement.HasProperty;
import eventprocessing.agent.interestprofile.predicates.statement.HasPropertyContains;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.agent.interestprofile.predicates.statement.IsFromTopic;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenApplicationIP;

/**
 * Der ActivityAgent sorgt dafür dass Applikationen der GUI vorgeschlagen werden, er konsumiert vom Topic TokenGeneration und verwendet die dort ankommenden
 * ApplicationType und CalendarEvent im Interessenprofil TokenApplicationIP.
 * 
 * @author rrapp, birk
 *
 */
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
			try {
				ip.add(new Or(new IsEventType("CalendarEvent"), new IsEventType("ApplicationEvent"), new HasProperty("ApplicationType")));
			} catch (NullPredicateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//ip.add(new IsEventType("DocumentEvent"));
			this.add(ip);
		
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
	}
	}
	