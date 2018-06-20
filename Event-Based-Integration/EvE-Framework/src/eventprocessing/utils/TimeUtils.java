package eventprocessing.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Hilfsklasse für die Berechnung der Differenz von zwei Zeitstempeln. Die
 * Ergebnisse werden immer als Ganzzahl zurückgegeben.
 * 
 * @author IngoT
 *
 */
public final class TimeUtils {

	// Angabe der Zeitzone, um die Uhrzeiten korrekt zu setzen
	private final static String TIME_ZONE_ID = "Europe/Budapest";
	// Format wie der Zeitstempel ausgegeben werden soll
	private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	private TimeUtils() {}
	
	
	/**
	 * Gibt die Zeitzone zurück.
	 * 
	 * @return TIME_ZONE_ID, Zeitzone im String-Format
	 */
	public static String getTimeZoneId() {
		return TIME_ZONE_ID;
	}

	/**
	 * Gibt das Datumsformat zurück.
	 * 
	 * @return DATE_FORMAT, Format für den Zeitstempel
	 */
	public static String getDateFormat() {
		return DATE_FORMAT;
	}

	/**
	 * Berechnet zwischen zwei Zeitstempeln die Differenz in Millisekunden
	 * 
	 * @param aTimestamp,
	 *            der erste Zeitstempel für die Berechnung
	 * @param otherTimestamp,
	 *            der zweite Zeitstempel für die Berechnung
	 * @return Die Differenz in Millisekunden als long
	 */
	public static long getDifferenceInMilliseconds(Timestamp aTimestamp, Timestamp otherTimestamp) {
		// Überprüfung ob die Zeitstempel nicht null sind
		if (aTimestamp != null && otherTimestamp != null) {
			// Die Zeit wird voneinander abgezogen
			long result = aTimestamp.getTime() - otherTimestamp.getTime();
			// und zurückgegeben
			return result;
		}
		// Sollte eine Instanz null sein, wird 0 zurückgegeben
		return 0;
	}
	
	/**
	 * Gibt den Wert des Zeitstempels in Millisekunden zurück
	 * 
	 * @param t, der Zeitstempel der umgerechnet werden soll
	 * @return long, Zeitstempel in Millisekunden
	 */
	public static long getTimestampInMilliseconds(Timestamp timestamp) {
		// Prüfung ob der Zeitstempel nicht null ist
		if(timestamp != null) {
			// Gebe die Zeit in Millisekunden zurück
			return timestamp.getTime();
		}
		// Sonst gebe 0 zurück
		return 0;
	}

	/**
	 * Berechnet zwischen zwei Zeitstempeln die Differenz in Sekunden
	 * 
	 * @param aTimestamp,
	 *            der erste Zeitstempel für die Berechnung
	 * @param otherTimestamp,
	 *            der zweite Zeitstempel für die Berechnung
	 * @return Die Differenz in Sekunden als int
	 */
	public static int getDifferenceInSeconds(Timestamp aTimestamp, Timestamp otherTimestamp) {
		long milliseconds = getDifferenceInMilliseconds(aTimestamp, otherTimestamp);
		int result = (int) milliseconds / 1000;
		return result;
	}

	/**
	 * Berechnet zwischen zwei Zeitstempeln die Differenz in Minuten
	 * 
	 * @param aTimestamp,
	 *            der erste Zeitstempel für die Berechnung
	 * @param otherTimestamp,
	 *            der zweite Zeitstempel für die Berechnung
	 * @return Die Differenz in Minuten als int
	 */
	public static int getDifferenceInMinutes(Timestamp aTimestamp, Timestamp otherTimestamp) {
		int seconds = getDifferenceInSeconds(aTimestamp, otherTimestamp);
		int minutes = (seconds % 3600) / 60;
		return minutes;
	}

	/**
	 * Berechnet zwischen zwei Zeitstempeln die Differenz in Stunden
	 * 
	 * @param aTimestamp,
	 *            der erste Zeitstempel für die Berechnung
	 * @param otherTimestamp,
	 *            der zweite Zeitstempel für die Berechnung
	 * @return Die Differenz in Stunden als int
	 */
	public static int getDifferenceInHours(Timestamp aTimestamp, Timestamp otherTimestamp) {
		int minutes = getDifferenceInMinutes(aTimestamp, otherTimestamp);
		int hours = minutes / 60;
		return hours;
	}

	/**
	 * prüft ob eine Nummer negativ ist oder nicht.
	 * 
	 * @param number,
	 *            die geprüft werden soll
	 * @return true, wenn sie kleiner 0 ist und false, wenn sie 0 oder größer ist.
	 */
	public static boolean isNegative(long number) {
		if (number < 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Ändert das Vorzeichen einer Zahl.
	 * 
	 * @param number,
	 *            dessen Vorzeichen geändert werden soll.
	 * @return number mit umgekehrten Vorzeichen.
	 */
	public static long changeSign(long number) {
		return number * -1;
	}

	/**
	 * Erzeugt das aktuelle Datum mit Uhrzeit in Timestamp-Format
	 * 
	 * @return Timestamp, aktuelles Datum + Uhrzeit
	 */
	public static Timestamp getCurrentTime() {
		// Gibt die aktuelle Systemuhrzeit zurück
		Instant now = Instant.now();
		// Die Zeitzone wird eingeführt
		ZonedDateTime zdt = now.atZone(ZoneId.of(getTimeZoneId()));
		// Das Format wird in einen Zeitstempel überführt
		Timestamp timestamp = Timestamp.from(zdt.toInstant());
		// Rückgabe des Zeitstempels
		return timestamp;
	}

}
