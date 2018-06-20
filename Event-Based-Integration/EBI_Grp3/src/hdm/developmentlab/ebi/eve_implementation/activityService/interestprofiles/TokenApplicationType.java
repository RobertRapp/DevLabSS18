package hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles;



import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import hdm.developmentlab.ebi.eve_implementation.events.ApplicationEvent;
import hdm.developmentlab.ebi.eve_implementation.events.TokenEvent;


public class TokenApplicationType extends eventprocessing.agent.interestprofile.AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(TokenApplicationType.class);
	
	// Factory für die Erzeugung der Events
	private AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	
	@Override
	protected void doOnReceive(AbstractEvent event) {
		// Erzeugt über die Factory ein neues Event
		ApplicationEvent e = (ApplicationEvent) eventFactory.createEvent("????");
			
		// Prüfe ob das empfangene Event vom Typ TokenEvent ist und eine Application beinhaltet
		//HIER MIT PREDICATES IN IF CONDITION ARBEITEN! 
		if (event instanceof TokenEvent) {
				// Alle benötigten Informationen werden aus dem Event entnommen
				//e.setApplicationID(tokenEvent.getChunkID());
				//e.setApplicationName(tokenEvent.getChunkSemantic());
				e.setLink("missing");
				
				// Sendet das Event an DR (welches Topic ???) 
				try {
					getAgent().send(e, "DR Topic ???");
				} catch (NoValidEventException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", e));
				} catch (NoValidTargetTopicException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", e));
				}
				
		}
		
		
		
	}

}
