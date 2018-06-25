package hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles;


import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;
import eventprocessing.agent.interestprofile.predicates.statement.HasProperty;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import hdm.developmentlab.ebi.eve_implementation.events.DocumentRequestEvent;
import hdm.developmentlab.ebi.eve_implementation.events.TokenEvent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.SessionContextAgent;


public class TokenDocumentType extends eventprocessing.agent.interestprofile.AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(TokenDocumentType.class);

	// Factory für die Erzeugung der Events
	private AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	
	/**
	 * Empfängt Tokentypen und leitet damit eine neue Dokumentenvorschlagsanfrage ein.
	 * @param arg0
	 */

	@Override
	protected void doOnReceive(AbstractEvent event) {
		// Erzeugt über die Factory ein neues Event
		AbstractEvent drEvent = eventFactory.createEvent(FactoryValues.INSTANCE.getAtomicEvent());
		AbstractEvent scAgent = eventFactory.createEvent(FactoryValues.INSTANCE.getAtomicEvent());
		
	
		//Hier if mit Zeitabprüfug und session context auf 30 sekunden oder so; TokenEvent ist es eigentlich schon
		// Prüfe ob das empfangene Event vom Typ TokenEvent ist undeinen Dokumententyp beinhaltet 
		if (EventUtils.isType("TokenEvent", event) && EventUtils.findPropertyByKey(event, "Type") == "Topic")) {
				Property<AbstractEvent> firstEvent = (Property<AbstractEvent>) EventUtils.findPropertyByKey(event, "FirstEvent");
				//Woher bekomm ich den SessionContext?
				Property<AbstractEvent> secondEvent = (Property<AbstractEvent>) EventUtils.findPropertyByKey(event, "SecondEvent");
				Property<Double> averageSpeed = (Property<Double>) EventUtils.findPropertyByKey(event,
					"AverageSpeed");

				//Token bei Bedarf um Infos aus SessionContext anreichern 
				if(EventUtils.findPropertyByKey(event, "project") = null) {
					tokenEvent.add(sc.getSessionById(tokenEvent.getSessionID()).getPropertyByKey("project"));
					dr.setToken(tokenEvent);

				} else 
					if(event.getPropertyByKey("project").getValue() == null) {
						tokenEvent.remove(tokenEvent.getPropertyByKey("project"));
						tokenEvent.add(sc.getSessionById(tokenEvent.getSessionID()).getPropertyByKey("project"));
						dr.setToken(tokenEvent);
					
				}
				
				if(tokenEvent.getPropertyByKey("timereference").getValue() == null) {
					dr.setTimeref(sc.getSessionById(tokenEvent.getSessionID()).getTimereference());
				}
				
				if(tokenEvent.getPropertyByKey("latestActivity").getValue() == null) {
					
				}
				
				if(tokenEvent.getPropertyByKey("users").getValue() == null) {
					dr.setUsers(sc.getSessionById(tokenEvent.getSessionID()).getUsers());
				}
				
				if(tokenEvent.getPropertyByKey("sessionId").getValue() == null) {
					dr.setSessionId(sc.getSessionById(tokenEvent.getSessionID()).getSessionId());
				}
				
				
				// Sendet das Event an DR (welches Topic ???) 
				try {
					getAgent().send(dr, "DR Topic ???");
				} catch (NoValidEventException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", dr));
				} catch (NoValidTargetTopicException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", dr));
				}
				
		}
		
		
		
	}
		
	}


