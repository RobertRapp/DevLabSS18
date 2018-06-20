package eventprocessing.agent;

/**
 * Die Exception wird geworfen, wenn das <code>AbstractEvent</code> ungültige
 * Werte aufweist. Sie wird in der Klasse <code>AbstractAgent</code> verwendet, um
 * zu überprüfen ob das Event gültige Werte enthält.
 * 
 * @author IngoT
 *
 */
public class NoValidEventException extends Exception{

	private static final long serialVersionUID = -8348347983951759756L;

	/**
	 * Erzeugt eine neue NoValidEventException
	 */
	public NoValidEventException() {
	}

	/**
	 * Erzeugt eine neue NoValidEventException mit einer detailierten
	 * Fehlermeldung
	 * 
	 * @param message
	 *            von der NoValidEventException ausgegeben wird.
	 */
	public NoValidEventException(String message) {
		super(message);
	}
}
