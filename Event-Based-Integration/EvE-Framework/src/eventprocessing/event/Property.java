package eventprocessing.event;

import java.io.Serializable;

import eventprocessing.utils.TextUtils;
import eventprocessing.utils.model.ModelUtils;

/**
 * Eine Eigenschaft bilden Informationen ab, die für das Event relevant sind und
 * nicht in die vorhandenen Attribute der <code>AbstractEvent</code> passt.
 * 
 * @author IngoT
 *
 */
public final class Property<T> implements Serializable {

	private static final long serialVersionUID = -5223936411633570389L;
	/*
	 * Jede Eigenschaft besitzt einen Wert, also eine Bezeichnung, sowie einen
	 * dazugehörigen Schlüssel
	 */
	private String key = null;
	// Der Value kann jeden Typen annehmen
	private T value = null;

	/**
	 * Setzen von default-Werten.
	 */
	public Property() {
		this("empty", null);
	}

	/**
	 * Erzeugt eine Eigenschaft mit einer Bezeichnung, aber ohne dazugehörigen Wert
	 * 
	 * @param key, Name der Eigenschaft
	 */
	public Property(String key) {
		this(key, null);
	}

	/**
	 * Erzeugt eine Eigenschaft mit einem Key-Value-Paar
	 * 
	 * @param key, Name der Eigenschaft
	 * @param value, dazugehörige Ausprägung
	 */
	public Property(String key, T value) {
		// Prüfung ob die Eigenschaft nicht leer oder null ist
		if (!TextUtils.isNullOrEmpty(key)) {
			// Eigenschaft wird gesetzt
			this.key = key;
		// Sonst wird ein default-Wert gesetzt
		} else {
			this.key = "empty";
		}
		// setzen der Ausprägung, null ist erlaubt
		this.value = value;
	}

	/**
	 * Gibt die Bezeichnung der Eigenschaft zurück 
	 * 
	 * @return key, Bezeichnung der Eigenschaft
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Setzt die Bezeichnung einer Eigenschaft.
	 * 
	 * @param key, Bezeichnung der Eigenschaft
	 */
	public void setKey(String key) {
		// Prüfung ob nicht null oder leer ist
		if (!TextUtils.isNullOrEmpty(key)) {
			this.key = key;
		} else {
			this.key = "empty";
		}
	}

	/**
	 * Gibt die Ausprägung zurück
	 * 
	 * @return T, Ausprägung der Eigenschaft
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Setzt die Ausprägung einer Eigenschaft
	 * 
	 * @param value, Ausprägung der Eigenschaft
	 */
	public void setValue(T value) {
		// Keine Prüfung auf null, da null erlaubt ist
		this.value = value;
	}

	private Object[] getSignificantFields() {
		return new Object[] { this.getKey(), this.getValue() };
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
			Property<T> that = (Property<T>) obj;
			result = ModelUtils.equalsFor(this.getSignificantFields(), that.getSignificantFields());
		}
		return result;
	}

	@Override
	public String toString() {
		return ModelUtils.toStringFor(this);
	}
}
