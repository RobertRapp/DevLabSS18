package hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;
import eventprocessing.agent.interestprofile.predicates.statement.HasProperty;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.TimeUtils;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;
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
		AbstractEvent requestEvent = eventFactory.createEvent(FactoryValues.INSTANCE.getAtomicEvent());
		AbstractEvent lastSessionContextEvent = eventFactory.createEvent(FactoryValues.INSTANCE.getAtomicEvent());
		
		//Wird ein neues SessionContextEvent empfangen, so wird dies als letzter und damit aktuellster SessionContext abgespeichert
		if (EventUtils.isType("SessionContext", event)) {
			lastSessionContextEvent = event;
			lastSessionContextEvent.setType("SessionContextEvent");
		}
		
	
		//Hier if mit Zeitabprüfug und session context auf 30 sekunden oder so; TokenEvent ist es eigentlich schon
		// Prüfe ob das empfangene Event vom Typ TokenEvent ist undeinen Dokumententyp beinhaltet 
		if (EventUtils.isType("TokenEvent", event) && EventUtils.findPropertyByKey(event, "Type").getValue() == "Topic") {
				requestEvent = event; 
				requestEvent.setType("RequestEvent");
				
				//Wenn letzter SessionContext nicht zu weit zurück liegt, wird das Tokenevent (bei Bedarf) um den aktuellen SC angereichert
				if(TimeUtils.getDifferenceInSeconds(lastSessionContextEvent.getCreationDate(), requestEvent.getCreationDate()) >= 120) {
					
				
					//Enthält TokenEvent keine Property namens project (oder eine der folgenden Namen) oder ist der jeweilige Wert gleich null, so wird das Projekt des SC angehängt 
					if(EventUtils.findPropertyByKey(requestEvent, "project") == null) {
						requestEvent.add(lastSessionContextEvent.getPropertyByKey("project"));
					} else 
						if(EventUtils.findPropertyByKey(requestEvent, "project").getValue() == null) {
							requestEvent.remove(EventUtils.findPropertyByKey(requestEvent, "project"));
							requestEvent.add(EventUtils.findPropertyByKey(lastSessionContextEvent, "project"));
						
					}
					
					if(EventUtils.findPropertyByKey(requestEvent, "timereference") == null) {
						requestEvent.add(lastSessionContextEvent.getPropertyByKey("timereference"));
					} else 
						if(EventUtils.findPropertyByKey(requestEvent, "timereference").getValue() == null) {
							requestEvent.remove(EventUtils.findPropertyByKey(requestEvent, "timereference"));
							requestEvent.add(EventUtils.findPropertyByKey(lastSessionContextEvent, "timereference"));
					}
					
					if(EventUtils.findPropertyByKey(requestEvent, "latestActivity") == null) {
						requestEvent.add(lastSessionContextEvent.getPropertyByKey("latestActivity"));
					} else 
						if(EventUtils.findPropertyByKey(requestEvent, "latestActivity").getValue() == null) {
							requestEvent.remove(EventUtils.findPropertyByKey(requestEvent, "latestActivity"));
							requestEvent.add(EventUtils.findPropertyByKey(lastSessionContextEvent, "latestActivity"));
					}
					
					if(EventUtils.findPropertyByKey(requestEvent, "users") == null) {
						requestEvent.add(lastSessionContextEvent.getPropertyByKey("users"));
					} else 
						if(EventUtils.findPropertyByKey(requestEvent, "users").getValue() == null) {
							requestEvent.remove(EventUtils.findPropertyByKey(requestEvent, "users"));
							requestEvent.add(EventUtils.findPropertyByKey(lastSessionContextEvent, "users"));
						
					}
					
					if(EventUtils.findPropertyByKey(requestEvent, "sessionId") == null) {
						requestEvent.add(lastSessionContextEvent.getPropertyByKey("sessionId"));
					} else 
						if(EventUtils.findPropertyByKey(requestEvent, "sessionId").getValue() == null) {
							requestEvent.remove(EventUtils.findPropertyByKey(requestEvent, "sessionId"));
							requestEvent.add(EventUtils.findPropertyByKey(lastSessionContextEvent, "sessionId"));
					}
				
				
				}
				
				// Sendet das Event an DR (welches Topic ???) 
				try {
					getAgent().send(requestEvent, "DR Topic ???");
				} catch (NoValidEventException e1) {
					java.util.logging.Logger logger = LoggerFactory.getLogger("RequestSend");
				} catch (NoValidTargetTopicException e1) {
					java.util.logging.Logger logger = LoggerFactory.getLogger("RequestSend");
				}
				
		}
		
		
		
	}
		
	}


