package hdm.developmentlab.ebi.eve_implementation.sessionContextService;

import java.util.ArrayList;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.consume.kafka.ConsumerSettings;
import hdm.developmentlab.ebi.eve_implementation.events.SessionEvent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.SessionState;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.TimeReference;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.User;




public class SessionContextAgent extends AbstractAgent {
	
	ArrayList<SessionEvent> sessions = new ArrayList<SessionEvent>();
	AbstractInterestProfile project;
	SessionState sessionState = new SessionState();
	TimeReference timeReference = new TimeReference();
	User userInfo = new User();

	protected void doOnInit() {
		this.setId("SessionContextAgent");
		try {
			this.add("TokenGeneration");
			this.add("test");
		} catch (NoValidConsumingTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
