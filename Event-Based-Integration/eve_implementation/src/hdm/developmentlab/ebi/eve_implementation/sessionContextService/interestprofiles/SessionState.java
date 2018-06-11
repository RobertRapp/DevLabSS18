package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;


import eventprocessing.agent.exceptions.NoValidEventException;
import eventprocessing.agent.exceptions.NoValidTargetTopicException;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.interestprofile.AbstractInterestProfile;
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
	SessionStateEvent arg0 = (SessionStateEvent) abs;		
		SessionContextAgent agent = (SessionContextAgent) this.getAgent(); 			
		SessionEvent session = new SessionEvent();
		session.setId(arg0.getId());
		session.setSessionId(arg0.getSessionID());
		session.setSessionStart((long) arg0.getProperties().get(0).getValue());
		session.add(new Property<String>("Report", "Ich habe ein Token erhalten, es war ein SessionStart mit um "+session.getSessionStart()));
		agent.addSession(session);
		
		try {
			agent.send(session, "test");
		} catch (NoValidEventException e) {
			
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
