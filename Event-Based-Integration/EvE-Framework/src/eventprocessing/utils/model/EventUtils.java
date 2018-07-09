package eventprocessing.utils.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.SystemUtils;
import eventprocessing.utils.TimeUtils;
import eventprocessing.utils.factory.LoggerFactory;

public final class EventUtils {

	private static Logger LOGGER = LoggerFactory.getLogger(EventUtils.class.getName());

	private EventUtils() {
		
	}
	
	/**
	 * Gibt das Alter des Events in Millisekunden zurück.
	 * 
	 * @return Alter des Event
	 */
	public static long getAge(AbstractEvent aEvent) {
		// Prüfung ob das Event null ist
		if (aEvent != null) {
			// Ruft die aktuelle Systemzeit ab
			Timestamp currentTime = TimeUtils.getCurrentTime();
			// Die Differenz der aktuellen Systemzeit und der Erstellungszeit wird berechnet
			long result = TimeUtils.getDifferenceInMilliseconds(aEvent.getCreationDate(), currentTime);
			// Das Ergebnis wird zurückgegeben
			return result;
		}
		return 0;
	}

	/**
	 * Berechnet den zeitlichen Abstand zu einem anderen Event in Millisekunden
	 * Liegt das Event vor dem zu vergleichenden Event, wird ein positiver Wert
	 * zurückgeliefert. Liegt das Event nach dem zu vergleichenden Event, wird ein
	 * negativer Wert zurückgeliefert.
	 * 
	 * @param aEvent,
	 *            Event von dem aus der Abstand berechnet wird
	 * @param otherEvent,
	 *            Event zu dem der Abstand berechnet werden soll.
	 * @return die Distanz in Millisekunden
	 */
	public static long distanceTo(AbstractEvent aEvent, AbstractEvent otherEvent) {
		// Prüfung ob das zu vergleichende Events nicht null sind
		if (aEvent != null && otherEvent != null) {
			// Differenz beider Zeitstempel werden in Millisekunden umgerechnet
			long result = TimeUtils.getDifferenceInMilliseconds(otherEvent.getCreationDate(), aEvent.getCreationDate());
			// Ergebnis wird zurückgegeben
			return result;
		}
		// Sonst gebe 0 zurück
		return 0;
	}

	/**
	 * Prüft ob das Event zeitlich vor dem zu überprüfenden Event liegt.
	 * 
	 * @param aEvent,
	 *            das erste Event, das geprüft wird
	 * @param otherEvent,
	 *            das zweite Event, das geprüft wird
	 * @return true, wenn es zeitlich vor dem anderen Event liegt. false, wenn es
	 *         zeitlich nach dem anderen Event liegt.
	 */
	public static boolean before(AbstractEvent aEvent, AbstractEvent otherEvent) {
		// Prüfung ob die Events nicht null ist
		if (aEvent != null && otherEvent != null) {
			// Umrechnung der beiden Zeitstempel in Millisekunden
			long otherTime = TimeUtils.getTimestampInMilliseconds(otherEvent.getCreationDate());
			long thisTime = TimeUtils.getTimestampInMilliseconds(aEvent.getCreationDate());
			/*
			 * Wenn der Wert des ausgehenden Events kleiner ist, liegt es zeitlich vor dem
			 * zu überprüfenden Event
			 */
			if (thisTime < otherTime) {
				return true;
				// Anderfalls liegt es nach dem zu vergleichenden Event
			} else {
				return false;
			}
			// Wenn die Events null sind, wird false zurückgegeben.
		} else {
			return false;
		}
	}

	/**
	 * Prüft die zeitliche Position zweier Events. Liegt das zu vergleichende Event
	 * zeitlich nach dem ausgehenden Event, wird true zurückgeliefert. Ist es
	 * zeitlich hinter dem ausgehenden Event, wird ein false zurückgeliefert.
	 * 
	 * @param aEvent,
	 *            das erste Event, das geprüft werden soll
	 * @param otherEvent,
	 *            das zweite Event, das geprüft werden soll
	 * @return false, wenn es zeitlich nach dem anderen Event liegt. true, wenn es
	 *         zeitlich nach dem anderen Event liegt.
	 */
	public static boolean after(AbstractEvent aEvent, AbstractEvent otherEvent) {
		// Prüfung ob die Events nicht null ist
		if (aEvent != null && otherEvent != null) {
			// Ergebnis der Methode before() wird umgekehrt
			return !(before(aEvent, otherEvent));
			// Wenn Event null ist, wird null zurückgegeben
		} else {
			return false;
		}
	}

	public static List<AbstractEvent> eventsWithin(long start, long end) {
		List<AbstractEvent> result = new ArrayList<AbstractEvent>();
		return result;
	}

	public static Property<?> findPropertyByKey(AbstractEvent event, String key) throws NoSuchElementException {
		if (event != null) {
			try {
				Property<?> resultProperty = event.getProperties().stream()
						.filter(property -> property.getKey().equalsIgnoreCase(key)).findFirst().get();
				return resultProperty;
			} catch (NoSuchElementException e) {
				LOGGER.log(Level.WARNING, () -> String.format("no matching property was found. Committed key: %s%s",
						SystemUtils.getLineSeparator(), key));
				return null;
			}
		} else {
			return null;
		}
	}

	public static <T> Property<?> findPropertyByValue(AbstractEvent event, T value) {
		if (event != null) {
			try {
				Property<?> resultProperty = event.getProperties().stream()
						.filter(property -> property.getValue().equals(value)).findFirst().get();
				return resultProperty;
			} catch (NoSuchElementException e) {
				LOGGER.log(Level.WARNING, () -> String.format("no matching property was found. Committed value: %s%s",
						SystemUtils.getLineSeparator(), value));
				return null;
			}
		} else {
			return null;
		}
	}
	
	public static AbstractEvent replacePropertyByKey(AbstractEvent event, String key, Property<?> property) {
		AbstractEvent newEvent = event;
		newEvent.remove(findPropertyByKey(event, (key)));
		newEvent.add(property);
		return newEvent;
		}
	
	public static boolean hasProperty(AbstractEvent event, String key) {
		
		Property<?> resultProperty = event.getProperties().stream()
				.filter(property -> property.getKey().equalsIgnoreCase(key)).findFirst().get();
		if(resultProperty != null) return true;
		return false;
		
	}
	
	public static boolean isType(String type, AbstractEvent event) {
		if (event != null) {
			if (event.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}
}
