package hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles;


import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.exceptions.NoValidEventException;
import eventprocessing.agent.exceptions.NoValidTargetTopicException;
import eventprocessing.event.AbstractEvent;
import eventprocessing.interestprofile.AbstractInterestProfile;
import eventprocessing.interestprofile.predicates.AbstractPredicate;
import eventprocessing.interestprofile.predicates.statement.HasProperty;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.AgentFactory;
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
	private AbstractFactory agentFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getAgentFactory());
	/**
	 * Empfängt Tokentypen und leitet damit eine neue Dokumentenvorschlagsanfrage ein.
	 * @param arg0
	 */

	@Override
	protected void doOnReceive(AbstractEvent event) {
		// Erzeugt über die Factory ein neues Event
		DocumentRequestEvent dr = (DocumentRequestEvent) eventFactory.createEvent("????");
		SessionContextAgent sc = (SessionContextAgent) agentFactory.createAgent("SessionContextAgent");
		
		
		
		// Prüfe ob das empfangene Event vom Typ TokenEvent ist und eine Application beinhaltet
		if (event instanceof TokenEvent) {
			// casten zu TokenEvent um Event auszulesen
				TokenEvent tokenEvent = (TokenEvent) event;

				AbstractPredicate predicate = new HasProperty("project");
				//Token bei Bedarf um Infos aus SessionContext anreichern 
				if(tokenEvent.getPropertyByKey("project").equals(null)) {
					tokenEvent.add(sc.getSessionById(tokenEvent.getSessionID()).getPropertyByKey("project"));
					dr.setToken(tokenEvent);

				} else 
					if(tokenEvent.getPropertyByKey("project").getValue() == null) {
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


