package eventprocessing.agent.interestprofile.predicates;

import eventprocessing.agent.interestprofile.InterestProfileException;

/**
 * Exception die von AbstractPredicate oder dessen Subklassen geworfen werden kann
 * 
 * 
 * @author IngoT
 *
 */
public class PredicateException extends InterestProfileException {

	private static final long serialVersionUID = -8591471764229157395L;

	/**
	 * Erzeugt eine neue PredicateException
	 */
	public PredicateException() {
	}

	/**
	 * Erzeugt eine neue PredicateException mit einer detailierten Fehlermeldung
	 * 
	 * @param message
	 *            von der PredicateException ausgegeben wird.
	 */
	public PredicateException(String message) {
		super(message);
	}
}
