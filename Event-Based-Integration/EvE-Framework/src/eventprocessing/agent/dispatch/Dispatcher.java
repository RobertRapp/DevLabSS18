package eventprocessing.agent.dispatch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.dispatch.filters.FilterQueue;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.utils.CollectionUtils;
import eventprocessing.utils.SystemUtils;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.mapping.FilterMapper;
import eventprocessing.utils.model.EqualsUtils;
import eventprocessing.utils.model.ModelUtils;

/**
 * Am Dispatcher registrieren sich alle <code>AbstractInterestProfile</code>.
 * Die Predicates der InteressenProfile werden über einen Mapper in Functions
 * umgewandelt. Mit der Paarung von InterestProfile und FilterQueue überprüft
 * der Dispatcher den DStream, welche Nachrichten für welches InteressenProfil
 * relevant sind und übermittelt diese, wenn sie von Interesse sind.
 * 
 * @author IngoT
 *
 */
public final class Dispatcher implements Serializable {

	private static final long serialVersionUID = -5217243321091042432L;
	private static Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class.getName());
	// Wird für das Mapping der Predicates in Functions verwendet
	private transient final FilterMapper filterMapper = new FilterMapper();
	// Liste mit allen Paaren aus InterestProfiles und FilterQueues
	private List<IPFilterQPair> ipFilterQueuePairs = new ArrayList<IPFilterQPair>();

	/**
	 * stellt das Bindeglied zwischen der <code>FilterQueue</code> und dem
	 * dazugehörigen <code>AbstractInterestProfile</code> dar. Für jedes
	 * InteressenProfil wird ein neues Paar erzeugt.
	 * 
	 * @author IngoT
	 *
	 *         Bemerkung:
	 * 
	 *         Bisher wird nicht darauf geachtet, ob verschiedene InterestProfiles
	 *         die selben Predicates besitzen, daher sind Doppelungen möglich. Durch
	 *         die Optimierung können Performancevorteile gewonnen werden.
	 */
	class IPFilterQPair implements Serializable {

		private static final long serialVersionUID = -6407101718482385621L;

		/*
		 * Das InteressenProfil, welches mit einer FilterQueue in Verbindung steht.
		 */
		private AbstractInterestProfile interestProfile = null;
		/*
		 * Die FilterQueue, die zu einem InteressenProfil gehört.
		 */
		private FilterQueue filterQueue = null;

		/**
		 * Ein Interessenprofil sowie eine Filterquee werden beim Instanziierung
		 * benötigt.
		 * 
		 * @param interestProfile,
		 *            welches mit der FilterQueue gepaart wird.
		 * @param filterQueue,
		 *            welches mit dem InterestProfile gepaart wird.
		 */
		public IPFilterQPair(AbstractInterestProfile interestProfile, FilterQueue filterQueue) {
			this.interestProfile = interestProfile;
			this.filterQueue = filterQueue;
		}

		/**
		 * Gibt das InterestProfile zurück
		 * 
		 * @return interestProfile
		 */
		public AbstractInterestProfile getInterestProfile() {
			return this.interestProfile;
		}

		/**
		 * Gibt die FilterQueue zurück
		 * 
		 * @return filterQueue
		 */
		public FilterQueue getFilterQueue() {
			return this.filterQueue;
		}

		private Object[] getSignificantFields() {
			return new Object[] { this.getInterestProfile(), this.getFilterQueue() };
		}

		@Override
		public int hashCode() {
			return ModelUtils.hashCodeFor(getSignificantFields());
		}

		@Override
		public boolean equals(Object obj) {
			Boolean result = ModelUtils.quickEquals(this, obj);
			if (result == null) {
				IPFilterQPair that = (IPFilterQPair) obj;
				result = ModelUtils.equalsFor(this.getSignificantFields(), that.getSignificantFields());
			}
			return result;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			Formatter formatter = new Formatter(builder);
			formatter.format("%s Object {%s", this.getClass().getName(), SystemUtils.getLineSeparator());
			formatter.format("%s%s", this.getInterestProfile().toString(), SystemUtils.getLineSeparator());
			formatter.format("%s%s", this.getFilterQueue().toString(), SystemUtils.getLineSeparator());
			formatter.format("}");

			formatter.close();
			return builder.toString();
		}

	}

	/**
	 * Sucht nach dem passenden Gegenstück für die übergebene FilterQueue
	 *
	 * @param queue,
	 *            zu dem das passende InterestProfile gesucht werden soll
	 * @return das dazugehörige InterestProfile
	 */
	public AbstractInterestProfile getInterestProfileOf(FilterQueue queue) {
		LOGGER.log(Level.FINE, () -> String.format("Looking for the InterestProfile of the FilterQueue: %s%s",
				SystemUtils.getLineSeparator(), queue));
		try {
			/*
			 * Es wird das erste InterestProfile-Element zurückgegeben, welches der
			 * FilterQueue entspricht.
			 */
			AbstractInterestProfile interestProfile = ipFilterQueuePairs.stream()
					.filter(pair -> EqualsUtils.areEqual(pair.getFilterQueue(), queue)).findFirst().get()
					.getInterestProfile();
			LOGGER.log(Level.FINE, () -> String.format("InterestProfile was found: %s%s",
					SystemUtils.getLineSeparator(), interestProfile));
			// Rückgabe der FilterQueue
			return interestProfile;
			/*
			 * Wenn keine passende FilterQueue gefunden wurde, wirft findFirst() eine
			 * Exception
			 */
		} catch (NoSuchElementException e) {
			LOGGER.log(Level.WARNING,
					() -> String.format("no matching InterestProfile was found. Committed FilterQueue: %s%s",
							SystemUtils.getLineSeparator(), queue));
			return null;
		}
	}

	/**
	 * Sucht nach dem passenden Gegenstück für die übergebene InterestProfile
	 *
	 * @param interestProfile,
	 *            zu dem die passende FilterQueue gesucht werden soll
	 * @return die dazugehörige FilterQueue
	 */
	public FilterQueue getFilterQueueOf(AbstractInterestProfile interestProfile) {
		LOGGER.log(Level.FINE, () -> String.format("Looking for FilterQueue of: %s%s", interestProfile));
		try {
			/*
			 * Es wird das erste FilterQueue-Element zurückgegeben, welches dem
			 * InterestProfile entspricht.
			 */
			FilterQueue filterQueue = ipFilterQueuePairs.stream()
					.filter(pair -> EqualsUtils.areEqual(pair.getInterestProfile(), interestProfile)).findFirst().get()
					.getFilterQueue();
			LOGGER.log(Level.FINE,
					() -> String.format("FilterQueue was found: %s%s", SystemUtils.getLineSeparator(), filterQueue));
			// Rückgabe der FilterQueue
			return filterQueue;
			/*
			 * Wenn keine passende FilterQueue gefunden wurde, wirft findFirst() eine
			 * Exception
			 */
		} catch (NoSuchElementException e) {
			LOGGER.log(Level.WARNING,
					() -> String.format("no matching FilterQueue was found. Committed InterestProfile: %s%s",
							SystemUtils.getLineSeparator(), interestProfile));
			return null;
		}
	}

	/**
	 * fügt der Liste ein weiteres InterestProfile hinzu. Bei der Übergabe werden
	 * aus den Predicates des InterestProfiles über den FilterMapper die
	 * FilterFunctions erzeugt. Diese FilterFunctions werden der FilterQueue
	 * hinzugefügt. Abschließend wird das InterestProfile mit der FilterQueue über
	 * die Innereklasse zusammengeführt und der Liste angefügt.
	 * 
	 * @param interestProfile,
	 *            das dem Dispatcher hinzugefügt werden soll.
	 * @throws NoValidInterestProfileException
	 *             , Sollte null übergeben worden sein, wird eine Exception geworfen
	 */
	public void add(AbstractInterestProfile interestProfile) throws NoValidInterestProfileException {
		if (interestProfile != null && !CollectionUtils.isEmpty(interestProfile.getPredicates())) {
			LOGGER.log(Level.FINE, () -> String.format("committed InterestProfile: %s", interestProfile));
			// Erzeugt eine leere FilterQueue
			FilterQueue filterQueue = new FilterQueue();
			// Für jedes Predicate im InterestProfile wird ein mapping durchgeführt
			interestProfile.getPredicates().stream().forEach(predicate -> {
				// Die Filterfunktion wird der FilterQueue zugefügt.
				LOGGER.log(Level.FINE, () -> String.format("add %s to the FilterQueue", predicate));
				filterQueue.add(filterMapper.toFilterFunction(predicate));
			});
			// Es wird ein neues Paar aus dem InterestProfile und FilterQueue erzeugt
			LOGGER.log(Level.FINE, "commit FilterQueue and InterestProfile to the Dispatcher.");
			IPFilterQPair ipFilterQPair = new IPFilterQPair(interestProfile, filterQueue);
			// Und der Liste hinzugefügt.
			ipFilterQueuePairs.add(ipFilterQPair);
		} else {
			throw new NoValidInterestProfileException(
					"no valid interest profile passed. The committed InterestProfile is null or doesn't contain any predicates");
		}
	}

	/**
	 * Rückgabe der gesamten Liste mit allen Paaren.
	 * 
	 * @return die Liste mit allen Paarungen von InterestProfile und FilterQueue
	 */
	public List<IPFilterQPair> getAllIPFilterQPairs() {
		return this.ipFilterQueuePairs;
	}

	/**
	 * Alle Felder die für hashCode() und equals(Object) relevant sind.
	 * 
	 * @return Liste mit allen relevanten Feldern der Klasse
	 */
	private Object[] getSignificantFields() {
		return new Object[] { this.getAllIPFilterQPairs() };
	}

	@Override
	public int hashCode() {
		return ModelUtils.hashCodeFor(getSignificantFields());
	}

	@Override
	public boolean equals(Object obj) {
		Boolean result = ModelUtils.quickEquals(this, obj);
		if (result == null) {
			Dispatcher that = (Dispatcher) obj;
			result = ModelUtils.equalsFor(this.getSignificantFields(), that.getSignificantFields());
		}
		return result;
	}

	@Override
	public String toString() {
		return ModelUtils.toStringFor(this);
	}

}
