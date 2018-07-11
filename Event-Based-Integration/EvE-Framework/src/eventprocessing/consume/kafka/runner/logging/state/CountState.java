package eventprocessing.consume.kafka.runner.logging.state;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.state.ReadyState;
import eventprocessing.agent.state.State;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.model.EventUtils;

/**
 * Dient dem Agenten in einen "zähler"-Zustand zu wechseln. Der Agent soll in
 * der Lage sein eingehende- sowie ausgehende Events zu loggen und in einem
 * vorgegebenen Intervall als Report abzusenden. Der Agent kann sich nur in
 * einem Zustand zurselben Zeit befinden und ignoriert Befehle in einen anderen
 * Zustand zu wechseln, sofern er sich nicht im <code>ReadyState</code> Zustand
 * befindet.
 * 
 * @author IngoT
 *
 */
public abstract class CountState extends TimerTask implements State {

	private static final long serialVersionUID = 1126830266257416697L;
	// Agent der den Zustand annimmt.
	private AbstractAgent agent = null;
	// Für die Ausführung der Routine
	private transient Timer timer = null;

	/**
	 * Gibt die Liste mit allen gezählten Events zurück
	 * 
	 * @return Liste, mit allen Events
	 */
	public List<AbstractEvent> getCountedEvents() {
		return this.getAgent().getAccum().value();
	}

	/**
	 * Übergibt das Event dem Agenten zum zählen.
	 * 
	 * @param event,
	 *            Event dass der Agent vorhalten soll.
	 */
	public void add(AbstractEvent event) {
		if (event != null) {
			this.getAgent().getAccum().add(event);
		}
	}

	/**
	 * leert die Liste
	 */
	public void resetCounter() {
		this.getAgent().getAccum().reset();
	}

	/**
	 * Gibt den dazugehörigen Agenten zurück
	 * 
	 * @return agent, der sich in diesem Zustand befindet
	 */
	public AbstractAgent getAgent() {
		return this.agent;
	}

	/**
	 * setzt den Agenten für die Ausführung.
	 * 
	 * @param agent, dessen Zustand geändert werden soll.
	 */
	public void setAgent(AbstractAgent agent) {
		if (agent != null) {
			this.agent = agent;
		}
	}

	/**
	 * Aktion die ausgeführt werden soll, wenn der Agent ein
	 * <code>CommandEvent</code> empfängt. Der Zustand des Agenten wird entsprechend
	 * geändert und das Interval festgelegt. Anschließend führt der Agent die
	 * entsprechenden Anweisungen um. Die Anweisungen sind in den run-Methoden der
	 * Subklassen definiert.
	 * 
	 * @param agent,
	 *            der die Aktion ausführen soll (Zustand).
	 * @param event,
	 *            beinhaltet den Befehl sowie den Interval
	 */
	@Override
	public void doAction(AbstractAgent agent, AbstractEvent event) {
		// Prüfung ob Agent und Event nicht null sind
		if (agent != null && event != null) {
			// Interval muss größer als eine Sekunde sein

			@SuppressWarnings("unchecked")
			Property<Integer> property = (Property<Integer>) EventUtils.findPropertyByKey(event, "Interval");
			if (property.getValue() > 1000) {
				// Versetzt den Agenten in den Zustand
				agent.setState(this);
				// Referenz auf den Agenten wird hinterlegt
				this.agent = agent;
				// Neues Timer() Objekt für die Ausführung des Intervals wird erzeugt.
				timer = new Timer();
				// führt die Anweisungen in dem vorgegebenen Interval aus.
				timer.schedule(this, 0, property.getValue());
			}
		}
	}

	/**
	 * beendet die aktuelle Aktion des Agenten. Der Zustand des Agenten wird im
	 * Anschluss auf "Ready" gesetzt und kann neue Befehle empfangen.
	 * 
	 * Achtung: Es kann einige Sekunden dauern bis der Agent den Befehl verarbeiten
	 * kann. Daher kann es passieren, dass er die vorherige Aktion nochmals
	 * ausführt.
	 */
	public void stopAction() {
		// Prüfung ob der timer nicht null ist
		if (timer != null) {
			// bricht die Verarbeitung ab (aktueller Durchlauf wird beendet)
			timer.cancel();
			// setzt den Status auf "Ready"
			this.agent.setState(new ReadyState());
		} // else, Exception
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
