package eventprocessing.agent.state;

import eventprocessing.agent.AgentException;

/**
 * wird geworfen, wenn eine Methode ausgeführt wird von <code>State</code>, die
 * keine Implementierung beinhaltet sondern die Klasse rein für die Setzung eines
 * Zustandes verwendet wird, ohne spezifische Aktionen hervorzurufen.
 * 
 * @author IngoT
 *
 */
public class StateException extends AgentException {

	private static final long serialVersionUID = 2055573184897857454L;

	/**
	 * Erzeugt eine neue StateException
	 */
	public StateException() {
	}

	/**
	 * Erzeugt eine neue StateException mit einer detailierten
	 * Fehlermeldung
	 * 
	 * @param message
	 *            von der StateException ausgegeben wird.
	 */
	public StateException(String message) {
		super(message);
	}
	
}
