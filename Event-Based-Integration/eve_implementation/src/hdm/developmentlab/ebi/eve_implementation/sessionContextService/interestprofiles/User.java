package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;


import eventprocessing.event.AbstractEvent;
import eventprocessing.interestprofile.AbstractInterestProfile;


public class User extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Empfängt UserEvents und speichert diese Information jeweils im korrekten SessionContext ab.
	 * @param arg0
	 */

	@Override
	protected void doOnReceive(AbstractEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
