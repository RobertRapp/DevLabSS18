package eventprocessing.agent.SessionEnd;

import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.demo.ShowcaseValues;
import eventprocessing.events.DocProposalEvent;
import eventprocessing.events.SessionEndEvent;
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
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.demo.events.SensorEvent;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.TextUtils;
import eventprocessing.utils.factory.LoggerFactory;

/**
 *
 */
public class SessionStartInterestProfile extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1707313434192975250L;
	private static Logger LOGGER = LoggerFactory.getLogger(SessionStartInterestProfile.class);

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {

			//SessionEndEvent e = (SessionEndEvent) event;
	/*	
				try {
					getAgent().send(event, "SessionState");
				System.out.println(this.getClass().getSimpleName()+" : Event versendet "+TimeUtils.getCurrentTime()+" - "+ event.getType());} catch (NoValidEventException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", event));
				} catch (NoValidTargetTopicException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", "SessionState"));
				}
*/
		
		
	}

}
