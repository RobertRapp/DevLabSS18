package eventprocessing.agent.dispatch.filters;

import org.apache.spark.api.java.function.Function;

import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;
import eventprocessing.utils.model.ModelUtils;

/**
 * Diese Funktionen werden für die Filterung von JavaDStreams benötigt. Für die
 * Filterung wird auf ein Predicate zurückgegriffen, welches von den
 * <code>AbstractInterestProfile</code> erzeugt werden und über den
 * <code>FilterMapper</code> in diese Function übersetzt werden. Alle Functions
 * finden sich in der <code>FilterQueue</code> wieder.
 * 
 * @author IngoT
 *
 */
public final class FilterFunction implements Function<String, Boolean> {

	private static final long serialVersionUID = -4304097003519456364L;
	// Predicate, welches für die Filterung verwendet wird.
	private AbstractPredicate predicate = null;

	/**
	 * fügt der Function ein Predicate hinzu, welches vom
	 * <code>AbstractInterestProfile</code> übermittelt wurde. Mit diesem Predicate werden
	 * eingehende Nachrichten gefiltert.
	 * 
	 * @param predicateFromInterestProfile
	 */
	public FilterFunction(AbstractPredicate predicate) {
		if (predicate != null) {
			this.predicate = predicate;
		}
	}

	/**
	 * Die call-Methode wird von der Function automatisch ausgeführt und ruft das
	 * Predicate auf, um die Nachricht zu überprüfen, ob diese von Interesse ist
	 * oder nicht.
	 */
	@Override
	public Boolean call(String message) {
		// Das Ergebniss wird zurückgegeben
		return predicate.test(message);
	}

	/**
	 * Alle Felder die für hashCode() und equals(Object) relevant sind.
	 * 
	 * @return Liste mit allen relevanten Feldern der Klasse
	 */
	private Object[] getSignificantFields() {
		return new Object[] { this.predicate };
	}
	
	@Override
	public int hashCode() {
		return ModelUtils.hashCodeFor(getSignificantFields());
	}

	@Override
	public boolean equals(Object obj) {
		Boolean result = ModelUtils.quickEquals(this, obj);
		if (result == null) {
			FilterFunction that = (FilterFunction) obj;
			result = ModelUtils.equalsFor(this.getSignificantFields(), that.getSignificantFields());
		}
		return result;
	}

	@Override
	public String toString() {
		return ModelUtils.toStringFor(this);
	}
}
