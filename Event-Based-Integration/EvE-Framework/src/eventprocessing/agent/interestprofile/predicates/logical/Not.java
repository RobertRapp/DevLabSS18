package eventprocessing.agent.interestprofile.predicates.logical;

import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;
import eventprocessing.utils.model.ModelUtils;

/**
 * Die Klasse repräsentiert den Boolescher Operator NOT. Er kehrt das Ergebnis
 * eines <code>AbstractPredicate</code> um.
 * 
 * @author IngoT
 *
 */
public final class Not extends LogicalPredicate {

	private static final long serialVersionUID = -7364739918645806745L;
	// Predicate welches das Ergebnis negiert werden soll.
	private AbstractPredicate predicate = null;

	public Not() {
		
	}
	
	/**
	 * Es muss genau ein Prädikat vorhanden sein, daher wird der default-Konstruktor
	 * überschrieben.
	 * 
	 * @param predicate
	 *            das Prädikat, dessen Ergebnis negiert werden soll.
	 * @throws NullPredicateException
	 *             ist das Prädikat null wird die Instanz nicht erzeugt.
	 */
	public Not(AbstractPredicate predicate) throws NullPredicateException {
		if (predicate == null)
			throw new NullPredicateException("the committed predicate is null");
		this.predicate = predicate;

	}

	/**
	 * Gibt das Predicate zurück
	 * 
	 * @return predicate
	 */
	public AbstractPredicate getPredicate() {
		return this.predicate;
	}
	
	/**
	 * Kehrt das Ergebnis der Prüfung um. Wenn ein Prädikat bei der Überprüfung den
	 * Wert "true" zurückliefert, wird das Ergebnis auf "false" umgewandelt und
	 * umgekerht.
	 * 
	 * @param message,
	 *            Nachricht die überprüft wird vom Prädikat
	 * @return boolean, Ergebnis der Auswertung
	 */
	@Override
	public boolean test(String message) {
		// Das Ergebnis eines Predicates wird negiert.
		return (predicate.test(message)) ? false : true;
	}
	
	/**
	 * Alle Felder die für hashCode() und equals(Object) relevant sind.
	 * 
	 * @return Liste mit allen relevanten Feldern der Klasse
	 */
	private Object[] getSignificantFields() {
		return new Object[] { this.getPredicate() };
	}
	
	@Override
	public int hashCode() {
		return ModelUtils.hashCodeFor(getSignificantFields());
	}

	@Override
	public boolean equals(Object obj) {
		Boolean result = ModelUtils.quickEquals(this, obj);
		if (result == null) {
			Not that = (Not) obj;
			result = ModelUtils.equalsFor(this.getSignificantFields(), that.getSignificantFields());
		}
		return result;
	}

	@Override
	public String toString() {
		return ModelUtils.toStringFor(this);
	}
	
}
