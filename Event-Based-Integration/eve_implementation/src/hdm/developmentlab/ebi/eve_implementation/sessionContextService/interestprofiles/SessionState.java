package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;


import eventprocessing.event.AbstractEvent;
import eventprocessing.interestprofile.AbstractInterestProfile;


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
	protected void doOnReceive(AbstractEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
