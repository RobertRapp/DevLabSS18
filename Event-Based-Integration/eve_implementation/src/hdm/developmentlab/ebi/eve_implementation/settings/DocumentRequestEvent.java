package hdm.developmentlab.ebi.eve_implementation.settings;

import eventprocessing.event.AbstractEvent;

import java.util.ArrayList;

public class DocumentRequestEvent extends AbstractEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TokenEvent token;
    String projectid;
    Enum<TimeReferenceEnum> timeref;
    String sessionId;

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

