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


public class Sessions extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(TokenApplicationIP.class);

	// Factory für die Erzeugung der Events
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static ArrayList<AbstractEvent> topicList = new ArrayList<AbstractEvent>();
	private static ArrayList<AbstractEvent> userList = new ArrayList<AbstractEvent>();
	private static ArrayList<AbstractEvent> projectList = new ArrayList<AbstractEvent>();
	private static ArrayList<AbstractEvent> proposedDocList = new ArrayList<AbstractEvent>();
	private static ArrayList<AbstractEvent> clickedDocList = new ArrayList<AbstractEvent>();
	private static AbstractEvent sessionStart = eventFactory.createEvent("AtomicEvent");
	private static AbstractEvent sessionEnd = eventFactory.createEvent("AtomicEvent");
	
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

		//ProtocolEvent ist das Event, dass am Ende raus geschickt wird 
		AbstractEvent protocolEvent = eventFactory.createEvent("AtomicEvent");
		//Aus Tokens werden Topics ausgelesen 
		AbstractEvent user = eventFactory.createEvent("AtomicEvent");
		AbstractEvent project = eventFactory.createEvent("AtomicEvent");
		AbstractEvent topic = eventFactory.createEvent("AtomicEvent");
		AbstractEvent proposedDoc = eventFactory.createEvent("AtomicEvent");
		AbstractEvent clickedDoc = eventFactory.createEvent("AtomicEvent");
		AbstractEvent sessionStart = eventFactory.createEvent("AtomicEvent");
		AbstractEvent sessionEnd = eventFactory.createEvent("AtomicEvent");
		
		// Prüfe ob das empfangene Event vom Typ TokenEvent ist. Wenn ja in TokenListe anfügen 
		if (EventUtils.isType("TokenEvent", event) && EventUtils.findPropertyByKey(event, "topic") != null) {
			topic = event;
			topicList.add(topic);
		} 
		
		// Prüfe ob das empfangene Event vom Typ TokenEvent ist. Wenn ja in TokenListe anfügen 
		if (EventUtils.isType("TokenEvent", event) && EventUtils.findPropertyByKey(event, "project") != null) {
			project = event;
			projectList.add(project);
		} 
		
		// Prüfe ob das empfangene Event vom Typ TokenEvent ist. Wenn ja in TokenListe anfügen 
		if (EventUtils.isType("UserInfos", event) && EventUtils.findPropertyByKey(event, "user") != null) {
			user = event;
			userList.add(user);
		} 
		
		// Prüfe ob das empfangene Event ein vorgeschlagenenes Dokument ist. Wenn ja in RequestListe anfügen 
		if (EventUtils.isType("proposedDoc", event)) {
			proposedDoc = event;
			proposedDocList.add(proposedDoc);
		}
		
		// Prüfe ob das empfangene Event vom Typ RequestEvent ist. Wenn ja in RequestListe anfügen 
		if (EventUtils.isType("clickedDoc", event)) {
			clickedDoc = event;
			clickedDocList.add(clickedDoc);
		}
		
		// Prüfe ob das empfangene Event vom Typ SessionEvent ist. Wenn ja, Sessioninfos speichern
		if (EventUtils.isType("SessionInfo", event)) {
			if(EventUtils.findPropertyByValue(event, "type").getValue().equals("SessionStart") == true) {
				sessionStart = event;
			}
			if(EventUtils.findPropertyByValue(event, "type").getValue().equals("SessionEnd") == true) {
				sessionEnd = event;
				
				//Properties vorbereiten
				Property<Long> sessionIDProp = new Property<>();;
				Property<AbstractEvent> startEvent = new Property<>();
				Property<AbstractEvent> endEvent = new Property<>();
				Property<ArrayList<AbstractEvent>> userProp = new Property<>();
				Property<ArrayList<AbstractEvent>> topicProp = new Property<>();
				Property<ArrayList<AbstractEvent>> projectProp = new Property<>();
				Property<Integer> durationProp = new Property<>();

				//Properties füllen
				sessionIDProp.setKey("sessionID");
				sessionIDProp.setValue(sessionEnd.getId());
				startEvent.setKey("sessionStart");
				startEvent.setValue(sessionStart);
				endEvent.setKey("sessionEnd");
				endEvent.setValue(sessionEnd);
				userProp.setKey("user");
				userProp.setValue(userList);
				topicProp.setKey("topics");
				topicProp.setValue(topicList);
				projectProp.setKey("projects");
				projectProp.setValue(projectList);
				durationProp.setKey("duration"); 
				durationProp.setValue(TimeUtils.getDifferenceInSeconds(sessionStart.getCreationDate(), sessionEnd.getCreationDate()));
				
				//Properties zu protocollEvent hinzufügen
				protocolEvent.add(sessionIDProp);
				protocolEvent.add(startEvent);
				protocolEvent.add(endEvent);
				protocolEvent.add(userProp);
				protocolEvent.add(topicProp);
				protocolEvent.add(projectProp);
				protocolEvent.add(durationProp);
				
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
	
	
	}


