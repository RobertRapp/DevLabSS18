package eventprocessing.utils.mapping;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import eventprocessing.utils.TimeUtils;

/**
 * Verarbeitung von Zeit und Datum. Das datePattern setzt das Muster für die
 * Repräsentation des Formats fest.
 * 
 * 
 * Wird aktuell in den <code>AbstractEvent</code> genutzt, um bei der
 * Instanziierung von Events einen Zeitstempel zu generieren.
 * 
 * @author IngoT
 *
 */
public final class DateTimeMapper implements Serializable {

	private static final long serialVersionUID = 2969110073970532699L;

	/**
	 * Konvertierung eines Strings in ein Timestamp
	 * 
	 * @param dateString,
	 *            der in ein Timestamp konvertiert werden soll.
	 * @return formatDateTime, der übergebene String als Timestamp
	 * @throws ParseException,
	 *             wenn bei der Konvertierung ein Fehler aufkommt.
	 */
	public Timestamp convertToTimestamp(String dateString) throws ParseException {
		if (dateString != null) {
			// Das pattern für die Konvertierung wird gesetzt
			SimpleDateFormat dateFormat = new SimpleDateFormat(TimeUtils.getDateFormat());
			// Der String wird in ein Date-Format überführt
			Date parsedDate = dateFormat.parse(dateString);
			// Konvertierung in ein timestamp
			Timestamp timestamp = new Timestamp(parsedDate.getTime());
			// Rückgabe des Datum als Timestamp
			return timestamp;
		} else {
			throw new IllegalArgumentException(String.format("null is not allowed"));
		}
	}

	/**
	 * Erzeugt aus dem Timestamp ein String.
	 * 
	 * @param date,
	 *            Das Datum, welches in einen String umgewandelt werden soll
	 * @return String, Die übergebene Zeitangabe als String
	 */
	public String convertToString(Timestamp date) {
		if (date != null) {
			return date.toString();
		} else {
			throw new IllegalArgumentException(String.format("null is not allowed"));
		}
	}

}
