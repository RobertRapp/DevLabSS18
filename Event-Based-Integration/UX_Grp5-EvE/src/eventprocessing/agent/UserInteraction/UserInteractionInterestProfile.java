package eventprocessing.agent.UserInteraction;

import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.demo.ShowcaseValues;
import eventprocessing.events.DocProposalEvent;
import eventprocessing.events.UserInteractionEvent;
import eventprocessing.demo.events.SensorEvent;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.TextUtils;
import eventprocessing.utils.factory.LoggerFactory;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;
import values.GUIValues;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.demo.events.SensorEvent;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.TextUtils;
import eventprocessing.utils.factory.LoggerFactory;

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
public class UserInteractionInterestProfile extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5861888526213304436L;
	private static Logger LOGGER = LoggerFactory.getLogger(UserInteractionInterestProfile.class);

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {

		//	UserInteractionEvent e = (UserInteractionEvent) event;
		/*	
				try {
					getAgent().send(event, "UserInteraction");
				System.out.println(this.getClass().getSimpleName()+" : Event versendet "+TimeUtils.getCurrentTime()+" - "+ event.getType());} catch (NoValidEventException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", event));
				} catch (NoValidTargetTopicException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", "UserInteraction"));
				}
				

		*/	
		
	}

}
