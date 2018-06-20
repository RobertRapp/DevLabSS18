package hdm.developmentlab.ebi.eve_implementation.sessionContextService;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.exceptions.NoValidConsumingTopicException;
import startServices.ShowcaseValues;
import eventprocessing.dispatch.NoValidInterestProfileException;
import eventprocessing.input.kafka.ConsumerSettings;
import eventprocessing.interestprofile.AbstractInterestProfile;
import eventprocessing.interestprofile.predicates.AbstractPredicate;


import eventprocessing.interestprofile.predicates.statement.HasProperty;
import hdm.developmentlab.ebi.eve_implementation.events.SessionEvent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.*;

import java.util.ArrayList;



public class SessionContextAgent extends AbstractAgent {
	
	ArrayList<SessionEvent> sessions = new ArrayList<SessionEvent>();
	AbstractInterestProfile project;
	SessionState sessionState = new SessionState();
	TimeReference timeReference = new TimeReference();
	User userInfo = new User();

	protected void doOnInit() {
		try {
			this.add("TokenGeneration");
			this.add("test");
		} catch (NoValidConsumingTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			
					
			SessionState sessionInt = new SessionState(); 
					
			this.add(sessionInt);
			
		} catch (NoValidInterestProfileException e) {
			
			e.printStackTrace();
		}
		this.setConsumerSettings(new ConsumerSettings(ShowcaseValues.INSTANCE.getIpKafka(),
				ShowcaseValues.INSTANCE.getPortKafka(), "Tokens"));
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<SessionEvent> getSessions() {
		return sessions;
	}


	public void addSession(SessionEvent session) {
		sessions.add(session);
	}
	
	public SessionEvent getSessionById(String session) {
		
		for(SessionEvent s : sessions) {
			if(s.getSessionId().equalsIgnoreCase(session)) return s;
		}		
		return null;
	}


	public AbstractInterestProfile getProject() {
		return project;
	}


	public void setProject(AbstractInterestProfile project) {
		this.project = project;
	}


	public SessionState getSessionState() {
		return sessionState;
	}


	public void setSessionState(SessionState sessionState) {
		this.sessionState = sessionState;
	}


	public TimeReference getTimeReference() {
		return timeReference;
	}


	public void setTimeReference(TimeReference timeReference) {
		this.timeReference = timeReference;
	}
	
	public User getUserInfo() {
		return userInfo;
	}


	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}





	


}
