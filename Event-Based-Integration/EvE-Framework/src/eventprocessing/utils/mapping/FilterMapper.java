package eventprocessing.utils.mapping;

import java.io.Serializable;

import eventprocessing.agent.dispatch.filters.FilterFunction;
import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;

/**
 * Dieser Mapper übersetzt die Predicates, welche von den
 * <code>AbstractInterestProfile</code> kommen in <code>FilterFunction</code>.
 * 
 * Die erzeugten FilterFunctions werden für die <code>FilterQueue</code>
 * benötigt. 
 * 
 * @author IngoT
 *
 */
public final class FilterMapper implements Serializable {

	private static final long serialVersionUID = 6746326822785470673L;

	/**
	 * Es wird eine neue Function erzeugt und wird mit dem Predicate verbunden. Die
	 * neue Function wird zurückgegeben.
	 * 
	 * @param predicate,
	 *            vom InterestProfile, das in eine Filterfunktion übersetzt wird.
	 * @return function, die FilterFunction mit dem AbstractPredicate vom
	 *         InterestProfile
	 */
	public FilterFunction toFilterFunction(AbstractPredicate predicate) {
		/**
		 * Wenn das Predicate nicht null ist, wird eine neue FilterFunction erzeugt und
		 * übergeben, ansonsten wird null zurückgeliefert.
		 */
		return (predicate != null) ? new FilterFunction(predicate) : null;
	}
}
