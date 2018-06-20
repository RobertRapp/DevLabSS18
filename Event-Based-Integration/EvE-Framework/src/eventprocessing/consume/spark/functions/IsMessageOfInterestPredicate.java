package eventprocessing.consume.spark.functions;

import java.io.Serializable;
import java.util.function.Predicate;

import org.apache.spark.api.java.function.Function;

/**
 * Dient der Überprüfung, ob eine Nachricht für ein <code>AbstractInterestProfile</code>
 * von interesse ist oder nicht. Findet Anwendung in der Klasse
 * <code>IsMessageOfInterest</code>
 * 
 * @author IngoT
 *
 */
public class IsMessageOfInterestPredicate implements Serializable {

	private static final long serialVersionUID = 3122024873734861784L;

	/**
	 * Das Predicate wird ausgeführt und prüft die Nachricht ob diese von Interesse
	 * ist.
	 * 
	 * @param message,
	 *            die vom DStream entnommen wurde @return, True wenn die Nachricht
	 *            relevant ist False wenn die Nachricht unwichtig ist.
	 */
	public Predicate<Function<String, Boolean>> isMessageOfInterest(String message) {
		return p -> {
			// call führt die Überprüfung durch.
			try {
				return p.call(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		};
	}
}
