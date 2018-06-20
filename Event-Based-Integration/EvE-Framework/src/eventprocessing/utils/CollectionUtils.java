package eventprocessing.utils;

import java.util.Collection;

/**
 * Hilfsklasse für die Verarbeitung von Collections
 * 
 * @author IngoT
 *
 */
public final class CollectionUtils {

	private CollectionUtils() {
		
	}
	/**
	 * Prüft ob die übergebene Collection null oder leer ist. Es wird nur geprüft,
	 * ob sich Elemente in der Collection befinden, nicht ob diese wiederum null
	 * oder leer sind.
	 * 
	 * @param obj,
	 *            die Collection die geprüft werden soll
	 * @return true, wenn die Collection null oder leer ist. false, wenn die
	 *         Collection weder null noch leer ist.
	 */
	public static boolean isEmpty(Collection<?> obj) {
		return obj == null || obj.isEmpty();
	}
}
