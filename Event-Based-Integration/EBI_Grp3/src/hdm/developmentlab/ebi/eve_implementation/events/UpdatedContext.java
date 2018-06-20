package hdm.developmentlab.ebi.eve_implementation.events;

import java.util.ArrayList;
import java.util.Stack;

import eventprocessing.event.AbstractEvent;

public class UpdatedContext extends AbstractEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	String sessionId;
    ArrayList<UserInfoEvent> users;
    Enum<TimeReferenceEnum> timereference;
    TokenEvent latestActivity;
    TokenEvent project;
    
	public TokenEvent getLatestActivity() {
		return latestActivity;
	}

	public void setLatestActivity(TokenEvent latestActivity) {
		this.latestActivity = latestActivity;
	}

	public void setUsers(ArrayList<UserInfoEvent> users) {
		this.users = users;
	}

    public ArrayList<UserInfoEvent> getUsers() {
        return users;
    }

    public void setUsers(UserInfoEvent userEvent) {
        this.users.add(userEvent);
    }

    public Enum<TimeReferenceEnum> getTimereference() {
        return timereference;
    }

    public void setTimereference(Enum<TimeReferenceEnum> timereference) {
        this.timereference = timereference;
    }
  

    public TokenEvent getProject() {
        return project;
    }

    public void setProject(TokenEvent project) {
        this.project = project;
    }
 

    public Stack<ClickEvent> getClickInteractionList() {
        return clickInteractionList;
    }

    public void addClickInteraction(ClickEvent clickInteraction) {
        this.clickInteractionList.add(clickInteraction);
    }

    Stack<ClickEvent> clickInteractionList;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
