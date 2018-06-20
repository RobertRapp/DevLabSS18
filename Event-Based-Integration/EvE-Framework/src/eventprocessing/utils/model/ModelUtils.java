package eventprocessing.utils.model;

/**
 * Klasse dient Bereitstellung der Hilfsmethoden für die Bereitstellung der
 * toString(), hashCode() und equals() Methode.
 * 
 * ACHTUNG: die toStringFor(String) Methode kann nicht bei enum und Klassen
 * verwendet werden, die eine Referenz untereinander haben.
 * 
 * @author IngoT
 *
 */
public final class ModelUtils {

	private ModelUtils() {
	}

	/**
	 * berechnet den hashCode anhand der relevanten Felder der Klasse
	 * 
	 * @param aFields,
	 *            ein Object[] mit allen relevanten Feldern der Klasse
	 * 
	 * @return hashCode as int.
	 */
	public static final int hashCodeFor(Object... aFields) {
		int result = HashCodeUtils.SEED;
		for (Object field : aFields) {
			result = HashCodeUtils.hash(result, field);
		}
		return result;
	}

	/**
	 * Gibt eine String Repräsentation des Objektes zurück. ACHTUNG: kann nicht bei
	 * enums verwendet werden oder wenn Objekte eine Referenz untereinander
	 * besitzen.
	 * 
	 * @param aObject,
	 *            aus dem die Repräsentation erzeugt werden soll
	 * @return String, mit allen Informationen aus dem aObject
	 */
	public static String toStringFor(Object aObject) {
		return ToStringUtils.getText(aObject);
	}

	/**
	 * führt eine einfache Überprüfung zweier Objekte durch. Es wird die Identität
	 * sowie die Klassenzugehörigkeit geprüft
	 * 
	 * @param aThis
	 *            erstes Objekt
	 * @param aThat
	 *            zweites Objekt
	 * @return true, wenn sie gleich sind und false, wenn sie nicht gleich sind.
	 */
	public static Boolean quickEquals(Object aThis, Object aThat) {
		return EqualsUtils.quickEquals(aThis, aThat);
	}

	/**
	 * Hier findet eine genauere Überprüfung der Objekte statt die vergliechen
	 * werden sollen. Es findet eine Überprüfung der relevanten Felder durch.
	 * 
	 * @param aThisSignificantFields,
	 *            Felder des ersten Objekts
	 * @param aThatSignificantFields,
	 *            Felder des zweiten Objekts
	 * @return true, wenn sie gleich sind und false, wenn sie nicht gleich sind.
	 */
	public static boolean equalsFor(Object[] aThisSignificantFields, Object[] aThatSignificantFields) {
		// (varargs can be used for final arg only)
		if (aThisSignificantFields.length != aThatSignificantFields.length) {
			throw new IllegalArgumentException(
					"Array lengths do not match. 'This' length is " + aThisSignificantFields.length
							+ ", while 'That' length is " + aThatSignificantFields.length + ".");
		}

		boolean result = true;
		/*
		 * Alle Felder werden einzeln überprüft. Sollte ein Feld nicht übereinstimmen,
		 * wird false zurückgeliefert und die Schleife abgebrochen
		 */
		for (int idx = 0; idx < aThisSignificantFields.length; ++idx) {
			if (!EqualsUtils.areEqual(aThisSignificantFields[idx], aThatSignificantFields[idx])) {
				result = false;
				break;
			}
		}
		return result;
	}

}
