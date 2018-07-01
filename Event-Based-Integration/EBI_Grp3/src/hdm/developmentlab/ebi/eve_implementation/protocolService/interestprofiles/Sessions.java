package hdm.developmentlab.ebi.eve_implementation.protocolService.interestprofiles;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.TimeUtils;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenApplicationIP;
import hdm.developmentlab.ebi.eve_implementation.protocolService.CreateNewXML;
import hdm.developmentlab.ebi.eve_implementation.protocolService.ProtocolAgent;


public class Sessions extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(TokenApplicationIP.class);

//	// Factory für die Erzeugung der Events
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
//	private static ArrayList<AbstractEvent> topicList = new ArrayList<AbstractEvent>();
//	private static ArrayList<AbstractEvent> userList = new ArrayList<AbstractEvent>();
//	private static ArrayList<AbstractEvent> projectList = new ArrayList<AbstractEvent>();
//	private static ArrayList<AbstractEvent> proposedDocList = new ArrayList<AbstractEvent>();
//	private static ArrayList<AbstractEvent> clickedDocList = new ArrayList<AbstractEvent>();
//	private static AbstractEvent sessionStart = eventFactory.createEvent("AtomicEvent");
//	private static AbstractEvent sessionEnd = eventFactory.createEvent("AtomicEvent");
//	
	
	
	/**
	 *
	 * In dieser Methode wird ein Gesprächskontext empfangen, sobald ein Gespräch abgeschlossen wird.
	 * Anhand der Informationen die innerhalb des empfangenen Events gespeichert sind, wird ein Protokoll erzeugt.
	 * Das ausgehende Format ist machinell analysiserbar und wird an den Agenten weiter gegeben, damit dieser das Event
	 * auf Google Drive abspeichern kann.
	 *
	 * @param arg0
	 */


	@Override
	protected void doOnReceive(AbstractEvent event) {
		System.out.println("IN RECEIVE VON ProtocollIP");
		
		ProtocolAgent protocolagent = (ProtocolAgent) this.getAgent();
		 
		//ProtocolEvent ist das Event, dass am Ende raus geschickt wird 
		AbstractEvent protocolEvent = eventFactory.createEvent("AtomicEvent");
		//Aus Tokens werden Topics ausgelesen 
		AbstractEvent user = eventFactory.createEvent("AtomicEvent");
		AbstractEvent project = eventFactory.createEvent("AtomicEvent");
		AbstractEvent topic = eventFactory.createEvent("AtomicEvent");
		AbstractEvent proposedDoc = eventFactory.createEvent("AtomicEvent");
		AbstractEvent clickedDoc = eventFactory.createEvent("AtomicEvent");

		
		// Prüfe ob das empfangene Event vom Typ TokenEvent ist. Wenn ja in TokenListe anfügen 
		if (EventUtils.isType("TokenEvent", event) && EventUtils.hasProperty(event, "topic")) {
			topic = event;
			protocolagent.addTopicList(topic);
		} 
		
		// Prüfe ob das empfangene Event vom Typ TokenEvent ist. Wenn ja in TokenListe anfügen 
		if (EventUtils.isType("TokenEvent", event) && EventUtils.hasProperty(event, "project")) {
			project = event;
			protocolagent.addProjectList(project);
		} 
		
		// Prüfe ob das empfangene Event vom Typ TokenEvent ist. Wenn ja in TokenListe anfügen 
		if (EventUtils.isType("user", event)) {
			user = event;
			protocolagent.addUserList(user);
		} 
		
		// Prüfe ob das empfangene Event ein vorgeschlagenenes Dokument ist. Wenn ja in RequestListe anfügen 
		if (EventUtils.isType("proposedDoc", event)) {
			proposedDoc = event;
			protocolagent.addProposedDocList(proposedDoc);
		}
		
		// Prüfe ob das empfangene Event vom Typ RequestEvent ist. Wenn ja in RequestListe anfügen 
		if (EventUtils.isType("clickedDoc", event)) {
			clickedDoc = event;
			protocolagent.addClickedDocList(clickedDoc);
		}
		
		// Prüfe ob das empfangene Event vom Typ SessionEvent ist. Wenn ja, Sessioninfos speichern
			if(EventUtils.isType("sessionStart", event)) {
				protocolagent.setSessionStart(event);

			}
			if(EventUtils.isType("sessionEnd", event)) {
				protocolagent.setSessionEnd(event);
				
				//Properties vorbereiten
				Property<Long> sessionIDProp = new Property<>();
				Property<AbstractEvent> startEvent = new Property<>();
				Property<AbstractEvent> endEvent = new Property<>();
				Property<ArrayList<AbstractEvent>> userProp = new Property<>();
				Property<ArrayList<AbstractEvent>> topicProp = new Property<>();
				Property<ArrayList<AbstractEvent>> projectProp = new Property<>();
				Property<ArrayList<AbstractEvent>> proposedDocsProp = new Property<>();
				Property<ArrayList<AbstractEvent>> clickedDocsProp = new Property<>();
				Property<Integer> durationProp = new Property<>();

				//Properties füllen
				sessionIDProp.setKey("sessionID");
				sessionIDProp.setValue(protocolagent.getSessionEnd().getId());
				startEvent.setKey("sessionStart");
				startEvent.setValue(protocolagent.getSessionStart());
				endEvent.setKey("sessionEnd");
				endEvent.setValue(protocolagent.getSessionEnd());
				//System.out.println("endEvent: " + endEvent.getValue());
				userProp.setKey("user");
				userProp.setValue(protocolagent.getUserList());
				//System.out.println("userProp: " + userProp.getValue());
				topicProp.setKey("topic");
				topicProp.setValue(protocolagent.getTopicList());
				//System.out.println("topicProp: " + topicProp.getValue());
				projectProp.setKey("project");
				projectProp.setValue(protocolagent.getProjectList());
				//System.out.println("projectProp: " + projectProp.getValue());
				durationProp.setKey("duration"); 
				durationProp.setValue(TimeUtils.getDifferenceInSeconds(protocolagent.getSessionEnd().getCreationDate(), protocolagent.getSessionStart().getCreationDate()));
				//System.out.println("durationProp: " + durationProp.getValue());
				proposedDocsProp.setKey("propDoc");
				proposedDocsProp.setValue(protocolagent.getProposedDocList());
				System.out.println("__________________________");
				System.out.println("proposedDocsProp: " + protocolagent.getProposedDocList());
				System.out.println("____________________");
				clickedDocsProp.setKey("clickedDoc");
				clickedDocsProp.setValue(protocolagent.getClickedDocList());
				//System.out.println("clickedDocsProp: " + clickedDocsProp.getValue());
				
				//Properties zu protocollEvent hinzufügen
				protocolEvent.add(sessionIDProp);
				protocolEvent.add(startEvent);
				protocolEvent.add(endEvent);
				protocolEvent.add(userProp);
				protocolEvent.add(topicProp);
				protocolEvent.add(projectProp);
				protocolEvent.add(durationProp);
				protocolEvent.add(clickedDocsProp);
				protocolEvent.add(proposedDocsProp);
				
				CreateNewXML createxml = new CreateNewXML();
				createxml.CreateNewXMl(protocolEvent);
				

				// Sendet das Event an DR (welches Topic ???) 
				try {
					getAgent().send(protocolEvent, "TOPIC");
				} catch (NoValidEventException e1) {
					LoggerFactory.getLogger("ProtocolSend");
				} catch (NoValidTargetTopicException e1) {
					LoggerFactory.getLogger("ProtocolSend");
				}
				
				
			}

			
		} 
	
	
	}


