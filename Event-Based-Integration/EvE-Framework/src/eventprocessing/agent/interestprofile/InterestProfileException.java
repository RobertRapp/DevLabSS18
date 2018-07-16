package eventprocessing.agent.interestprofile;

import eventprocessing.agent.AgentException;

/**
 * Exception die von AbstractInterestprofiles oder dessen Subklassen geworfen werden kann
 * 
 * 
 * @author IngoT
 *
 */
public class InterestProfileException extends AgentException {

	private static final long serialVersionUID = -8591471764229157395L;

	/**
	 * Erzeugt eine neue InterestProfileException
	 */
	public InterestProfileException() {
	}

	/**
	 * Erzeugt eine neue InterestProfileException mit einer detailierten Fehlermeldung
	 * 
	 * @param message
	 *            von der InterestProfileException ausgegeben wird.
	 */
	public InterestProfileException(String message) {
		super(message);
	}
}
