package eventprocessing.event;

/**
 * Diese Exception wird geworfen, wenn dem Event eine ungültige id zugewiesen wird.
 * 
 * @author IngoT
 *
 */
public class NoValidIdException extends EventException {

	private static final long serialVersionUID = 6789886379110719062L;

	/**
	 * Erzeugt eine neue NoValidIdException
	 */
	public NoValidIdException() {
	}

	/**
	 * Erzeugt eine neue NoValidIdException mit einer detailierten Fehlermeldung
	 * 
	 * @param message
	 *            von der NoValidIdException ausgegeben wird.
	 */
	public NoValidIdException(String message) {
		super(message);
	}
}
