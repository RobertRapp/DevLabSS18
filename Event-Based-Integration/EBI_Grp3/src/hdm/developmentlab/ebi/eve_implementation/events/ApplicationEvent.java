package hdm.developmentlab.ebi.eve_implementation.events;

import eventprocessing.event.AbstractEvent;

public class ApplicationEvent extends AbstractEvent {
	
	private static final long serialVersionUID = 1L;
	
	private String ApplicationID;
	private String ApplicationName;
	private String Link;
	
	
	public String getApplicationID() {
		return ApplicationID;
	}
	public void setApplicationID(String applicationID) {
		ApplicationID = applicationID;
	}
	public String getApplicationName() {
		return ApplicationName;
	}
	public void setApplicationName(String applicationName) {
		ApplicationName = applicationName;
	}
	public String getLink() {
		return Link;
	}
	public void setLink(String link) {
		Link = link;
	}

	
	
}
