package eventprocessing.utils.mapping;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eventprocessing.event.AbstractEvent;
import eventprocessing.event.AtomicEvent;
import eventprocessing.utils.TimeUtils;
import eventprocessing.utils.factory.LoggerFactory;

/**
 * Dient der Übersetzung von <code>AbstractEvent</code> in einen String (JSON-Format)
 * und umgekerht.
 * 
 * @author IngoT
 *
 */
public final class MessageMapper implements Serializable {

	private static final long serialVersionUID = 8176302099069175583L;
	private static Logger LOGGER = LoggerFactory.getLogger(MessageMapper.class.getName());
	// Für die Umwandlung der Strings und Events.
	private static final ObjectMapper mapper = new ObjectMapper();

	public MessageMapper() {
		DateFormat df = new SimpleDateFormat(TimeUtils.getDateFormat());
		mapper.setDateFormat(df);
	}
	/**
	 * Konvertiert ein <code>AbstractEvent</code> in einen String um.
	 * Die Klasse <code>AbstractAgent</code> nutzt diese Methode.
	 * 
	 * @param event, das versendet werden soll
	 * @return jsonString, repräsentiert das Event und kann versendet werden.
	 */
	public String toJSON(AbstractEvent event) {
		LOGGER.log(Level.FINE, () -> String.format("convert: %s", event));
		// Es wird versucht aus dem Event ein JSON-String zu erzeugen
		try {
			String jsonString = mapper.writeValueAsString(event);
			LOGGER.log(Level.FINE, () -> String.format("Json was created successfully: %s", jsonString));
			return jsonString;
		// Wenn bei der Konvertierung Fehler auftreten.
		} catch (JsonGenerationException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		} catch (JsonMappingException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		// Sonst null zurückgeben
		return null;
	}

	/**
	 * aus einem JSON-String wird Objekt vom Typ <code>AbstractEvent</code> erzeugt.
	 * 
	 * @param jsonString, Nachricht die umgewandelt werden soll in ein Event.
	 * @return event, dass die Informationen aus dem jsonString enthält.
	 */
	public AbstractEvent toEvent(String jsonString) {
		LOGGER.log(Level.FINE, () -> String.format("Convert JSON: %s", jsonString));
		// Aus dem String soll ein Event erzeugt werden.
		try {
			AtomicEvent event = mapper.readValue(jsonString, AtomicEvent.class);
			LOGGER.log(Level.FINE, () -> String.format("Event was created successfully: %s", event));
			// Rückgabe Event
			return event;
			// Wenn bei der Konvertierung Fehler auftreten.
		} catch (JsonParseException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		} catch (JsonMappingException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		// Bei Fehler wird null zurückgegeben
		return null;
	}
	
	

}
