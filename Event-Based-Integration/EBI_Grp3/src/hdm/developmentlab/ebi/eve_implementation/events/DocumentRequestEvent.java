package hdm.developmentlab.ebi.eve_implementation.events;

import java.util.ArrayList;

import eventprocessing.event.AbstractEvent;

public class DocumentRequestEvent extends AbstractEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TokenEvent token;
    private String projectid;
    private Enum<TimeReferenceEnum> timeref;
    private String sessionId;

    public TokenEvent getToken() {
        return token;
    }

    public void setToken(TokenEvent token) {
        this.token = token;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public Enum<TimeReferenceEnum> getTimeref() {
        return timeref;
    }

    public void setTimeref(Enum<TimeReferenceEnum> timeref) {
        this.timeref = timeref;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ArrayList<UserInfoEvent> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserInfoEvent> users) {
        this.users = users;
    }

    ArrayList<UserInfoEvent> users;



}

