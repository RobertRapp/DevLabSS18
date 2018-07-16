package hdm.developmentlab.ebi.eve_implementation.protocolService.interestprofiles;


import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import eventprocessing.agent.AbstractAgent;
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
import hdm.developmentlab.ebi.eve_implementation.protocolService.ProtocolAgent;


public class Sessions extends AbstractInterestProfile {
	
	/**
	 *
	 * In dieser Methode wird ein Gesprächskontext empfangen, sobald ein Gespräch abgeschlossen wird.
	 * Anhand der Informationen die innerhalb des empfangenen Events gespeichert sind, wird ein Protokoll erzeugt.
	 * Das ausgehende Format ist machinell analysiserbar und die Informationen können im späteren Verlauf auch auf Google Drive gespeichert werden.
	 *
	 * @author rrapp, birk, pokorski, meier
	 */
	
	
	
	
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(Sessions.class);

	// Factory für die Erzeugung der Events
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static AbstractEvent protocolEvent = eventFactory.createEvent("AtomicEvent");

	
	protected void doOnReceive(AbstractEvent event) { System.out.println(this.getClass().getSimpleName()+" : Event angekommen "+event.getType()+" -um: " + TimeUtils.getCurrentTime());
	
		
		System.out.println("in Protokoll IP");
		System.out.println("event geht ein: " + event);
		
		switch(event.getType()) {
		
		case "SessionStartEvent": 
			System.out.println("Protokoll hat SessionStartEvent empfangen");
			ProtocolAgent.setSessionStart(event.getCreationDate());			
			break;
			
		case "DocProposalEvent": 
			System.out.println("Protokoll hat DocProposalEvent empfangen");
			ProtocolAgent.addProposedDocList(event);			
			break;
		
		case "UserInteractionEvent":
			System.out.println("Protokoll hat UserInteractionEvent empfangen");
			ProtocolAgent.addClickedDocList(event);
			break;
			
		case "SessionEndEvent":
			System.out.println("Protokoll hat SessionEndEvent empfangen");
			ProtocolAgent.setSessionEnd(event.getCreationDate());
			CreateProtocolEvent();
			break;
		
		default:
			System.out.println("In default case");
			
		for(Property<?> property :event.getProperties()) {
			
			switch (property.getKey().toLowerCase()) {
			
			case "teilnehmer1":
				System.out.println("Protokoll hat User1 empfangen: " + property.getValue().toString());
				ProtocolAgent.addUserList(property.getValue().toString());				
				break;
			case "teilnehmer2":
				System.out.println("Protokoll hat User2 empfangen: " + property.getValue().toString());
				ProtocolAgent.addUserList(property.getValue().toString());
				break;
			case "topic":
				if(property.getValue() != null) {
				System.out.println("Protokoll hat das folgende Topic empfangen: " + property.getValue().toString());
				ProtocolAgent.addTopicList(property.getValue().toString());
				}
				break;
			case "sessionstart":
				ProtocolAgent.setSessionStart(event.getCreationDate());
				break;
			case "project":
				if(property.getValue() != null) {
				System.out.println("Protokoll hat Projekt empfangen: " + property.getValue().toString());
				ProtocolAgent.addProjectList(property.getValue().toString());
				}
				break;
			case "sessionid":
				ProtocolAgent.setSessionId(property.getValue().toString());
				break;
			default:
				break;
			}
		}
		break;
		} 
		}
		
		
		public void CreateProtocolEvent() {
		try {
			
			protocolEvent.setType("ProtocolEvent");
			protocolEvent.add(new Property<String>("SessionID", ProtocolAgent.getSessionId()));
			protocolEvent.add(new Property<Timestamp>("SessionStart", ProtocolAgent.getSessionStart()));
			protocolEvent.add(new Property<Timestamp>("SessionEnd", ProtocolAgent.getSessionEnd()));
			Integer duration = TimeUtils.getDifferenceInSeconds(ProtocolAgent.getSessionEnd(), ProtocolAgent.getSessionStart());
			protocolEvent.add(new Property<Integer>("Duration", duration));
			protocolEvent.add(new Property<ArrayList<String>>("User", ProtocolAgent.getUserList()));
			protocolEvent.add(new Property<ArrayList<String>>("Topics", ProtocolAgent.getTopicList()));
			protocolEvent.add(new Property<ArrayList<AbstractEvent>>("ProposedDocs", ProtocolAgent.getProposedDocList()));
			protocolEvent.add(new Property<ArrayList<AbstractEvent>>("ClickedDocs", ProtocolAgent.getClickedDocList()));
			protocolEvent.add(new Property<ArrayList<String>>("Projects", ProtocolAgent.getProjectList()));
			
			try {
				this.getAgent().send(protocolEvent, "Protocol");
			} catch (NoValidEventException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LoggerFactory.getLogger("ProtocolSend");
		} catch (NoValidTargetTopicException e1) {
			LoggerFactory.getLogger("ProtocolSend");
		}
	}
	
	
	}


