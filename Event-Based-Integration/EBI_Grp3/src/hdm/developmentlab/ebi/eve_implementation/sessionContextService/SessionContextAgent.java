package hdm.developmentlab.ebi.eve_implementation.sessionContextService;

import java.util.ArrayList;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.NullPredicateException;
import eventprocessing.agent.interestprofile.predicates.logical.Or;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.agent.interestprofile.predicates.statement.IsFromTopic;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.model.EventUtils;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.SessionContextIP;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.SessionState;

/*
 * Dieser Agent baut einen SessionContext auf, ein Sessionkontext besteht aus SessionID, zwei Teilnehmern, Projekt, Thema
 * die über empfangene Events gesetzt werden. Er implementiert zwei IPs 
 * 
 */



public class SessionContextAgent extends AbstractAgent {
	
	
	private static final long serialVersionUID = -182840242690218605L;
	
	/**
	 *  Hier werden die Attribute der Agenten festgelegt, falls Listen nicht dauerhaft
	 *  gespeichert werden,
	 */
	
	static ArrayList<AbstractEvent> sessions = new ArrayList<AbstractEvent>();

	
	public ArrayList<AbstractEvent> getSessions() {
		return sessions;
	}
	
	public AbstractEvent getSessionById(String sessionID) {
		for(AbstractEvent e:this.getSessions()) {
			System.out.println(EventUtils.findPropertyByKey(e,"sessionID").getValue());
			if( sessionID.equals(EventUtils.findPropertyByKey(e, "sessionID").getValue())) return e;
		}
		return null;
	}
	

	protected void doOnInit() {
			
		//Ohne ID geht der Agent nicht, bitte setzen
		this.setId("SessionContextAgent");
		
		
		
		/*
		 * Hier werden die Interessensprofile erzeugt und mit den Predicates ausgestattet.
		 * Welche Predicates es gibt findet man 
		 * in dem package eventprocessing.agent.interestprofile.predicates		  
		 * Für logische Predicates bspw. Oder / Und werden die zwei Predicates angegeben,
		 * die damit verbunden werden sollen.
		 */
		
		AbstractInterestProfile sessionContextIP = new SessionContextIP();	
		sessionContextIP.add(new IsFromTopic("TokenGeneration"));
		
		AbstractInterestProfile sessionState = new SessionState();		
		try {
			sessionState.add(new Or(new IsEventType("SessionStartEvent"),new IsEventType("SessionEndEvent")));
		} catch (NullPredicateException e1) {
			
			e1.printStackTrace();
		}
		
		/*
		 * Hier werden alle Interessensprofile des Agenten hinzugefügt.
		 * Bitte darauf achten, dass davor die Predicates gesetzt wurden.  
		 */
		try {
			this.add(sessionContextIP);			
			this.add(sessionState);

		} catch (NoValidInterestProfileException e) {
			e.printStackTrace();
		}
		
		/*
		 * Hier werden alle Topics des Agenten angegeben.
		 * Es ist dabei auf CaseSensetive zu achten!
		 * 
		 */
		try {
			this.add("SessionState");	
			this.add("TokenGeneration");
			this.add("SessionContextUpdate");
		} catch (NoValidConsumingTopicException e) {			
			e.printStackTrace();
		}
		
		
	}
	
	public void addSession(AbstractEvent session) {
		sessions.add(session);
	}
	
	





	


}
