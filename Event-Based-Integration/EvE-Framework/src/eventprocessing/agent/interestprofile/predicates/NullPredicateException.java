package eventprocessing.agent.interestprofile.predicates;

/**
 * Wenn ein leeres <code>AbstractPredicate</code> übergeben wird an die
 * logischen Prädikaten, wird diese Exception geworfen.
 * 
 * 
 * @author IngoT
 *
 */
public class NullPredicateException extends Exception {

	private static final long serialVersionUID = -8591471764229157395L;

	/**
	 * Erzeugt eine neue NullPredicateException
	 */
	public NullPredicateException() {
	}

	/**
	 * Erzeugt eine neue NullPredicateException mit einer detailierten Fehlermeldung
	 * 
	 * @param message
	 *            von der NullPredicateException ausgegeben wird.
	 */
	public NullPredicateException(String message) {
		super(message);
	}
}
