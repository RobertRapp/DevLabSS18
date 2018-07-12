package eventprocessing.agent.dispatch.filters;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.spark.api.java.function.Function;

import eventprocessing.utils.model.ModelUtils;

/**
 * Die FilterQueue beinhaltet alle <code>FilterFunction</code> die zu einem
 * <code>AbstractInterestProfile</code> gehören
 * 
 * Sie wird für jedes <code>AbstractInterestProfile</code> erzeugt und wird im
 * <code>Dispatcher</code> zusammengeführt. Im Streaming-Prozess wird sie in der
 * Function <code>IsMessageOfInterest</code> angewandt, um uninteressante
 * Nachrichten auszufiltern.
 * 
 * @author IngoT
 *
 */
public final class FilterQueue implements Serializable {

	private static final long serialVersionUID = -1428541641893172099L;
	/*
	 * Alle Filterfunktionen werden in einer Queue vorgehalten.
	 * Die Nachricht durchläuft die gesamte Queue, um zu überprüfen,
	 * ob die Nachricht von Relevanz ist.
	 */
	private Queue<Function<String, Boolean>> filterFunctions = new LinkedList<Function<String, Boolean>>();

	/**
	 * hinzufügen eines Filters in die Queue
	 * 
	 * @param filter, welches der FilterQueue hinzugefügt wird.
	 */
	public void add(FilterFunction filter) {
		filterFunctions.add(filter);
	}

	/**
	 * Gibt die Queue mit allen FilterFunctions zurück
	 * 
	 * @return filterFunctions, Queue mit allen FilterFunctions.
	 */
	public Queue<Function<String, Boolean>> getFilters() {
		return this.filterFunctions;
	}

	/**
	 * Alle Felder die für hashCode() und equals(Object) relevant sind.
	 * 
	 * @return Liste mit allen relevanten Feldern der Klasse
	 */
	private Object[] getSignificantFields() {
		return new Object[] { this.getFilters() };
	}
	
	@Override
	public int hashCode() {
		return ModelUtils.hashCodeFor(getSignificantFields());
	}

	@Override
	public boolean equals(Object obj) {
		Boolean result = ModelUtils.quickEquals(this, obj);
		if (result == null) {
			FilterQueue that = (FilterQueue) obj;
			result = ModelUtils.equalsFor(this.getSignificantFields(), that.getSignificantFields());
		}
		return result;
	}

	@Override
	public String toString() {
		return ModelUtils.toStringFor(this);
	}

}
