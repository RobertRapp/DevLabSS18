package eventprocessing.agent.interestprofile.predicates.statement;

import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;
import eventprocessing.utils.model.ModelUtils;

/**
 * Statements überprüfen die eingehende Nachricht auf ein bestimmtes Muster.
 * Dazu wird ein <code>FilterAttribute</code> gesetzt, welches auf die
 * eingehende Nachricht angewandt wird.
 * 
 * @author IngoT
 *
 */
public abstract class StatementPredicate extends AbstractPredicate {

	private static final long serialVersionUID = 877832720731205730L;

	// Beinhaltet das Muster für die Prüfung der Nachrichten.
	protected String pattern = null;

	/*
	 * trägt die Informationen in sich, nach welchen Eigenschaften bzw. Werten
	 * ausschau gehalten werden.
	 */
	private FilterAttribute<?> filterAttribute = null;

	public StatementPredicate() {

	}

	/**
	 * Wenn nur nach der Eigenschaft gesucht wird.
	 * 
	 * @param property,
	 *            die Eigenschaft nach der gesucht wird.
	 */
	public StatementPredicate(String property) {
		this.filterAttribute = new FilterAttribute<String>(property);
	}

	/**
	 * Wenn nach einer Eigenschaft und dem dazugehörigen Wert gesucht werden soll.
	 * 
	 * @param property,
	 *            Eigenschaft für die Suche
	 * @param value,
	 *            Wert der Eigenschaft die gesucht wird.
	 */
	public StatementPredicate(String property, String value) {
		this.filterAttribute = new FilterAttribute<String>(property, value);
	}

	public StatementPredicate(String property, Integer value) {
		this.filterAttribute = new FilterAttribute<Integer>(property, value);
	}

	public StatementPredicate(String property, Boolean value) {
		this.filterAttribute = new FilterAttribute<Boolean>(property, value);
	}

	/**
	 * Ruft das FilterAttribute ab.
	 * 
	 * @param <T>
	 *            Art des Filterattributes.
	 * 
	 * @return das Filterattribute
	 */
	public <T> FilterAttribute<?> getFilterAttribute() {
		return this.filterAttribute;
	}

	/**
	 * Setzt das Suchmuster für die Nachrichten.
	 * 
	 * @param pattern,
	 *            beinhaltet das Suchmuster in Stringform
	 */
	protected void setPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * Ruft das Suchmuster für die Nachrichten ab.
	 * 
	 * @return pattern, das Suchmuster
	 */
	protected String getPattern() {
		return this.pattern;
	}

	/**
	 * Prüft ob die Nachricht dem Muster entspricht.
	 * 
	 * @param message
	 *            die geprüft wird gegen das Pattern
	 * @return true, wenn das Pattern in der Nachricht vorkommt, false, wenn das
	 *         Pattern nicht vorkommt.
	 */
	@Override
	public boolean test(String message) {
		/*
		 * Prüfung ob die Nachricht oder die Eigenschaften an denen Interesse besteht
		 * null ist, sollte dies der Fall sein, wird direkt false zurückgegeben
		 */
		if (message == null) {
			return false;
		}
		// Es wird gerpüft, ob die Eigenschaft in der Nachricht vorhanden ist
		return (message.contains(pattern)) ? true : false;
	}

	/**
	 * Alle Felder die für hashCode() und equals(Object) relevant sind.
	 * 
	 * @return Liste mit allen relevanten Feldern der Klasse
	 */
	private Object[] getSignificantFields() {
		return new Object[] { this.getFilterAttribute(), this.getPattern() };
	}

	@Override
	public int hashCode() {
		return ModelUtils.hashCodeFor(getSignificantFields());
	}

	@Override
	public boolean equals(Object obj) {
		Boolean result = ModelUtils.quickEquals(this, obj);
		if (result == null) {
			StatementPredicate that = (StatementPredicate) obj;
			result = ModelUtils.equalsFor(this.getSignificantFields(), that.getSignificantFields());
		}
		return result;
	}

	@Override
	public String toString() {
		return ModelUtils.toStringFor(this);
	}

}
