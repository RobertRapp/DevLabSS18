package eventprocessing.agent.DocProposal;

import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.events.DocProposalEvent;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.factory.LoggerFactory;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.Property;
import eventprocessing.utils.model.EventUtils;
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
public class DocProposalInterestProfile extends AbstractInterestProfile {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8699861536301449664L;
	private static Logger LOGGER = LoggerFactory.getLogger(DocProposalInterestProfile.class);

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {
		if (event instanceof DocProposalEvent) {
			DocProposalEvent e = (DocProposalEvent) event;
			
				try {
					getAgent().send(e, GUIValues.INSTANCE.getDocProposalTopic());
				} catch (NoValidEventException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", e));
				} catch (NoValidTargetTopicException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", e));
				}
				
			Property<?> docProposals=EventUtils.findPropertyByKey(event, "docProposals");
			System.out.println(docProposals);
			
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