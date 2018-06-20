package eventprocessing.utils.factory;

import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.AtomicEvent;
import eventprocessing.event.ComplexEvent;
import eventprocessing.event.EventIdProvider;
import eventprocessing.utils.TextUtils;
import eventprocessing.utils.TimeUtils;

/**
 * Die Factory erzeugt beim Aufruf ein neues <code>AbstractEvent</code>
 * 
 * @author IngoT
 *
 */
public final class EventFactory extends AbstractFactory {

	private static final long serialVersionUID = 8072849773695560526L;
	private static Logger LOGGER = LoggerFactory.getLogger(EventFactory.class.getName());

	/**
	 * Erzeugt ein <code>AbstractEvent</code> anhand des übergebenen Strings. Die
	 * Methode ist nicht case sensitive und wenn kein passendes AbstractEvent
	 * gefunden wurde, wird null zurückgegeben.
	 * 
	 * @param String,
	 *            Name der Klasse des AbstractEvent
	 * @return AbstractEvent, das AbstractEvent, welches auf Basis des Strings
	 *         gefunden wurde.
	 */
	@Override
	public AbstractEvent createEvent(String event) {
		LOGGER.log(Level.INFO, () -> String.format("Try to create a new event of type: %s", event));
		// Wenn der übergebene String leer ist
		if (TextUtils.isNullOrEmpty(event)) {
			LOGGER.log(Level.WARNING, "String is null or empty, no event can be created!");
			return null;
		}
		// Erzeugung der Instanz
		AbstractEvent newEvent = null;
		/**
		 * Auflistung der Events die erzeugt werden können.
		 */
		if (event.equalsIgnoreCase(FactoryValues.INSTANCE.getAtomicEvent())) {
			LOGGER.log(Level.FINE, () -> String.format("Returning event: %s", event));
			newEvent = new AtomicEvent();
		} else if (event.equalsIgnoreCase(FactoryValues.INSTANCE.getComplexEvent())) {
			LOGGER.log(Level.FINE, () -> String.format("Returning event: %s", event));
			newEvent = new ComplexEvent();
		} 
		// Wenn der Name zu keinem Event passt.
		else {
			// Wenn kein passendes Event gefunden wurde.
			LOGGER.log(Level.WARNING, "No event type %s found", event);
			return null;
		}

		/*
		 * Bei der Erzeugung einer neuen Instanz wird für das Event das aktuelle Datum
		 * (Abhänging von der Systemzeit) erzeugt und eine eindeutige ID vergeben.
		 */
		newEvent.setId(EventIdProvider.INSTANCE.getUniqueId());
		newEvent.setCreationDate(TimeUtils.getCurrentTime());
		
		LOGGER.log(Level.FINE, () -> String.format("Returning event: %s", event));
		// die neue Instanz wird zurückgegeben
		return newEvent;

	}

	/**
	 * werden hier nicht benötigt
	 */

	@Override
	public AbstractAgent createAgent(String agent) {
		return null;
	}

	@Override
	public AbstractInterestProfile createInterestProfile(String interestProfile) {
		return null;
	}

	@Override
	public AbstractPredicate createPredicate(String predicate) {
		return null;
	}

}
