package eventprocessing.agent.interestprofile;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;
import eventprocessing.consume.kafka.runner.logging.state.CountReceiveState;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.SystemUtils;
import eventprocessing.utils.TimeUtils;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.ModelUtils;

/**
 * Die InterestProfiles repräsentieren das Interesse eines
 * <code>AbstractAgent</code> an bestimmte Events. Durch die Anwendung der
 * Prädikatslogik können Nachrichten auf bestimmte Inhalte überprüft werden und
 * bei Interesse weiterverarbeitet werden.
 * 
 * @author IngoT
 *
 */
public abstract class AbstractInterestProfile implements Serializable {

	private static final long serialVersionUID = -2118960722615655528L;
	private static Logger LOGGER = LoggerFactory.getLogger(AbstractInterestProfile.class);

	/*
	 * Damit ein InteressenProfil nur die Nachrichten erhält, die für das
	 * InteressenProfil relevant sind, wird durch die Prädikatenlogik den Inhalt der
	 * Nachrichten geprüft.
	 */
	private transient List<AbstractPredicate> predicates = new ArrayList<AbstractPredicate>();
	// Der Agent, zu dem das InterestProfile gehört
	private AbstractAgent agent = null;

	/**
	 * gibt den Agenten zurück, zu dem das InterestProfile gehört
	 * 
	 * @return agent, das dem Interessenprofil zugewiesen ist.
	 */
	public final AbstractAgent getAgent() {
		return this.agent;
	}

	/**
	 * Setzt den zugehörigen Agenten
	 * 
	 * @param agent,
	 *            zu dem das InterestProfile gehört
	 */
	public final void setAgent(AbstractAgent agent) {
		if (agent != null) {
			this.agent = agent;
		}
	}

	/**
	 * Ein Predicate vom Typ <code>AbstractPredicate</code> wird der Liste
	 * hinzugefügt.
	 * 
	 * @param predicate
	 *            Prädikat, welches dem Interessenprofil hinzugefügt wird.
	 */
	public final void add(AbstractPredicate predicate) {
		if (predicate != null) {
			LOGGER.log(Level.FINE, () -> String.format("add %s", predicate));
			predicates.add(predicate);
		}
	}

	/**
	 * gibt alle Predicates in Listenform zurück
	 * 
	 * @return predicates, die dem Interessenprofil zugeordnet sind.
	 */
	public final List<AbstractPredicate> getPredicates() {
		return this.predicates;
	}

	/**
	 * Muss von den abgeleiteten InterestProfiles implementiert werden. Diese
	 * Methode beinhaltet die Verarbeitungslogik der Events, die relevant sind.
	 * 
	 * @param event,
	 *            das verarbeitet werden soll
	 */
	public final void receive(AbstractEvent event) {
		if (event != null) {
			/*
			 * Wenn der Agent sich im Zustand "CountReceive" befindet, werden alle
			 * empfangenen Events gezählt.
			 */
			if (this.getAgent().getState() instanceof CountReceiveState) {
				// Loggingevents selber sollen nicht gezählt werden.
				((CountReceiveState) this.getAgent().getState()).add(event);
			}
						Timestamp sendedTime =  new Timestamp((long) event.getValueByKey("gesendetUm"));
						long dauer = TimeUtils.getCurrentTime().getTime() - sendedTime.getTime();
						LOGGER.log(Level.WARNING, "Event("+event.getId()+") -> "+event.getType()+" wurde mit Verz�gerung "+dauer+"msec von Topic "+event.getSource()+" empfangen.");
			 			
			 		
			/*
			 * Jede Subklasse muss die Methode doOnReceive implementieren, dieser wird im
			 * Anschluss ausgeführt.
			 */
			doOnReceive(event);
		}
	}

	/**
	 * Alle Anweisungen für die Verarbeitung von <code>AbstractEvent</code> die
	 * nicht gefiltert wurden.
	 * 
	 * @param event,
	 *            das dem Interesse entspricht
	 */
	protected abstract void doOnReceive(AbstractEvent event);

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
			AbstractInterestProfile that = (AbstractInterestProfile) obj;
			result = ModelUtils.equalsFor(this.getSignificantFields(), that.getSignificantFields());
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		formatter.format("%s Object {%s", this.getClass().getName(), SystemUtils.getLineSeparator());
		formatter.format("Predicates in the List: %s", SystemUtils.getLineSeparator());
		this.getPredicates().forEach(predicate -> {
			formatter.format("%s%s", predicate.toString(), SystemUtils.getLineSeparator());
		});
		formatter.format("}");

		formatter.close();
		return builder.toString();
	}

	/**
	 * Throws CloneNotSupportedException as a Event can not be meaningfully cloned.
	 * Construct a new Event instead.
	 *
	 * @throws CloneNotSupportedException
	 *             always
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
