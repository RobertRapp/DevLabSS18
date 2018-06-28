package hdm.developmentlab.ebi.eve_implementation.sessionContextService;

import java.util.ArrayList;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.GetEverything;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.agent.interestprofile.predicates.statement.IsFromTopic;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.demo.agents.diagnosis.DiagnosisInterestProfile;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.InterestProfileFactory;
import eventprocessing.utils.factory.PredicateFactory;
import hdm.developmentlab.ebi.eve_implementation.events.SessionEvent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.SessionContextIP;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.SessionState;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.TimeReference;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.Tokens;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.User;




public class SessionContextAgent extends AbstractAgent {
	
	ArrayList<SessionEvent> sessions = new ArrayList<SessionEvent>();
	AbstractInterestProfile project;
	

	TimeReference timeReference = new TimeReference();
	User userInfo = new User();
	
	protected void doOnInit() {
		
		//Ohne ID geht der Agent nicht 
		this.setId("SessionContextAgent");
		
		try {
			this.add("TokenGeneration");
		} catch (NoValidConsumingTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AbstractInterestProfile sessionContextIP = new SessionContextIP();
		sessionContextIP.add(new IsEventType("TokenEvent"));
		try {
			this.add(sessionContextIP);
		} catch (NoValidInterestProfileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		AbstractInterestProfile sessionState = new SessionState();
//		AbstractInterestProfile ip = new DiagnosisInterestProfile();
//		ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSpeedEvent()));
//		try {
//			this.add(ip);
//		} catch (NoValidInterestProfileException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//		this.setId("SessionContextAgent");
//		
//		try {
//			sessionState.add(new GetEverything());
//			this.add(sessionState);
//		} catch (NoValidInterestProfileException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//			this.add("SessionState");			
//		} catch (NoValidConsumingTopicException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
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
