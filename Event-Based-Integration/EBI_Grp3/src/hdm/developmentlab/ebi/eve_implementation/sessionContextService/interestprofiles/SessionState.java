package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;


import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.factory.LoggerFactory;
import hdm.developmentlab.ebi.eve_implementation.events.SessionEvent;
import hdm.developmentlab.ebi.eve_implementation.events.SessionStateEvent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.SessionContextAgent;


public class SessionState extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Empfängt das Event, dass ein Gespräch gestartet ist und erzuegt dafür ein neues SessionEvent, das während
	 * des Gesprächs mit weiteren Informationen befüllt wird. Abgesendet wird ein SessionEvent erst wenn das Event
	 * "Gespräch beendet" zu einem passenden  SessionStartEvent ankommt.
	 * @param arg0
	 */
	@Override
	protected void doOnReceive(AbstractEvent abs) {
		
		Logger l = LoggerFactory.getLogger("DOONRECEIVE SESSIONSTATE");
		l.log(Level.WARNING, "Event von test erhalten");
		SessionStateEvent arg0 = (SessionStateEvent) abs;		
		SessionContextAgent agent = (SessionContextAgent) this.getAgent(); 			
		SessionEvent session = new SessionEvent();
		session.setId(arg0.getId());
		session.setSessionId(arg0.getSessionID());
		session.setSessionStart((long) arg0.getProperties().get(0).getValue());
		session.add(new Property<String>("Report", "Ich habe ein Token erhalten, es war ein SessionStart mit um "+session.getSessionStart()));
		agent.addSession(session);
		
		try {
			agent.send(session, "Sessions");
			
		} catch (NoValidEventException e) {
			l.log(Level.WARNING, "SessionState Event konnte nicht publiziert werden"+e);
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
