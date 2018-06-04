package hdm.developmentlab.ebi.eve_implementation.sessionContextService;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.exceptions.NoValidConsumingTopicException;
import startServices.ShowcaseValues;
import eventprocessing.dispatch.NoValidInterestProfileException;
import eventprocessing.input.kafka.ConsumerSettings;
import eventprocessing.interestprofile.AbstractInterestProfile;import eventprocessing.interestprofile.predicates.AbstractPredicate;
import eventprocessing.interestprofile.predicates.statement.HasProperty;
import hdm.developmentlab.ebi.eve_implementation.events.SessionEvent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.*;

import java.util.ArrayList;



public class SessionContextAgent extends AbstractAgent {


	protected void doOnInit() {
		try {
			this.add("TokenGeneration");
		} catch (NoValidConsumingTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			
			AbstractPredicate predicate = new HasProperty("sessionStart");						
			tokenDocumentType.add(predicate);
			this.add(tokenDocumentType);
			
		} catch (NoValidInterestProfileException e) {
			// TODO Auto-generated catch block
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


	public TokenDocumentType getTokenDocumentType() {
		return tokenDocumentType;
	}


	public void setTokenDocumentType(TokenDocumentType tokenDocumentType) {
		this.tokenDocumentType = tokenDocumentType;
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


	ArrayList<SessionEvent> sessions = new ArrayList<SessionEvent>();
	AbstractInterestProfile project;
	SessionState sessionState = new SessionState();
	TimeReference timeReference = new TimeReference();
	TokenDocumentType tokenDocumentType = new TokenDocumentType();
	User userInfo = new User();


	


}
