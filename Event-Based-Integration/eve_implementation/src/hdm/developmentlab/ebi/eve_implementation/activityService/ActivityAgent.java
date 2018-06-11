package hdm.developmentlab.ebi.eve_implementation.activityService;

import java.util.ArrayList;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.exceptions.NoValidConsumingTopicException;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.dispatch.NoValidInterestProfileException;
import eventprocessing.input.kafka.ConsumerSettings;
import eventprocessing.interestprofile.AbstractInterestProfile;
import eventprocessing.interestprofile.predicates.AbstractPredicate;
import eventprocessing.interestprofile.predicates.statement.HasProperty;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenApplicationType;
import hdm.developmentlab.ebi.eve_implementation.events.SessionEvent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.SessionState;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.TimeReference;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.TokenDocumentType;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.User;


public class ActivityAgent extends AbstractAgent {

	

	
	/**
	 * 
	 */
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
			AbstractPredicate predicate = new HasProperty("application");
			tokenDocumentType.add(predicate);
			this.add(tokenDocumentType);
			
			/*
			 * Beinhaltet Tokenevent eine Application? 
			 * ApplicationEvent-Objekt erzeugen 
			 * Wenn ja, Event erzeugen, welches Link zur Application bei DR "abfragt"
			 * 
			 */
			
			AbstractInterestProfile ip = new TokenApplicationType();
			//ip.add(new IsEventType(ShowcaseValues.INSTANCE.Token()));
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
		this.setConsumerSettings(new ConsumerSettings(ShowcaseValues.INSTANCE.getIpKafka(),
				ShowcaseValues.INSTANCE.getPortKafka(), "Tokens2"));

	}
	
	
	public ArrayList<SessionEvent> getSessions() {
		return sessions;
	}
	public void setSessions(ArrayList<SessionEvent> sessions) {
		this.sessions = sessions;
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




	ArrayList<SessionEvent> sessions = new ArrayList<SessionEvent>();
	AbstractInterestProfile project;
	SessionState sessionState = new SessionState();
	TimeReference timeReference = new TimeReference();
	TokenDocumentType tokenDocumentType = new TokenDocumentType();
	User userInfo = new User();
	
	
		
		
	}

	

