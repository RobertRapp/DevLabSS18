package eventprocessing.agent.dispatch;

/**
 * Die Exception wird geworfen, wenn das <code>AbstractInterestProfile</code> ungültige
 * Werte aufweist. Sie wird in der Klasse <code>Dispatcher</code> verwendet, um
 * zu überprüfen ob das InterestProfile null ist oder es Predicates beinhaltet.
 * Wenn es entweder null oder leer ist, wird diese Exception geworfen.
 * 
 * @author IngoT
 *
 */
public class NoValidInterestProfileException extends Exception {

	private static final long serialVersionUID = 4566990960020937553L;

	/**
	 * Erzeugt eine neue NoValidInterestProfileException
	 */
	public NoValidInterestProfileException() {
	}

	/**
	 * Erzeugt eine neue NoValidInterestProfileException mit einer detailierten
	 * Fehlermeldung
	 * 
	 * @param message
	 *            von der NoValidInterestProfileException ausgegeben wird.
	 */
	public NoValidInterestProfileException(String message) {
		super(message);
	}

}
