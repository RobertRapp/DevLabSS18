package hdm.developmentlab.ebi.eve_implementation.protocolService;

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
import eventprocessing.utils.TimeUtils;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenDocumentType;
import hdm.developmentlab.ebi.eve_implementation.protocolService.interestprofiles.Sessions;

public class ProtocolAgent extends AbstractAgent {


	/**
	 * Der ProtokollAgent erstellt ein Protokoll des Geprächsverlaufs, sowie alles Interaktionen,
	 * indem er von den Topics SessionState, SessionContext und UserInteraction konsumiert. 
	 * Diese Informationen werden ausglesen formatiert und in ein XML Dokument geschrieben.
	 * @author rrapp, birk, meier
	 */
	
	private static final long serialVersionUID=1L;
	private static String sessionId; 
	private static ArrayList<String> topicList=new ArrayList<>();
	private static ArrayList<String> userList=new ArrayList<>();
	private static ArrayList<String> projectList=new ArrayList<>();
	private static ArrayList<AbstractEvent> proposedDocList=new ArrayList<>();
	private static ArrayList<AbstractEvent> clickedDocList=new ArrayList<>();
	private static String sessionStart;
	private static String sessionEnd;

	@Override
	protected void doOnInit() {

		
		this.setId("ProtocolAgent");
		/*
		 * Angabe der Topics, die konsumiert werden sollen. Es können mehrere Topics
		 * angegeben werden.
		 * Es werden die Topics, SessionState, SessionContext und UserInteraction konsumiert.
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
		 * Fügt dem Agenten ein InteressenProfil hinzu. Ein Agent kann mehrere
		 * InteressenProfile besitzen.
		 * Dem Agent wird das IP Sessions hinzugefügt.
		 */
		
		try {
			AbstractInterestProfile ip = new Sessions();
			try {
				ip.add(new Or(new IsEventType("SessionContext"), new IsEventType("SessionStartEvent"), new IsEventType("SessionEndEvent"), new IsFromTopic("UserInteraction")));
			} catch (NullPredicateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.add(ip);
			} catch (NoValidInterestProfileException e1) {
				e1.printStackTrace();
			}
		}
	
	
	public String getSessionStart() {
		return sessionStart;
	}

	public void setSessionStart(String sessionStart) {
		ProtocolAgent.sessionStart= sessionStart;
	}

	public String getSessionEnd() {
		return sessionEnd;
	}

	public void setSessionEnd(String sessionEnd) {
		ProtocolAgent.sessionEnd= sessionEnd;
	}


	public  ArrayList<String> getTopicList() {
		return topicList;
	}


	public  void addTopicList(String topicList) {
		ProtocolAgent.topicList.add(topicList);
	}


	public  ArrayList<String> getUserList() {
		return userList;
	}


	public void addUserList(String userList) {
		ProtocolAgent.userList.add(userList);
	}


	public  ArrayList<String> getProjectList() {
		return projectList;
	}


	public  void addProjectList(String projectList) {
		ProtocolAgent.projectList.add(projectList);
	}


	public  ArrayList<AbstractEvent> getProposedDocList() {
		return proposedDocList;
	}


	public  void addProposedDocList(AbstractEvent proposedDocList) {
		ProtocolAgent.proposedDocList.add(proposedDocList);
	}


	public  ArrayList<AbstractEvent> getClickedDocList() {
		return clickedDocList;
	}


	public  void addClickedDocList(AbstractEvent clickedDocList) {
		ProtocolAgent.clickedDocList.add(clickedDocList);
	}


	public  long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getSessionId() {
		return sessionId;
	}


	public  void setSessionId(String sessionId) {
		ProtocolAgent.sessionId = sessionId;
	}
	
	}
