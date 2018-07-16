package hdm.developmentlab.ebi.eve_implementation.protocolService;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.logical.NullPredicateException;
import eventprocessing.agent.interestprofile.predicates.logical.Or;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.agent.interestprofile.predicates.statement.IsFromTopic;
import eventprocessing.event.AbstractEvent;
import hdm.developmentlab.ebi.eve_implementation.protocolService.interestprofiles.Sessions;

public class ProtocolAgent extends AbstractAgent {

	/**
	 * Der ProtokollAgent erstellt ein Protokoll des Geprächsverlaufs, sowie alles
	 * Interaktionen, indem er von den Topics SessionState, SessionContext und
	 * UserInteraction konsumiert. Diese Informationen werden ausglesen formatiert
	 * und in ein XML Dokument geschrieben.
	 * 
	 * @author rrapp, birk, meier
	 */

	private static final long serialVersionUID=1L;
	private static String sessionId; 
	private static ArrayList<String> topicList=new ArrayList<>();
	private static ArrayList<String> userList=new ArrayList<>();
	private static ArrayList<String> projectList=new ArrayList<>();
	private static ArrayList<AbstractEvent> proposedDocList=new ArrayList<>();
	private static ArrayList<AbstractEvent> clickedDocList=new ArrayList<>();
	private static Timestamp sessionStart;
	private static Timestamp sessionEnd;


	
	protected void doOnInit() {

		this.setId("ProtocolAgent");
		/*
		 * Angabe der Topics, die konsumiert werden sollen. Es können mehrere Topics
		 * angegeben werden. Es werden die Topics, SessionState, SessionContext und
		 * UserInteraction konsumiert.
		 */
		try {
			this.add("SessionState");
			this.add("SessionContext");
			this.add("UserInteraction");

			// + alle Topics also Doc Requests auch
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}

		/*
		 * 		 
		 * Fügt dem Agenten ein InteressenProfil hinzu. Ein Agent kann mehrere
		 * InteressenProfile besitzen. Dem Agent wird das IP Sessions hinzugefügt.
		 * 
		 */

		try {
			AbstractInterestProfile ip = new Sessions();
			try {
				ip.add(new Or(new IsEventType("SessionContextEvent"), 
						new IsEventType("SessionStartEvent"), new IsEventType("SessionEndEvent"), 
						new IsFromTopic("UserInteraction")));

			} catch (NullPredicateException e) {
				
				e.printStackTrace();
				
			}

			this.add(ip);
			
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
	}

	public static Timestamp getSessionStart() {
		return sessionStart;
	}


	public static void setSessionStart(Timestamp event) {
		ProtocolAgent.sessionStart = event;
	}

	public static Timestamp getSessionEnd() {
		return sessionEnd;
	}

	public static void setSessionEnd(Timestamp sessionEnd) {
		ProtocolAgent.sessionEnd = sessionEnd;
	}

	public  static ArrayList<String> getTopicList() {
		return topicList;
	}

	public  static void addTopicList(String topicList) {
		ProtocolAgent.topicList.add(topicList);
	}

	public  static ArrayList<String> getUserList() {
		return userList;
	}

	public static void addUserList(String userList) {
		ProtocolAgent.userList.add(userList);
	}

	public  static ArrayList<String> getProjectList() {
		return projectList;
	}

	public static void addProjectList(String projectList) {
		ProtocolAgent.projectList.add(projectList);
	}

	public static ArrayList<AbstractEvent> getProposedDocList() {
		return proposedDocList;
	}

	public static void addProposedDocList(AbstractEvent proposedDocList) {
		ProtocolAgent.proposedDocList.add(proposedDocList);
	}

	public static ArrayList<AbstractEvent> getClickedDocList() {
		return clickedDocList;
	}

	public static void addClickedDocList(AbstractEvent clickedDocList) {
		ProtocolAgent.clickedDocList.add(clickedDocList);
	}

	public static String getSessionId() {
		return sessionId;
	}


	public static void setSessionId(String sessionId) {
		ProtocolAgent.sessionId = sessionId;
	}

}
