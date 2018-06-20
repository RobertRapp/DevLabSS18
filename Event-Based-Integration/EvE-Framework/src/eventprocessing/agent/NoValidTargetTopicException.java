package eventprocessing.agent;

/**
 * Die Exception wird geworfen, wenn ein ungültiger Zahlenwert für die Partition
 * von Kafka angegeben wird.
 * 
 * @author IngoT
 *
 */
public class NoValidTargetTopicException extends Exception {

	private static final long serialVersionUID = -3216116961138915228L;

	/**
	 * Erzeugt eine neue NoValidTargetTopicException
	 */
	public NoValidTargetTopicException() {
	}

	/**
	 * Erzeugt eine neue NoValidTargetTopicException mit einer detailierten
	 * Fehlermeldung
	 * 
	 * @param message
	 *            von der NoValidTargetTopicException ausgegeben wird.
	 */
	public NoValidTargetTopicException(String message) {
		super(message);
	}

}
