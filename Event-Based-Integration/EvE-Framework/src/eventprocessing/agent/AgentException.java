package eventprocessing.agent;

public class AgentException extends Exception {

	private static final long serialVersionUID = 4990901207919582694L;

	/**
	 * Erzeugt eine neue AgentException
	 */
	public AgentException() {
	}

	/**
	 * Erzeugt eine neue AgentException mit einer detailierten Fehlermeldung
	 * 
	 * @param message
	 *            von der AgentException ausgegeben wird.
	 */
	public AgentException(String message) {
		super(message);
	}
}
