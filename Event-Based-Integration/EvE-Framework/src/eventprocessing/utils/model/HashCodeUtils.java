package eventprocessing.utils.model;

import java.lang.reflect.Array;

/**
 * Helferklasse für die Berechnung des HashCodes eines Objekts.
 * 
 * @author IngoT
 *
 */
public final class HashCodeUtils {
	
	private HashCodeUtils() {
		
	}
	// Ein Startwert für die Berechnung des Hashwertes. Sollte nicht den Wert 0 haben. 
	public static final int SEED = 23;
	// Basiswert für die Berechnung der Werte für die Member eines Objekts
	private static final int FODD_PRIME_NUMBER = 37;
	
	// Für booleans
	public static int hash(int aSeed, boolean aBoolean) {
		return firstTerm(aSeed) + (aBoolean ? 1 : 0);
	}

	// Für chars
	public static int hash(int aSeed, char aChar) {
		return firstTerm(aSeed) + (int) aChar;
	}

	// Für int, byte, short
	public static int hash(int aSeed, int aInt) {
		return firstTerm(aSeed) + aInt;
	}

	// Für longs
	public static int hash(int aSeed, long aLong) {
		return firstTerm(aSeed) + (int) (aLong ^ (aLong >>> 32));
	}

	// Für floats
	public static int hash(int aSeed, float aFloat) {
		return hash(aSeed, Float.floatToIntBits(aFloat));
	}

	// Für doubles
	public static int hash(int aSeed, double aDouble) {
		return hash(aSeed, Double.doubleToLongBits(aDouble));
	}

	/**
	 * <tt>aObject</tt> Ein Objekt kann null sein oder auch ein Feld.
	 */
	public static int hash(int aSeed, Object aObject) {
		int result = aSeed;
		if (aObject == null) {
			result = hash(result, 0);
		} else if (!isArray(aObject)) {
			result = hash(result, aObject.hashCode());
		} else {
			int length = Array.getLength(aObject);
			for (int idx = 0; idx < length; ++idx) {
				Object item = Array.get(aObject, idx);
				// Wenn ein Eintrag auf das Array selber verweist, wird die Endlosschleife vermieden
				if (!(item == aObject))
					// rekursiver Aufruf
					result = hash(result, item);
			}
		}
		return result;
	}

	// private Member
	private static int firstTerm(int aSeed) {
		return FODD_PRIME_NUMBER * aSeed;
	}

	// Handelt es sich bei dem Objekt um ein Array
	private static boolean isArray(Object aObject) {
		return aObject.getClass().isArray();
	}
}
