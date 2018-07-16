package eventprocessing.agent.interestprofile.predicates.logical;

import java.util.ArrayList;
import java.util.List;

import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;
import eventprocessing.utils.model.ModelUtils;

/**
 * Die Klasse repräsentiert den Boolescher Operator OR. Alle
 * <code>AbstractPredicate</code> werden mit einem logischen "or" verknüpft und
 * ausgewertet.
 * 
 * @author IngoT
 *
 */
public final class Or extends LogicalPredicate {

	private static final long serialVersionUID = 6355457207712336729L;
	// Liste mit allen Prädikaten die geprüft werden
	private List<AbstractPredicate> predicates = new ArrayList<AbstractPredicate>();
	// Notwendig für die Überprüfung der Prädikaten in der Auswertung.
	private final StreamPredicate streamPredicate = new StreamPredicate();

	public Or() {

	}

	/**
	 * Für die Verarbeitung sind mindestens zwei <code>AbstractPredicate</code>
	 * notwendig. Alle weiteren Prädikate sind optional.
	 * 
	 * @param firstPredicate
	 *            das erste Prädikat, welches geprüft wird
	 * @param secondPredicate
	 *            das zweite Prädikat, welches geprüft wird morePredicates, alle
	 *            weiteren Prädikate, die Optional angegeben werden.
	 * @param morePredicates
	 *            es kann eine beliebige Verkettung von Prädikaten angegeben werden.
	 * @throws NullPredicateException
	 *             ist eins der ersten beiden Prädikate null wird eine Exception
	 *             geworfen und keine Instanz erzeugt
	 */
	public Or(AbstractPredicate firstPredicate, AbstractPredicate secondPredicate, AbstractPredicate... morePredicates)
			throws NullPredicateException {
		// Prüfung ob die Prädikate null sind
		if (firstPredicate == null || secondPredicate == null)
			throw new NullPredicateException("one or both of the predicates are null");
		// die beiden Prädikate werden der Liste hinzugefügt
		this.predicates.add(firstPredicate);
		this.predicates.add(secondPredicate);

		/*
		 * Alle weiteren Prädikate werden der Liste hinzugefügt, null wird nicht
		 * hinzugefügt und ignoriert
		 */
		for (AbstractPredicate predicate : morePredicates) {
			if (predicate != null) {
				this.predicates.add(predicate);
			}
		}
	}

	/**
	 * Gibt die Liste aller Predicates zurück
	 * 
	 * @return List, mit allen Predicates
	 */
	public List<AbstractPredicate> getPredicates() {
		return this.predicates;
	}

	/**
	 * 
	 * Die Nachricht durchläuft alle Prädikate und sobald ein Prädikat das Ergebnis
	 * "true" zurückliefert, werden die weiteren Prädikate nicht mehr ausgewertet.
	 * 
	 * @param message,
	 *            Nachricht die überprüft wird vom Prädikat
	 * @return boolean, Ergebnis der Auswertung
	 */
	@Override
	public boolean test(String message) {
		/*
		 * anyMatch bricht die Überprüfung des Streams ab, sobald ein "true" gefunden
		 * wurde.
		 */
		return predicates.stream().anyMatch(streamPredicate.Check(message));
	}

	/**
	 * Alle Felder die für hashCode() und equals(Object) relevant sind.
	 * 
	 * @return Liste mit allen relevanten Feldern der Klasse
	 */
	private Object[] getSignificantFields() {
		return new Object[] { this.getPredicates() };
	}

	@Override
	public int hashCode() {
		return ModelUtils.hashCodeFor(getSignificantFields());
	}

	@Override
	public boolean equals(Object obj) {
		Boolean result = ModelUtils.quickEquals(this, obj);
		if (result == null) {
			Or that = (Or) obj;
			result = ModelUtils.equalsFor(this.getSignificantFields(), that.getSignificantFields());
		}
		return result;
	}

	@Override
	public String toString() {
		return ModelUtils.toStringFor(this);
	}
}
