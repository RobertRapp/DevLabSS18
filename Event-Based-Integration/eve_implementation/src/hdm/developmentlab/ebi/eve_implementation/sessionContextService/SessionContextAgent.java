package hdm.developmentlab.ebi.eve_implementation.sessionContextService;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.interestprofile.AbstractInterestProfile;
import hdm.developmentlab.ebi.eve_implementation.events.SessionEvent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.*;

import java.util.ArrayList;

public class SessionContextAgent extends AbstractAgent {


	ArrayList<SessionEvent> sessions = new ArrayList<SessionEvent>();
	AbstractInterestProfile project;
	SessionState sessionState = new SessionState();
	TimeReference timeReference = new TimeReference();
	TokenDocumentType tokenDocumentType = new TokenDocumentType();
	User userInfo = new User();




	protected void doOnInit() {


	}


}
