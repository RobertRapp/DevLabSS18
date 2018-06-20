package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;


import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.AtomicEvent;
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
		AbstractEvent session = new AtomicEvent();
		session.setId(arg0.getId()+1);
		session.add(abs.getPropertyByKey("sessionStart"));
		session.add(new Property<String>("Report", "Ich habe ein Token erhalten, es war ein SessionStart"));
		
		
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
