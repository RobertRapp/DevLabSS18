package eventprocessing.event;

/**
 * Diese Exception wird geworfen, wenn dem Event eine ungültige Property zugewiesen wird.
 * 
 * @author IngoT
 *
 */
public class NoVaildPropertyException extends EventException {

	private static final long serialVersionUID = -3182747352628365879L;

	/**
	 * Erzeugt eine neue NoVaildPropertyException
	 */
	public NoVaildPropertyException() {
	}

	/**
	 * Erzeugt eine neue NoVaildPropertyException mit einer detailierten Fehlermeldung
	 * 
	 * @param message
	 *            von der NoVaildPropertyException ausgegeben wird.
	 */
	public NoVaildPropertyException(String message) {
		super(message);
	}
}
