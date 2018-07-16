package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;

<<<<<<< HEAD


=======
import java.util.LinkedHashMap;
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
<<<<<<< HEAD
=======
import eventprocessing.utils.TimeUtils;
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.SessionContextAgent;

<<<<<<< HEAD

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
=======
public class SessionContextIP extends eventprocessing.agent.interestprofile.AbstractInterestProfile {

	/**
	 * Empfängt ein Event und prüft ob das neue Token den aktuellen SessionContext
	 * im Gespräch verändert,
	 * 
	 * ist das der Fall wird die gewonnene Information als SessionContextUpdate
	 * publiziert.
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(SessionContextIP.class);

	// Factory für die Erzeugung der Events
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static AbstractEvent lastSessionContext = null;

>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
<<<<<<< HEAD
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


=======
	 */
	@Override
	protected void doOnReceive(AbstractEvent event) {
		System.out.println(this.getClass().getSimpleName() + " : Event angekommen " + event.getType() + " - um: "
				+ TimeUtils.getCurrentTime());
		/**
		 * 
		 * In dieser Methode wird die Verarbeitung eines Events gemacht. D. h. wie der
		 * Agent auf ein bestimmtes Event reagieren soll. Hierfür ist keine weitere
		 * Abprüfung nötig, ob das Event dem entspricht was als Predicates für das
		 * Interessensprofile festgelegt wurde.
		 */

		/*
		 * Innerhalb des Interessensprofils kann der Agent verwendet werden, dem dieses
		 * IP zugewiesen ist.
		 */
		SessionContextAgent sA = (SessionContextAgent) this.getAgent();

		/*
		 * Ein Interessensprofil kann ebenfalls Events publizieren, hierfür wird erstmal
		 * ein Event erzeugt, das über die eventFactory erzeugt wird. Es handelt sich
		 * dabei im Rahmen dieses Projekts um ein AtomicEvent
		 */

		// Auslesen eines SessionContexts
		// System.out.println("Alle Sessions"+sA.getSessions().get(0) +" Vergleichswert
		// aus Event"+ event.getValueByKey("sessionID"));
		AbstractEvent currentSession = (AbstractEvent) sA.getSessions().get(0);

		AbstractEvent sessionContext = eventFactory.createEvent("AtomicEvent");
		sessionContext.setType("SessionContext");

		/**
		 * Je nach Projektevent wird eine Person, der Projekt-Name oder ein Dokument als
		 * Thema ausgewählt.
		 */
		switch (event.getType()) {
		case "ProjectEvent":
			sessionContext.addOrReplace(EventUtils.findPropertyByKey(event, "SessionID"));
			Property<?> sentenceID = EventUtils.findPropertyByKey(event, "SentenceID");
			for (Property<?> p : event.getProperties()) {
				switch (p.getKey().toLowerCase()) {
				case "person":
					LinkedHashMap<String, ?> hashmap = (LinkedHashMap<String, ?>) p.getValue();
					sessionContext.addOrReplace(new Property<>("topic",
							"Satz-ID: " + sentenceID + " -> Thema ist die Person " + hashmap.get("key")));
					break;
				case "project":
					LinkedHashMap<String, ?> hashmap1 = (LinkedHashMap<String, ?>) p.getValue();
					sessionContext.addOrReplace(new Property<>("topic",
							"Satz-ID: " + sentenceID + " ->Thema ist das Projekt " + hashmap1.get("key")));
				case "document":
					LinkedHashMap<String, ?> hashmap2 = (LinkedHashMap<String, ?>) p.getValue();
					sessionContext.addOrReplace(new Property<>("topic",
							"Satz-ID: " + sentenceID + " -> Thema ist das Document " + hashmap2.get("key")));
				default:

					break;
				}
			}
			break;

		/**
		 * Je nach PersonEvent wird eine Person, der Projekt-Name oder ein Dokument als
		 * Thema ausgewählt.
		 * 
		 */
		case "PersonEvent":
			sessionContext.addOrReplace(EventUtils.findPropertyByKey(event, "SessionID"));
			Property<?> sentenceID1 = EventUtils.findPropertyByKey(event, "SentenceID");
			for (Property<?> p : event.getProperties()) {
				switch (p.getKey().toLowerCase()) {
				case "person":
					LinkedHashMap<String, ?> hashmap = (LinkedHashMap<String, ?>) p.getValue();
					sessionContext.addOrReplace(new Property<>("topic",
							"Satz-ID: " + sentenceID1 + " ->Thema ist die Person " + hashmap.get("key")));
					break;
				case "project":
					LinkedHashMap<String, ?> hashmap1 = (LinkedHashMap<String, ?>) p.getValue();
					sessionContext.addOrReplace(new Property<>("topic",
							"Satz-ID: " + sentenceID1 + " -> Thema ist das Projekt " + hashmap1.get("key")));
				case "document":
					LinkedHashMap<String, ?> hashmap2 = (LinkedHashMap<String, ?>) p.getValue();
					sessionContext.addOrReplace(new Property<>("topic",
							"Satz-ID: " + sentenceID1 + " -> Thema ist das Dokument " + hashmap2.get("key")));
				default:

					break;
				}
			}
			break;

		/**
		 * Je nach DocumentEvent wird eine Person, der Projekt-Name oder ein Dokument
		 * als Thema ausgewählt.
		 * 
		 */
		case "DocumentEvent":
			sessionContext.addOrReplace(EventUtils.findPropertyByKey(event, "SessionID"));
			Property<?> sentenceID2 = EventUtils.findPropertyByKey(event, "SentenceID");
			for (Property<?> p : event.getProperties()) {
				switch (p.getKey().toLowerCase()) {
				case "person":
					LinkedHashMap<String, ?> hashmap = (LinkedHashMap<String, ?>) p.getValue();
					sessionContext.addOrReplace(new Property<>("topic",
							"Satz-ID: " + sentenceID2 + " -> Thema ist die Person " + hashmap.get("key")));
					break;
				case "project":
					LinkedHashMap<String, ?> hashmap1 = (LinkedHashMap<String, ?>) p.getValue();
					sessionContext.addOrReplace(new Property<>("topic",
							"Satz-ID: " + sentenceID2 + " -> Thema ist das Projekt " + hashmap1.get("key")));
				case "document":
					LinkedHashMap<String, ?> hashmap2 = (LinkedHashMap<String, ?>) p.getValue();
					sessionContext.addOrReplace(new Property<>("topic",
							"Satz-ID: " + sentenceID2 + " -> Thema ist das Document " + hashmap2.get("key")));
				default:

					break;
				}
			}
			break;

		default:
			break;
		}
		/**
		 * 
		 * sessionContext wird mit neuen Informationen befüllt
		 */
		Property<?> newSessionContextProperty = new Property<AbstractEvent>("sessionContext", sessionContext);
		AbstractEvent newSession = EventUtils.replacePropertyByKey(currentSession, "sessionContext",
				newSessionContextProperty);
		sA.getSessions().set(sA.getSessions().indexOf(currentSession), newSession);
		try {
			this.getAgent().send(sessionContext, "SessionContext");
		} catch (NoValidEventException e) {

			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
