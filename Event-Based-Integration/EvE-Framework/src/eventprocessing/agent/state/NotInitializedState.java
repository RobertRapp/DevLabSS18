package eventprocessing.agent.state;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.event.AbstractEvent;

/**
 * Der Agent wurde noch nicht initialisiert, dieser Zustand ist der
 * Defaultzustand. Erst wenn der Agent initialisiert wurde, ändert er seinen
 * Zustand in <code>ReadyState</code>.
 * 
 * @author IngoT
 *
 */
public final class NotInitializedState implements State {

	private static final long serialVersionUID = 6614844924871744747L;

	/**
	 * keine Aktionen nötig
	 */
	@Override
	public void doAction(AbstractAgent agent, AbstractEvent event) throws NoStateActionSupportException {
		throw new NoStateActionSupportException();

	}

	@Override
	public void stopAction() throws NoStateActionSupportException {
		throw new NoStateActionSupportException();

	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
