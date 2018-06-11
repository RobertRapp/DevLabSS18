package hdm.developmentlab.ebi.eve_implementation.settings;

import eventprocessing.event.AbstractEvent;

public class SessionStateEvent extends AbstractEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sessionID;
	
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	} 
}
