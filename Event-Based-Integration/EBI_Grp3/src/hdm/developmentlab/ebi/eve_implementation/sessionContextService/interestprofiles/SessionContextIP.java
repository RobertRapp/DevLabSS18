package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;



import java.util.logging.Level;
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
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.SessionContextAgent;


public class SessionContextIP extends eventprocessing.agent.interestprofile.AbstractInterestProfile {

	/**
	 * Empfängt ein Event und prüft ob das neue Token den aktuellen SessionContext im Gespräch verändert,
	 * 
	 * ist das der Fall wird die gewonnene Information als SessionContextUpdate publiziert.
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(SessionContextIP.class);
	
	// Factory für die Erzeugung der Events
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static AbstractEvent lastSessionContext = null;
	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */	
	
	@Override
	protected void doOnReceive(AbstractEvent event) {	
	/**
	 * 
	 * In dieser Methode wird die Verarbeitung eines Events gemacht. D. h. wie der Agent auf ein bestimmtes
	 * Event reagieren soll. Hierfür ist keine weitere Abprüfung nötig, ob das Event dem entspricht
	 * was als Predicates für das Interessensprofile festgelegt wurde.
	 */
	
	/*
	 * Innerhalb des Interessensprofils kann der Agent verwendet werden, dem dieses IP zugewiesen ist. 
	 */
	SessionContextAgent sA = (SessionContextAgent) this.getAgent();
	
	/*
	 * Ein Interessensprofil kann ebenfalls Events publizieren, hierfür wird erstmal ein Event erzeugt,
	 * das über die eventFactory erzeugt wird. Es handelt sich dabei im Rahmen dieses Projekts um ein AtomicEvent
	 */
	
	// Auslesen eines SessionContexts
	AbstractEvent currentSession = (AbstractEvent) sA.getSessionById(String.valueOf(event.getValueByKey("sessionID")));
	AbstractEvent currentSessionContext = (AbstractEvent) currentSession.getPropertyByKey("sessionContext").getValue();
	AbstractEvent sessionContext = eventFactory.createEvent("AtomicEvent");
	AbstractEvent tokenEvent = eventFactory.createEvent("AtomicEvent");
	sessionContext.setType("SessionContext");
		
	//Pr�fen, ob sich der SessionContext ge�ndert hat 
	AbstractEvent newSessionContext = eventFactory.createEvent("AtomicEvent");
	boolean abgeaendert = false;
	for (Property<?> pro: currentSessionContext.getProperties()){
		for(Property<?> pro2: event.getProperties()) {
			if(!pro.equals(pro2)){
				//properties sind ungleich
				if(pro.getKey() == pro2.getKey()) {
					newSessionContext.add(pro2);
				}
			}
		}
	}
	Property<?> newSessionContextProperty = new Property<AbstractEvent>("sessionContext", newSessionContext);
	AbstractEvent newSession = EventUtils.replacePropertyByKey(currentSession, "sessionContext", newSessionContextProperty);
	sA.getSessions().set(sA.getSessions().indexOf(currentSession), newSession);
	}
}


