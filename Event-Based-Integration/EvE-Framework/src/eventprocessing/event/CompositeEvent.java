package eventprocessing.event;

import java.util.ArrayList;
import java.util.List;

import eventprocessing.utils.model.ModelUtils;

/**
 * In einem CompositeEvent werden die einzelnen Events des selben Typs in
 * aggregierter Form vorgehalten werden.
 * 
 * 
 * @author IngoT
 *
 * @param <T>
 *            Der Subtype des Events
 */
public class CompositeEvent<T extends AbstractEvent> extends AbstractEvent {

	private static final long serialVersionUID = -2107701039791695195L;
	// Liste mit allen Events
	private List<T> events = new ArrayList<T>();

	/**
	 * fügt der Liste ein Event hinzu
	 * 
	 * @param event,
	 *            welches der Liste hinzugefügt werden soll
	 */
	public void add(T event) {
		// Wenn das Event nicht null ist
		if (event != null) {
			// wird es der Liste hinzugefügt
			this.events.add(event);
		}
	}

	/**
	 * Ruft alle Events ab
	 * 
	 * @return List<T> mit allen Events
	 */
	public List<T> getEvents() {
		return this.events;
	}

	/**
	 * entfernt das entsprechende Event aus der Liste
	 * 
	 * @param event,
	 *            das Event, welches entfernt werden soll
	 */
	public void remove(T event) {
		if (event != null) {
			this.events.remove(event);
		}
	}

	/**
	 * entfernt alle Events aus der Liste
	 */
	public void removeAll() {
		this.events.clear();
	}

	private Object[] getSignificantFields() {
		return new Object[] { this.getId(), this.getCreationDate(), this.getProperties(), this.getEvents() };
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
			CompositeEvent<T> that = (CompositeEvent<T>) obj;
			result = ModelUtils.equalsFor(this.getSignificantFields(), that.getSignificantFields());
		}
		return result;
	}

	@Override
	public String toString() {
		return ModelUtils.toStringFor(this);
	}
}
