package eventprocessing.agent.interestprofile.predicates.statement;

import java.io.Serializable;

import eventprocessing.utils.TextUtils;
import eventprocessing.utils.model.ModelUtils;

/**
 * Die FilterAttribute stellen Key-Value-Paare dar, mit denen die Nachrichten
 * aus dem Stream geprüft werden. Über das Generic kann das FilterAttribute
 * verschiedene Typen von Values annehmen.
 * 
 * @author IngoT
 *
 */
public final class FilterAttribute<T> implements Serializable {

	private static final long serialVersionUID = -4899443646580575200L;
	// Die Eigenschaft
	private String property = null;
	// Der Wert, der zu der Eigenschaft gehört
	private T value = null;

	/**
	 * Erzeugt ein Filterattribute mit einer Eigenschaft
	 * 
	 * @param property,
	 *            Eigenschaft die gesetzt wird
	 */
	public FilterAttribute(String property) {
		this(property, null);
	}

	/**
	 * Erzeugt ein FilterAttribute mit einer Eigenschaft und dazugehörigen Wert
	 * 
	 * @param property,
	 *            Eigenschaft die gesetzt wird
	 * @param value,
	 *            der zugehörige Wert für die Eigenschaft
	 */
	public FilterAttribute(String property, T value) {
		if (!TextUtils.isNullOrEmpty(property)) {
			this.property = property;
		} else {
			this.property = "empty";
		}
		this.value = value;
	}

	/**
	 * Gibt die Eigenschaft zurück
	 * 
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * Gibt den Wert der Eigenschaft zurück
	 * 
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Alle Felder die für hashCode() und equals(Object) relevant sind.
	 * 
	 * @return Liste mit allen relevanten Feldern der Klasse
	 */
	private Object[] getSignificantFields() {
		return new Object[] { this.getValue(), this.getProperty() };
	}

	@Override
	public int hashCode() {
		return ModelUtils.hashCodeFor(getSignificantFields());
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		Boolean result = ModelUtils.quickEquals(this, obj);
		if (result == null) {
			FilterAttribute<T> that = (FilterAttribute<T>) obj;
			result = ModelUtils.equalsFor(this.getSignificantFields(), that.getSignificantFields());
		}
		return result;
	}

	@Override
	public String toString() {
		return ModelUtils.toStringFor(this);
	}

}
