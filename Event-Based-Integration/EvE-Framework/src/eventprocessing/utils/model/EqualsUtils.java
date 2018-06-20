package eventprocessing.utils.model;

/**
 * Helferklasse für den Vergleich der Member zwischen zwei Objekten.
 * 
 * @author IngoT
 *
 */
public final class EqualsUtils {

	private EqualsUtils() {
	}

	/**
	 * Einfach Überprüfung ob zwei Objekte identisch sind.
	 * 
	 * @param aThis,
	 *            erstes Objekt zur Überprüfung
	 * @param aThat,
	 *            zweites Objekt zur Überprüfung
	 * @return true, wenn es sich um dasselbe Objekt handelt und false, wenn es zwei
	 *         verschiedene sind.
	 */
	public static Boolean quickEquals(Object aThis, Object aThat) {
		Boolean result = null;
		// Überprüfung der Identität
		if (aThis == aThat) {
			// Wenn identisch, gebe true zurück
			result = Boolean.TRUE;
		}
		// sonst
		else {
			// Überprüfe ob die zwei Objekte zur selben Klasse gehören
			Class<?> thisClass = aThis.getClass();
			if (!thisClass.isInstance(aThat)) {
				// Wenn nein, gebe false zurück
				result = Boolean.FALSE;
			}
		}
		return result;
	}

	// Prüft ob die Booleans den selben Zustand haben
	static public boolean areEqual(boolean aThis, boolean other) {
		return aThis == other;
	}

	// Prüft ob das Zeichen oder auch Strings den selben Wert bzw. Inhalt aufweist.
	static public boolean areEqual(char aThis, char other) {
		return aThis == other;
	}

	// Prüft long, byte, short und int.
	static public boolean areEqual(long aThis, long other) {
		return aThis == other;
	}

	// Prüft die float-Werte
	static public boolean areEqual(float aThis, float other) {
		return Float.floatToIntBits(aThis) == Float.floatToIntBits(other);
	}

	// Prüft die double-Werte
	static public boolean areEqual(double aThis, double other) {
		return Double.doubleToLongBits(aThis) == Double.doubleToLongBits(other);
	}

	/**
	 * sollten beide Objecte null sein, wird true zurückgegeben. Hiermit können auch
	 * Collections geprüft werden.
	 */
	static public boolean areEqual(Object aThis, Object other) {
		return aThis == null ? other == null : aThis.equals(other);
	}

}
