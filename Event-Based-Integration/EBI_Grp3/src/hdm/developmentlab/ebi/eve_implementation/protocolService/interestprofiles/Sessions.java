package hdm.developmentlab.ebi.eve_implementation.protocolService.interestprofiles;


import java.util.ArrayList;
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

	// Factory für die Erzeugung der Events
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());

	
	
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
		
		//Instanz von ProtocolAgent wird benötigt, um dort Attribute zwischenzuspeichern. 
		ProtocolAgent protocolagent = (ProtocolAgent) this.getAgent();
		 
		//ProtocolEvent ist das Event, dass am Ende raus geschickt wird 
		AbstractEvent protocolEvent = eventFactory.createEvent("AtomicEvent");
		
		//Benötigte Events werden vorbereitet
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

				//Properties aus zwischengespeicherten Attributswerten des ProtocolAgent auslesen
				sessionIDProp.setKey("sessionID");
				sessionIDProp.setValue(protocolagent.getSessionEnd().getId());
				startEvent.setKey("sessionStart");
				startEvent.setValue(protocolagent.getSessionStart());
				endEvent.setKey("sessionEnd");
				endEvent.setValue(protocolagent.getSessionEnd());
				userProp.setKey("user");
				userProp.setValue(protocolagent.getUserList());
				topicProp.setKey("topic");
				topicProp.setValue(protocolagent.getTopicList());
				projectProp.setKey("project");
				projectProp.setValue(protocolagent.getProjectList());
				durationProp.setKey("duration"); 
				durationProp.setValue(TimeUtils.getDifferenceInSeconds(protocolagent.getSessionEnd().getCreationDate(), protocolagent.getSessionStart().getCreationDate()));
				proposedDocsProp.setKey("propDoc");
				proposedDocsProp.setValue(protocolagent.getProposedDocList());
				clickedDocsProp.setKey("clickedDoc");
				clickedDocsProp.setValue(protocolagent.getClickedDocList());
			
				//Properties zu protocolEvent hinzufügen
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


