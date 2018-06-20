package eventprocessing.consume.spark.streaming;

/**
 * Die Exception wird geworfen, wenn ein ungültiger Agent erkannt wird.
 * 
 * @author IngoT
 *
 */
public class NoValidAgentException extends Exception {

	private static final long serialVersionUID = -8695963782008920224L;

	/**
	 * Erzeugt eine neue NoValidAgentException
	 */
	public NoValidAgentException() {
	}

	/**
	 * Erzeugt eine neue NoValidAgentException mit einer detailierten
	 * Fehlermeldung
	 * 
	 * @param message
	 *            von der NoValidAgentException ausgegeben wird.
	 */
	public NoValidAgentException(String message) {
		super(message);
	}

}
