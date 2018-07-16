package eventprocessing.demo.agents.SensorProcessing;

import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.TextUtils;
import eventprocessing.utils.TimeUtils;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;

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
public class SensorProcessingInterestProfile extends AbstractInterestProfile {

	private static final long serialVersionUID = 186142080400351240L;
	private static Logger LOGGER = LoggerFactory.getLogger(SensorProcessingInterestProfile.class);

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doOnReceive(AbstractEvent event) {
		// Prüfung ob es vom Typ SensorEvent ist.
		if (EventUtils.isType("SensorEvent", event)) {
			// Aussortierung von fehlerhaften Sensorwerten.
			Property<Integer> sensorIdProperty = (Property<Integer>) EventUtils.findPropertyByKey(event, "SensorID");
			Property<String> locationProperty = (Property<String>) EventUtils.findPropertyByKey(event, "Location");
			if (sensorIdProperty.getValue() != 0 && !TextUtils.isNullOrEmpty(locationProperty.getValue())) {
				try {
					// Das erzeugte Event wird über den Agenten an das Topic "TrafficData" versendet
					getAgent().send(event, ShowcaseValues.INSTANCE.getTrafficDataTopic());
				System.out.println(this.getClass().getSimpleName()+" : Event versendet "+TimeUtils.getCurrentTime()+" - "+ event.getType());} catch (NoValidEventException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", event));
				} catch (NoValidTargetTopicException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", ShowcaseValues.INSTANCE.getTrafficDataTopic()));
				}
			}
		}
	}

}
