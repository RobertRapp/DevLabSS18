package eventprocessing.agent.state;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.event.AbstractEvent;

/**
 * Wenn sich der Agent im ReadyState befindet, ist er initialisiert und kann
 * Befehle entgegennehmen.
 * 
 * @author IngoT
 *
 */
public final class ReadyState implements State {

	private static final long serialVersionUID = 4292549803137474539L;

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
