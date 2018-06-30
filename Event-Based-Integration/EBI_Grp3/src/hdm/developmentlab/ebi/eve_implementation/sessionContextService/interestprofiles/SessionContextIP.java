package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;



import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;


public class SessionContextIP extends eventprocessing.agent.interestprofile.AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(SessionContextIP.class);
	
	// Factory für die Erzeugung der Events
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static AbstractEvent lastSessionContext = eventFactory.createEvent("AtomicEvent");
	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	
	
	
	@Override
	protected void doOnReceive(AbstractEvent event) {
		System.out.println("RECEIVEDSCIP");
		System.out.println("DAs ist der letzte SC");
		System.out.println(lastSessionContext);
		// Erzeugt über die Factory ein neues Event
		AbstractEvent sessionContext = eventFactory.createEvent("AtomicEvent");
		AbstractEvent tokenEvent = eventFactory.createEvent("AtomicEvent");
		
		sessionContext.setType("SessionContext");
		tokenEvent = event;
		

		//Pr�fen, ob sich der SessionContext ge�ndert hat 
		if(lastSessionContext.getProperties().size() < 1) {
			System.out.println("Alter SessionC exisitiert nicht! ");
			sessionContext.add(EventUtils.findPropertyByKey(tokenEvent, "project"));
			sessionContext.add(EventUtils.findPropertyByKey(tokenEvent, "topic"));
			sessionContext.add(EventUtils.findPropertyByKey(tokenEvent, "timereference"));
			sessionContext.add(EventUtils.findPropertyByKey(tokenEvent, "testggggg"));
			
			//Neuer SessionContext f�r den weiteren Verlauf als lastSessionContext abspeichern
			System.out.println("DAs bekommt der letzte SC: " + sessionContext);
			lastSessionContext = sessionContext;
		} else {
			//Pr�fen, ob sich das Projekt ge�ndert hat
			if(!EventUtils.findPropertyByKey(tokenEvent, "project").getValue().equals(EventUtils.findPropertyByKey(lastSessionContext, "project").getValue())) {
			System.out.println("PROJEKT HAT SICH GE�NDERT!");
			sessionContext.add(EventUtils.findPropertyByKey(tokenEvent, "project"));
			
			
			//Neuer SessionContext f�r den weiteren Verlauf als lastSessionContext abspeichern
			System.out.println("DAs bekommt der letzte SC: " + sessionContext);
			lastSessionContext = sessionContext;
			}
		}
		
		
		

			// Sendet das Event an ? (welches Topic ???) 
			try {
				System.out.println("Send the foll. SC: ");
				System.out.println(sessionContext);
				getAgent().send(sessionContext, "TOPIC");
			} catch (NoValidEventException e1) {
				LoggerFactory.getLogger("SessionContextSend");
			} catch (NoValidTargetTopicException e1) {
				LoggerFactory.getLogger("SessionContextSend");
			}
				
		}
		
		
		
	}


