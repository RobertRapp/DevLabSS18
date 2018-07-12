package eventprocessing.agent.dispatch;

import eventprocessing.agent.AgentException;

/**
 * Für alle Exceptions, die den Dispatcher betreffen.
 * 
 * @author IngoT
 *
 */
public class DispatcherException extends AgentException {

	private static final long serialVersionUID = 4566990960020937553L;

	/**
	 * Erzeugt eine neue NoValidInterestProfileException
	 */
	public DispatcherException() {
	}

	/**
	 * Erzeugt eine neue NoValidInterestProfileException mit einer detailierten
	 * Fehlermeldung
	 * 
	 * @param message
	 *            von der NoValidInterestProfileException ausgegeben wird.
	 */
	public DispatcherException(String message) {
		super(message);
	}

}
