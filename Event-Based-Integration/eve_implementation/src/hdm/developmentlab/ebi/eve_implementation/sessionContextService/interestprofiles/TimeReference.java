package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;


import eventprocessing.event.AbstractEvent;
import eventprocessing.interestprofile.AbstractInterestProfile;


public class TimeReference extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 * Empfängt einen Zeitbezug und verändert dadurch den Zeitbezug im korrekten SessionContext.
	 *
	 */

	@Override
	protected void doOnReceive(AbstractEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
