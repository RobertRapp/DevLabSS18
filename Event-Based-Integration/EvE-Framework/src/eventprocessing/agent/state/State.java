package eventprocessing.agent.state;

import java.io.Serializable;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.event.AbstractEvent;

/**
 * State beschreibt den Zustand eines Agenten.
 * Jeder Agent kann sich in nur einem Zustand befinden.
 * Jeder Zustand kann eine Aktion ausführen und diese wieder beenden.
 * 
 * @author IngoT
 *
 */
public interface State extends Serializable {

	/* Aktion die der Agent in seinem Zustand ausführen kann, 
	 * sollte keine Aktion vorgesehen sein, 
	 * kann bei der Implementierung eine Exception geworfen werden.
	 */
	public void doAction(AbstractAgent agent, AbstractEvent event) throws NoStateActionSupportException;
	
	/* Die Aktion des Agenten kann unterbrochen werden, 
	 * sollte kein Stop vorgesehen sein, 
	 * kann bei der Implementierung eine Exception geworfen werden.
	 */
	public void stopAction() throws NoStateActionSupportException;
	
}
