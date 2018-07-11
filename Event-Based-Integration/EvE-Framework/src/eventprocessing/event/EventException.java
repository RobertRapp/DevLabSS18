package eventprocessing.event;

/**
 * Superklasse Für alle Exceptions, die Events betreffen.
 * 
 * @author IngoT
 *
 */
public class EventException extends Exception {

	private static final long serialVersionUID = -5526731189190932860L;

	/**
	 * Erzeugt eine neue EventException
	 */
	public EventException() {
	}

	/**
	 * Erzeugt eine neue EventException mit einer detailierten Fehlermeldung
	 * 
	 * @param message
	 *            von der EventException ausgegeben wird.
	 */
	public EventException(String message) {
		super(message);
	}
	
}
