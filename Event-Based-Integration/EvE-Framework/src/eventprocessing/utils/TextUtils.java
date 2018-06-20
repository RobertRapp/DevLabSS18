package eventprocessing.utils;

import java.lang.reflect.Array;
import java.math.BigInteger;

/**
 * Hilfsklasse für die Stringverarbeitung.
 * 
 * @author IngoT
 *
 */
public final class TextUtils {

	private static final String SINGLE_QUOTE = "'";

	// Instanzen nicht vorgesehen, daher private
	private TextUtils() {

	}

	/**
	 * Prüft, ob ein String Null oder Empty ist. wenn ein String nur Leerzeichen
	 * enthält, wird er als Empty angesehen.
	 * 
	 * @param s,
	 *            String der überprüft wird.
	 * @return Boolean, True, wenn der String null oder Empty ist, False, wenn der
	 *         String Zeichen enthält (außer Leerzeichen)
	 */
	public static boolean isNullOrEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}

	/**
	 * Wandelt den String in eine 120 Zeichenlangen Hexwert um. Fehlende Stellen
	 * werden mit 0er aufgefüllt, sodass alle dieselbe Länge haben.
	 * 
	 * @param s,
	 *            String der konvertiert werden soll
	 * @return Hexdecimal String
	 */
	public static String toHex(String s) {
		return String.format("%0120x", new BigInteger(1, s.getBytes()));
	}

	/**
	 * wandelt ein Array in einen String um.
	 * 
	 * @param aArray
	 * @return aArray als String konvertiert
	 */
	public static String getArrayAsString(Object aArray) {

		// Zeichen die in einem Array vorkommen
		final String fSTART_CHAR = "[";
		final String fEND_CHAR = "]";
		final String fSEPARATOR = ", ";
		final String fNULL = "null";

		// Wenn das Array null ist, gebe "null" als String zurück
		if (aArray == null)
			return fNULL;
		// Überprüfung ob das Array überhaupt ein Array ist
		checkObjectIsArray(aArray);

		// erstes Zeichen eines Strings ist immer die [
		StringBuilder result = new StringBuilder(fSTART_CHAR);
		// Anzahl der Elemente
		int length = Array.getLength(aArray);
		// Iteration durch jedes Element des Arrays
		for (int idx = 0; idx < length; ++idx) {
			// Inhalt dem item zuweisen
			Object item = Array.get(aArray, idx);
			// Prüfung ob das Element selber ein Array ist
			if (isNonNullArray(item)) {
				// recursive call!
				result.append(getArrayAsString(item));
			}
			// sonst das Element dem Ergebnis hinzufügen
			else {
				result.append(item);
			}
			// Wenn es nicht das letzte Element war, wird der Separator gesetzt
			if (!isLastItem(idx, length)) {
				result.append(fSEPARATOR);
			}
		}
		// Am Ende das ] setzen
		result.append(fEND_CHAR);
		// Rückgabe des Strings
		return result.toString();
	}

	/**
	 * Prüfung ob das Object ein Array ist und nicht null ist
	 * 
	 * @param aItem,
	 *            das Object das geprüft werden soll
	 * @return true, wenn es nicht null ist und ein Array ist. False, wenn es null
	 *         ist oder kein Array
	 */
	private static boolean isNonNullArray(Object aItem) {
		return aItem != null && aItem.getClass().isArray();
	}

	/**
	 * einfache Prüfung, ob das Object ein Array ist
	 * 
	 * @param aArray
	 */
	private static void checkObjectIsArray(Object aArray) {
		if (!aArray.getClass().isArray()) {
			throw new IllegalArgumentException("Object is not an array.");
		}
	}

	/**
	 * Prüfung ob der Index das letzte Element im Array ist
	 * 
	 * @param aIdx,
	 *            der aktuelle Index
	 * @param aLength,
	 *            die Länge des Arrays
	 * @return true, wenn es das letzte Element ist und false, wenn es nicht das
	 *         letzte Element ist
	 */
	private static boolean isLastItem(int aIdx, int aLength) {
		return (aIdx == aLength - 1);
	}

	public static String quote(Object aObject) {
		return SINGLE_QUOTE + String.valueOf(aObject) + SINGLE_QUOTE;
	}
}
