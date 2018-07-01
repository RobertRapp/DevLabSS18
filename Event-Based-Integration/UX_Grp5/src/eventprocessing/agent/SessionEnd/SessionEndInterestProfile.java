package eventprocessing.agent.SessionEnd;

import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.events.SessionEndEvent;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.factory.LoggerFactory;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import values.GUIValues;

/**
 * Dieses Interessenprofil führt die Reinigung sowie Filterung durch. Es wird
 * nach fehlerhaften Nachrichten ausschau gehalten, die aus dem Datenstrom
 * gefiltert werden, bevor diese an den <code>TrafficAnalysis</code> Agenten
 * geschickt werden. Fehlerhafte Nachrichten sind beispielsweise die ID vom Wert
 * 0 oder wenn keine Location angegeben wurde.
 * Doppelungen von Nachrichten werden hier nicht geprüft.
 * 
 * @author IngoT
 *
 */
public class SessionEndInterestProfile extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8861336072837156090L;
	private static Logger LOGGER = LoggerFactory.getLogger(SessionEndInterestProfile.class);

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {
		if (event instanceof SessionEndEvent) {
			SessionEndEvent e = (SessionEndEvent) event;
		
				try {
					getAgent().send(e, GUIValues.INSTANCE.getSessionEndTopic());
				} catch (NoValidEventException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", e));
				} catch (NoValidTargetTopicException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", e));
				}
			
		}
		
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
