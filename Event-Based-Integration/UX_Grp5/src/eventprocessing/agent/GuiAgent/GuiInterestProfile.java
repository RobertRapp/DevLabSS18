package eventprocessing.agent.GuiAgent;

import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.factory.LoggerFactory;

import eventprocessing.agent.interestprofile.AbstractInterestProfile;

/**
 * Dieses Interessenprofil führt die Reinigung sowie Filterung durch. Es wird
 * nach fehlerhaften Nachrichten ausschau gehalten, die aus dem Datenstrom
 * gefiltert werden, bevor diese an den <code>GuAgent</code> Agenten
 * geschickt werden.
 * 
 * @author IngoT
 *
 */
public class GuiInterestProfile extends AbstractInterestProfile {

	private static final long serialVersionUID = -210735813565569965L;
	private static Logger LOGGER = LoggerFactory.getLogger(GuiInterestProfile.class);

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {
			
		LOGGER.log(Level.WARNING, "Vom Topic DocProposal konsumiert!!!!!!: " +event);
		
	
		
	/*	
		// Prüfung ob es vom Typ SensorEvent ist.
		if (event instanceof SensorEvent) {
			// Wenn ja, cast.
			SensorEvent e = (SensorEvent) event;
			// Aussortierung von fehlerhaften Sensorwerten.
			if (e.getSensorID() != 0 && !TextUtils.isNullOrEmpty(e.getLocation())) {
				try {
					// Das erzeugte Event wird über den Agenten an das Topic "TrafficData" versendet
					getAgent().send(e, ShowcaseValues.INSTANCE.getTrafficDataTopic());
				} catch (NoValidEventException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", e));
				} catch (NoValidTargetTopicException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", e));
				}
			}
		} */
	}

}
