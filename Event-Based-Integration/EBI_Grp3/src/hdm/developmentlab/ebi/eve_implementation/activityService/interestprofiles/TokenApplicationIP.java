package hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles;



import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
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
			
			if(event.getType().equalsIgnoreCase("CalendarEvent")) event.add(new Property<String>("URL","calendar.google.com"));
			String type = (String) event.getPropertyByKey("ApplicationType").getValue();			
			switch (type) {
			case "presentation":
				event.add(new Property<String>("URL","docs.google.com/presentation"));	
				break;
			case "spreadsheets":
				event.add(new Property<String>("URL","docs.google.com/spreadsheets"));	
				break;
			default:
				event.add(new Property<String>("URL",type+".google.com"));
				break;
			}
			System.out.println("Wir schlagen vor auf "+event.getPropertyByKey("URL")+ " zu gehen!");
				try {
					event.setType("ApplicationEvent");
					getAgent().send(event, "Applications");
				} catch (NoValidEventException e1) {
					LoggerFactory.getLogger("ApplicationSend");
				} catch (NoValidTargetTopicException e1) {
					LoggerFactory.getLogger("ApplicationSend");
				}
				
		}
		
		
		
	
}
