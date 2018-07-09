package hdm.developmentlab.ebi.eve_implementation.activityService;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.NullPredicateException;
import eventprocessing.agent.interestprofile.predicates.logical.And;
import eventprocessing.agent.interestprofile.predicates.logical.Not;
import eventprocessing.agent.interestprofile.predicates.logical.Or;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.agent.interestprofile.predicates.statement.IsFromTopic;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenDocumentType;

/**
 * Der RequestAgent sorgt dafür dass Dokumentenanfragen an DR gesendet werden. Dafür konsumiert er vom Topic TokenGeneration und SessionContext
 * 
 * Er besitzt die IP TokenDocumentType. 
 * 
 * @author rrapp, birk
 *
 */

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
				ip.add(new Or(new IsEventType("SessionContextEvent"), (new And(new IsFromTopic("TokenGeneration"), new Not(new IsEventType("ApplicationEvent"))))));
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
	

	

