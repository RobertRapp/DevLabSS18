package hdm.developmentlab.ebi.eve_implementation.settings;

import eventprocessing.event.AbstractEvent;

import java.util.ArrayList;
import java.util.Stack;

public class SessionEvent extends AbstractEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String sessionId;
    ArrayList<UserInfoEvent> users;
    Enum<TimeReferenceEnum> timereference;
    TokenEvent latesttopic;
    TokenEvent project;
    long sessionStart;
    long sessionEnd;
    Stack<TokenEvent> tokens;
    Stack<DocumentProposalEvent> docProposalList;

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

    public TokenEvent getLatesttopic() {
        return latesttopic;
    }

    public void setLatesttopic(TokenEvent latesttopic) {
        this.latesttopic = latesttopic;
    }

    public TokenEvent getProject() {
        return project;
    }

    public void setProject(TokenEvent project) {
        this.project = project;
    }

    public long getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(long sessionStart) {
        this.sessionStart = sessionStart;
    }

    public long getSessionEnd() {
        return sessionEnd;
    }

    public void setSessionEnd(long sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    public Stack<TokenEvent> getTokens() {
        return tokens;
    }

    public void addToken(TokenEvent tokens) {
        this.tokens.add(tokens);
    }

    public Stack<DocumentProposalEvent> getDocProposalList() {
        return docProposalList;
    }

    public void addDocProposal(DocumentProposalEvent docProposal) {
        this.docProposalList.add(docProposal);
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
