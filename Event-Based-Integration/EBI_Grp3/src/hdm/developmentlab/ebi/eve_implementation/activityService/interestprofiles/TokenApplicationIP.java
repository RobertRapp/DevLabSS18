package hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles;



import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;
import hdm.developmentlab.ebi.eve_implementation.events.ApplicationEvent;
import hdm.developmentlab.ebi.eve_implementation.events.TokenEvent;


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
		if (EventUtils.findPropertyByKey(event, "type") != null && EventUtils.findPropertyByKey(event, "Type").getValue() == "Application") {
			System.out.println("IN IF");
			applicationEvent = event; 	
			applicationEvent.setType("ApplicationEvent");
			System.out.println("TIMESTAMP : ");
			System.out.println("NeuEvent: " + applicationEvent.getCreationDate());
			System.out.println("NeuEvent: " + event.getCreationDate());
			
				// Sendet das Event an DR (welches Topic ???) 
				try {
					System.out.println("WIRD GESENDET");
					getAgent().send(applicationEvent, "TOPIC");
				} catch (NoValidEventException e1) {
					java.util.logging.Logger logger = LoggerFactory.getLogger("ApplicationSend");
				} catch (NoValidTargetTopicException e1) {
					java.util.logging.Logger logger = LoggerFactory.getLogger("ApplicationSend");
				}
				
		}
		
		
		
	}

}
