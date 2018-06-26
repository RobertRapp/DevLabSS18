package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;


import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
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
		
		System.out.println("Das Event "+event+" in Tokens IP angekommen");
		if(EventUtils.findPropertyByKey(event, "contextupdate") != null) {
			System.out.println("Das Event "+event+" ist ein CONTEXTUPDATE");
		}
		
	}

}
