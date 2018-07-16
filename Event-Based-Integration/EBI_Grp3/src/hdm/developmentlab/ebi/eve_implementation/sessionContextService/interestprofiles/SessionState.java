
package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.AtomicEvent;
import eventprocessing.event.EventIdProvider;
import eventprocessing.event.Property;
import eventprocessing.utils.TimeUtils;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.EventFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenApplicationIP;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.SessionContextAgent;

public class SessionState extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static Logger LOGGER = LoggerFactory.getLogger(SessionState.class);

	/**
	 * Empfängt das Event, dass ein Gespräch gestartet ist und erzuegt dafür ein
	 * neues SessionEvent, das während des Gesprächs mit weiteren Informationen
	 * befüllt wird. Abgesendet wird ein SessionEvent erst wenn das Event "Gespräch
	 * beendet" zu einem passenden SessionStartEvent ankommt.
	 * 
	 * @param arg0
	 */
	@Override
	protected void doOnReceive(AbstractEvent event) { System.out.println(this.getClass().getSimpleName() + " : Event angekommen "+event.getType()+" - " + TimeUtils.getCurrentTime());
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

		if (event.getType().equalsIgnoreCase("SessionEndEvent")) {
			AbstractEvent session = sA.getSessionById(event.getValueByKey("sessionID").toString());
			session.add(new Property<>("sessionEnd", TimeUtils.getCurrentTime()));
			session.setType("SessionState");
			try {
				System.out.println("Neuer SessionState raus geschickt");
				sA.send(session, "SessionState");
				sA.getSessions().remove(session);
			} catch (NoValidEventException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoValidTargetTopicException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*
		 * Ein Interessensprofil kann ebenfalls Events publizieren, hierfür wird erstmal
		 * ein Event erzeugt, das über die eventFactory erzeugt wird. Es handelt sich
		 * dabei im Rahmen dieses Projekts um ein AtomicEvent
		 */

		AbstractEvent newSession = eventFactory.createEvent("AtomicEvent");

		/*
		 * Werden einzelne Attribute eines Events übernommen kann dafür eine Schleife
		 * über die Properties gehen.
		 */
		for (Property<?> p : event.getProperties()) {
			newSession.addOrReplace(p);
		}
		// erzeugen einer SessionID wenn noch keine vorhanden ist.
		if (EventUtils.findPropertyByKey(newSession, "sessionID") == null) {
			newSession.add(new Property<>("sessionID", event.hashCode() + System.currentTimeMillis()));
			System.out.println("neue Sessionid wird hinzugefügt "
					+ EventUtils.findPropertyByKey(newSession, "sessionID").getValue());
		} else if (EventUtils.findPropertyByKey(newSession, "sessionID").getValue() == "") {
			newSession.getProperties().remove(newSession.getPropertyByKey("sessionID"));
			newSession.add(new Property<String>("sessionID" + event.hashCode() + System.currentTimeMillis()));
		}

		/*
		 * Jede Session hat ebenfalls einen SessionContext, wovon die Attribute mittels
		 * Properties festgelegt werden. Hier werden Default-Werte festgelegt
		 */
		AbstractEvent createdSessionContext = eventFactory.createEvent(("AtomicEvent"));
		createdSessionContext.setType("SessionContextEvent");
		createdSessionContext.add(newSession.getPropertyByKey("sessionID"));
		createdSessionContext.add(new Property<String>("project"));
		createdSessionContext.add(new Property<String>("topic"));
		createdSessionContext.add(new Property<>("teilnehmer1", event.getValueByKey("userID")));
		createdSessionContext.add(new Property<>("teilnehmer2", event.getValueBySecoundMatch("userID")));
		newSession.add(new Property<AbstractEvent>("sessionContext", createdSessionContext));

		try {
			AbstractEvent ersteAnfrage = eventFactory.createEvent("AtomicEvent");
			ersteAnfrage.setType("DocRequestEvent");
			ersteAnfrage.add(new Property<>("teilnehmer1", event.getValueByKey("userID")));
			ersteAnfrage.add(new Property<>("teilnehmer2", event.getValueBySecoundMatch("userID")));
			ersteAnfrage.add(new Property<String>("keyword", "protocol"));
			// Publizieren von Events über die send-Methode des Agenten.
			System.out.println("SessionContext wird raus geschickt");
			sA.send(createdSessionContext, "SessionContext");
			sA.addSession(event);

		} catch (NoValidEventException e) {
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			e.printStackTrace();
		}
	}

}
