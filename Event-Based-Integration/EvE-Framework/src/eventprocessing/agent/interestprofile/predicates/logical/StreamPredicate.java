package eventprocessing.agent.interestprofile.predicates.logical;

import java.io.Serializable;
import java.util.function.Predicate;

import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;

/**
 * Übergibt die Nachricht an einen Subtype von <code>AbstractPredicate</code>
 * und wertet die Nachricht aus und gibt das Ergebnis in Form eines Boolean
 * zurück.
 * 
 * @author IngoT
 *
 */
public final class StreamPredicate implements Serializable {

	private static final long serialVersionUID = 7331774799195294415L;

	/**
	 * Die Nachricht wird an das Prädikat übergeben und ausgewertet.
	 * 
	 * @param message,
	 *            die vom DStream entnommen wurde @return, true wenn die Nachricht
	 *            der Prädikatenlogik entspricht und false, wenn nicht.
	 * @return true, wenn die Nachricht dem Pattern entspricht und false, wenn
	 *         nicht.
	 */
	public Predicate<? super AbstractPredicate> Check(String message) {
		return p -> {
			// call führt die Überprüfung durch.
			try {
				return p.test(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		};
	}
}
