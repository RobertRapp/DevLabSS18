package hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles;



import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;


public class TokenApplicationIP extends eventprocessing.agent.interestprofile.AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(TokenApplicationIP.class);
	
	// Factory für die Erzeugung der Events
	private AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	
	@Override
	protected void doOnReceive(AbstractEvent event) {
		System.out.println("RECEIVED");
		// Erzeugt über die Factory ein neues Event
		AbstractEvent applicationEvent = eventFactory.createEvent("AtomicEvent");
		
		// Prüfe ob das empfangene Event vom Typ TokenEvent ist und eine Application beinhaltet
		if (EventUtils.hasProperty(event, "type") && EventUtils.findPropertyByKey(event, "type").getValue().equals("application")) {
			applicationEvent = event;
			applicationEvent.setType("ApplicationEvent");
			
			
			//FRAGE: WIRD LINK VON DR GLEICH MIT GESCHICKT? 
			
			
				// Sendet das Event an ? (welches Topic ???) 
				try {
					getAgent().send(applicationEvent, "TOPIC");
				} catch (NoValidEventException e1) {
					LoggerFactory.getLogger("ApplicationSend");
				} catch (NoValidTargetTopicException e1) {
					LoggerFactory.getLogger("ApplicationSend");
				}
				
		}
		
		
		
	}

}
