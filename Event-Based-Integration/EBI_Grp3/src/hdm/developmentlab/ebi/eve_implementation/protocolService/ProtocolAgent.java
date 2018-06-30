package hdm.developmentlab.ebi.eve_implementation.protocolService;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.NullPredicateException;
import eventprocessing.agent.interestprofile.predicates.logical.Or;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenDocumentType;
import hdm.developmentlab.ebi.eve_implementation.protocolService.interestprofiles.Sessions;

public class ProtocolAgent extends AbstractAgent {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doOnInit() {

		
		this.setId("ProtocolAgent");
		/*
		 * Angabe der Topics, die konsumiert werden sollen. Es können mehrere Topics
		 * angegeben werden.
		 */
		try {
			this.add("SessionInfo");
			this.add("TokenGeneration");
			this.add("UserInfo");
			this.add("proposedDoc");
			this.add("clickedDoc");
			
			// + alle Topics also Doc Requests auch
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}
		
		/*
		 * Fügt dem Agenten ein InteressenProfil hinzu. Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		
		try {
			AbstractInterestProfile ip = new Sessions();
			try {
				ip.add(new Or(new IsEventType("TokenEvent"), new IsEventType("User"), new IsEventType("proposedDoc"), new IsEventType("clickedDoc"), new IsEventType("sessionStart"), new IsEventType("sessionEnd")));
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
