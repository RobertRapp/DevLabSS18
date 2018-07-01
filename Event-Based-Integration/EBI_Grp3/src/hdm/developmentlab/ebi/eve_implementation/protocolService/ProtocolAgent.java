package hdm.developmentlab.ebi.eve_implementation.protocolService;

import java.util.ArrayList;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.NullPredicateException;
import eventprocessing.agent.interestprofile.predicates.logical.Or;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenDocumentType;
import hdm.developmentlab.ebi.eve_implementation.protocolService.interestprofiles.Sessions;

public class ProtocolAgent extends AbstractAgent {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ArrayList<AbstractEvent> topicList = new ArrayList<>();
	private static ArrayList<AbstractEvent> userList = new ArrayList<>();
	private static ArrayList<AbstractEvent> projectList = new ArrayList<>();
	private static ArrayList<AbstractEvent> proposedDocList = new ArrayList<>();
	private static ArrayList<AbstractEvent> clickedDocList = new ArrayList<>();
	private static AbstractEvent sessionStart;
	private static AbstractEvent sessionEnd;

	@Override
	protected void doOnInit() {

		
		this.setId("ProtocolAgent");
		/*
		 * Angabe der Topics, die konsumiert werden sollen. Es können mehrere Topics
		 * angegeben werden.
		 */
		try {
			this.add("SessionInfo");
			this.add("TokenGeneration");
			this.add("UserInfo");
			this.add("proposedDoc");
			this.add("clickedDoc");
			
			// + alle Topics also Doc Requests auch
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}
		
		/*
		 * Fügt dem Agenten ein InteressenProfil hinzu. Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		
		try {
			AbstractInterestProfile ip = new Sessions();
			try {
				ip.add(new Or(new IsEventType("TokenEvent"), new IsEventType("user"), new IsEventType("proposedDoc"), new IsEventType("clickedDoc"), new IsEventType("sessionStart"), new IsEventType("sessionEnd")));
			} catch (NullPredicateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.add(ip);
			} catch (NoValidInterestProfileException e1) {
				e1.printStackTrace();
			}
		}
	
	public ArrayList<AbstractEvent> getTopicList() {
		return topicList;
	}

	public void addTopicList(AbstractEvent topicList) {
		ProtocolAgent.topicList.add(topicList);
	}

	public ArrayList<AbstractEvent> getUserList() {
		return userList;
	}

	public void addUserList(AbstractEvent userList) {
		ProtocolAgent.userList.add(userList);
	}

	public ArrayList<AbstractEvent> getProjectList() {
		return projectList;
	}

	public void addProjectList(AbstractEvent projectList) {
		ProtocolAgent.projectList.add(projectList);
	}

	public ArrayList<AbstractEvent> getProposedDocList() {
		return proposedDocList;
	}

	public void addProposedDocList(AbstractEvent proposedDocList) {
		ProtocolAgent.proposedDocList.add(proposedDocList);
	}

	public ArrayList<AbstractEvent> getClickedDocList() {
		return clickedDocList;
	}

	public void addClickedDocList(AbstractEvent clickedDocList) {
		ProtocolAgent.clickedDocList.add(clickedDocList);
	}

	public AbstractEvent getSessionStart() {
		return sessionStart;
	}

	public void setSessionStart(AbstractEvent sessionStart) {
		ProtocolAgent.sessionStart = sessionStart;
	}

	public AbstractEvent getSessionEnd() {
		return sessionEnd;
	}

	public void setSessionEnd(AbstractEvent sessionEnd) {
		ProtocolAgent.sessionEnd = sessionEnd;
	}
	
	}
