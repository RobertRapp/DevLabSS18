package eventprocessing.agent;

/**
 * Die Exception wird geworfen, wenn ein ungültige Angabe für die Topics von
 * Kafka angegeben werden, die konsumiert werden sollen.
 * 
 * @author IngoT
 *
 */
public class NoValidConsumingTopicException extends Exception {

	private static final long serialVersionUID = -7722428652834859678L;

	/**
	 * Erzeugt eine neue NoValidConsumingTopicException
	 */
	public NoValidConsumingTopicException() {
	}

	/**
	 * Erzeugt eine neue NoValidConsumingTopicException mit einer detailierten
	 * Fehlermeldung
	 * 
	 * @param message
	 *            von der NoValidConsumingTopicException ausgegeben wird.
	 */
	public NoValidConsumingTopicException(String message) {
		super(message);
	}

}
