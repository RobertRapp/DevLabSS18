package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;


import eventprocessing.agent.exceptions.NoValidEventException;
import eventprocessing.agent.exceptions.NoValidTargetTopicException;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.interestprofile.AbstractInterestProfile;
import hdm.developmentlab.ebi.eve_implementation.events.SessionEvent;
import hdm.developmentlab.ebi.eve_implementation.events.TokenEvent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.SessionContextAgent;


public class TokenDocumentType extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Empfängt Tokentypen und leitet damit eine neue Dokumentenvorschlagsanfrage ein.
	 * @param arg0
	 */

	@Override
	protected void doOnReceive(AbstractEvent arg) {
		
		TokenEvent arg0 = (TokenEvent) arg;
		
		SessionContextAgent agent = (SessionContextAgent) this.getAgent(); 	
		
		SessionEvent session = new SessionEvent();
		session.setId(arg0.getId());
		session.setSessionId(arg0.getSessionID());
		session.setSessionStart((long) arg0.getProperties().get(0).getValue());
		agent.addSession(session);
		session.add(new Property<String>("Report", "Ich habe ein Token erhalten, es war ein SessionStart mit um "+session.getSessionStart()));
		
		try {
			agent.send(session, "test");
		} catch (NoValidEventException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
