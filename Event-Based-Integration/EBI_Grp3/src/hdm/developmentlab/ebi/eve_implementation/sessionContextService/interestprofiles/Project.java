package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;


import eventprocessing.event.AbstractEvent;
import eventprocessing.interestprofile.AbstractInterestProfile;


public class Project extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Empfängt ein ProjektEvent und aktualisiert den passenden SessionContext mit der Info.
	 * @param arg0
	 */
	@Override
	protected void doOnReceive(AbstractEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}