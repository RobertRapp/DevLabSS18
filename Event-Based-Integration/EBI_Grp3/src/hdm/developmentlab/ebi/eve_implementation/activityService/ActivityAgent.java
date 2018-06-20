package hdm.developmentlab.ebi.eve_implementation.activityService;

import java.util.ArrayList;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;
import eventprocessing.agent.interestprofile.predicates.statement.HasProperty;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.demo.ShowcaseValues;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenApplicationType;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenDocumentType;
import hdm.developmentlab.ebi.eve_implementation.events.SessionEvent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.SessionState;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.TimeReference;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.User;


public class ActivityAgent extends AbstractAgent {

	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<SessionEvent> sessions = new ArrayList<SessionEvent>();
	private AbstractInterestProfile project;
	private SessionState sessionState = new SessionState();
	private TimeReference timeReference = new TimeReference();
	private TokenApplicationType tokenApplicationType = new TokenApplicationType();
	private TokenDocumentType tokenDocumentType = new TokenDocumentType();
	private User userInfo = new User();

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
			
			tokenApplicationType.add(predicate);
			this.add(tokenApplicationType);
			
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
		
		try {
			AbstractPredicate predicate = new HasProperty("topic");
			
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

	public User getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}

	public TokenApplicationType getTokenApplicationType() {
		return tokenApplicationType;
	}

	public void setTokenApplicationType(TokenApplicationType tokenApplicationType) {
		this.tokenApplicationType = tokenApplicationType;
	}
		
	}

	

