package hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles;



import eventprocessing.event.AbstractEvent;
import eventprocessing.interestprofile.AbstractInterestProfile;
import hdm.developmentlab.ebi.eve_implementation.events.TokenEvent;


public class TokenApplicationType extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	@Override
	protected void doOnReceive(AbstractEvent event) {
	/*
	 * Hier werden die Muster definiert, um zu prüfen, ob innerhalb ein Token vom Typ Application 
	 * ist. 
	 */
		
		if (event instanceof TokenEvent) {
			// casten zu SpeedEvent um HÃ¶chstgeschwindigkeit auszulesen
			//SpeedEvent speedEvent = (SpeedEvent) event;
			// Alle benÃ¶tigten Informationen werden aus dem Event entnommen
			// e.setVelocity(speedEvent.getAverageSpeed());

		}
		
	}

}
