package hdm.developmentlab.ebi.eve_implementation.settings;

import eventprocessing.event.AbstractEvent;

public class TokenEvent extends AbstractEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6649640980097463080L;
	private String sessionID;
	
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	} 
	

}
