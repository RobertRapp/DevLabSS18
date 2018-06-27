package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;


import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;


public class Tokens extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Empfängt ein ProjektEvent und aktualisiert den passenden SessionContext mit der Info.
	 * @param arg0
	 */
	@Override
	protected void doOnReceive(AbstractEvent event) {
		
		if(EventUtils.isType("GuiEvent", event)) {
			Logger l = LoggerFactory.getLogger("logger");
			l.log(Level.WARNING, "GUIEVENT ERHALTEN");
			try {
				this.getAgent().send(event, "DocProposal");
			} catch (NoValidEventException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoValidTargetTopicException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		try {
			this.getAgent().send(event, "test");
		} catch (NoValidEventException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Das Event "+event+" in Tokens IP angekommen");
		if(EventUtils.findPropertyByKey(event, "contextupdate") != null) {
			System.out.println("Das Event "+event+" ist ein CONTEXTUPDATE");
		}
		
	}

}
