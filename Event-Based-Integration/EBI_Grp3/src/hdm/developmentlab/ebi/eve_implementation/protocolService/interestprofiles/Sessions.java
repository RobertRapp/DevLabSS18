package hdm.developmentlab.ebi.eve_implementation.protocolService.interestprofiles;


import java.io.File;
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
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(Sessions.class);

	// Factory für die Erzeugung der Events
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static AbstractEvent protocolEvent = eventFactory.createEvent("AtomicEvent");
	
	
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
		
		
		ProtocolAgent protocolagent = (ProtocolAgent) this.getAgent();
		
		switch(event.getType()) {
		case "DocProposal": 
			protocolagent.addProposedDocList(event);			
			break;
		
		case "UserInteraction":
			protocolagent.addClickedDocList(event);
			break;
		
		default:
		for(Property<?> property :event.getProperties()) {
			switch (property.getKey().toLowerCase()) {
				
			case "participant1":
				protocolagent.addUserList(property.getValue().toString());				
				break;
			case "participant2":
				protocolagent.addUserList(property.getValue().toString());
				break;
			case "topic":
				protocolagent.addTopicList(property.getValue().toString());
				break;
			case "sessionstart":
				protocolagent.setSessionStart(property.getValue().toString());
				break;
			case "sessionend":
				protocolagent.setSessionEnd(property.getValue().toString());
				break;
			case "project":
				protocolagent.addProjectList(property.getValue().toString());
			
			case "sessionid":
				protocolagent.setSessionId(property.getValue().toString());
				break;
			default:
				break;
			}
		}
		break;
		} 
		}
		
	
	
	public void CreateNewXMl() {
		System.out.println("XML WIRD ERSTELLT! ");
		ProtocolAgent protokollAgent = (ProtocolAgent) this.getAgent();
		String sessionID = protokollAgent.getSessionId();	
		ArrayList<String> user = (ArrayList<String>) protokollAgent.getUserList();
		ArrayList<String> topics = (ArrayList<String>) protokollAgent.getTopicList();
		ArrayList<String> projects = (ArrayList<String>) protokollAgent.getProjectList();
		ArrayList<AbstractEvent> propDocs =  protokollAgent.getProposedDocList();
		ArrayList<AbstractEvent> clickedDocs =  protokollAgent.getClickedDocList();
		
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			Date now = new Date();
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:S.SSS");
			dateformat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
			String strDate = dateformat.format(now);	
			String strEventDate = dateformat.format(protokollAgent.getSessionStart());
			String endEventDate = dateformat.format(protokollAgent.getSessionEnd());
			
			// Complete new formatting
			// root element
			Element rootElement = doc.createElement("Protocol");
			doc.appendChild(rootElement);


			// id element
			Element id = doc.createElement("ID");
			// id.appendChild(doc.createTextNode("ID ERSTELLEN"));
			//id.appendChild(doc.createTextNode(sessionID.getValue().toString()));
			//id.setValue((sessionID.getValue().toString()));
			id.appendChild(doc.createTextNode(protokollAgent.getSessionId()));
			rootElement.appendChild(id);
			
//			Attr attr = doc.createAttribute("id");
//			attr.setValue((sessionID.getValue().toString()));
//			id.setAttributeNode(attr);
			
			
			// date element
			Element date = doc.createElement("date");
			date.appendChild(doc.createTextNode(strDate));
			rootElement.appendChild(date);
			// starttime element
			Element starttime = doc.createElement("starttime");
			starttime.appendChild(doc.createTextNode(strEventDate));
			rootElement.appendChild(starttime);
			// endtime element
			Element endtime = doc.createElement("endtime");
			endtime.appendChild(doc.createTextNode(endEventDate));
			rootElement.appendChild(endtime);
			// participant1 element
			Element participant1 = doc.createElement("participant1");
			participant1.appendChild(doc.createTextNode(user.get(0)));
			rootElement.appendChild(participant1);		
			
			// participant2 element
			Element participant2 = doc.createElement("participant2");
			participant2.appendChild(doc.createTextNode(user.get(1)));
			rootElement.appendChild(participant2);

			
/*
 * *++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * *++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * *++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * *++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * *++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++			
 */
			// action2 element für Projekte
			Element action1 = doc.createElement("Projekte");
			rootElement.appendChild(action1);
			
			// action element für Topics
			Element action2 = doc.createElement("Gesprächsthemen");
			rootElement.appendChild(action2);
			
			// action2 element für vorgeschlagene Docs 
			Element action3 = doc.createElement("vorgeschlagene Dokumente");
			rootElement.appendChild(action3);
			
			// action2 element für geöffnete Docs
			Element action4 = doc.createElement("geklickte Dokumente");
			rootElement.appendChild(action4);
			

			
	
			for (int i = 0; i < projects.size(); i++) {
			// project element
			Element actionid0 = doc.createElement("Projekt-Nummer: "+i);
			action1.appendChild(actionid0);	
			
						
			// type element
			Element typeP = doc.createElement("Typ");
			typeP.appendChild(doc.createTextNode("Projekt"));
			actionid0.appendChild(typeP);
			
			// project element
			Element project1 = doc.createElement("Projektbezeichnung");
			project1.appendChild(doc.createTextNode(protokollAgent.getProjectList().get(i)));
			actionid0.appendChild(project1);
			action1.appendChild(actionid0);	
			}
			
			/*
			 * 
			 * 
			 * 
			 */
			
			
			for (int i = 0; i < topics.size(); i++) {				
				action2.appendChild(doc.createTextNode(topics.get(i)));				
				}
			/*
			 * 
			 * 
			 * 
			 */
			
			//Weitere dynamische Elemente:
			for (int i = 0; i < propDocs.size(); i++) {
			//vorgeschlagene Dokumente: 
			Element actionid2 = doc.createElement("Dokumenten-Nummer:"+i);
			action3.appendChild(actionid2);
			
			Element timePD = doc.createElement("Zeitstempel");
			timePD.appendChild(doc.createTextNode(propDocs.get(i).getCreationDate().toString()));
			actionid2.appendChild(timePD);
			
			Element docid = doc.createElement("Dokumenten-ID");
			docid.appendChild(doc.createTextNode(propDocs.get(i).getPropertyByKey("FileID").toString()));
			actionid2.appendChild(docid);
				
			// topic element
			Element docname = doc.createElement("Bezeichnung");
			docname.appendChild(doc.createTextNode(propDocs.get(i).getPropertyByKey("Documentname").toString()));
			actionid2.appendChild(docname);
			
			}
			
			//Geöffente Dokumente:
			for (int i = 0; i < clickedDocs.size(); i++) {
			Element actionid3 = doc.createElement("geklicktes Dokument "+i);
			action4.appendChild(actionid3);
			
			Element timeCD = doc.createElement("Zeitstempel");
			timeCD.appendChild(doc.createTextNode(clickedDocs.get(i).getCreationDate().toString()));
			actionid3.appendChild(timeCD);
			
			// type element
			Element typeCD = doc.createElement("geklickt von Nutzer");
			typeCD.appendChild(doc.createTextNode(clickedDocs.get(i).getValueByKey("userID").toString()));
			actionid3.appendChild(typeCD);
			
			// type element
						Element DOCID = doc.createElement("Dokumenten-ID");
						DOCID.appendChild(doc.createTextNode(clickedDocs.get(i).getValueByKey("FileID").toString()));
						actionid3.appendChild(DOCID);
			// topic element
			Element docnameC = doc.createElement("Bezeichnung");
			docnameC.appendChild(doc.createTextNode(clickedDocs.get(i).getPropertyByKey("DocumentName").toString()));
			actionid3.appendChild(docnameC);
			}
			

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			AbstractEvent protocolOutput = eventFactory.createEvent("AtomicEvent");
			protocolOutput.setType("ProtocolEvent");
			protocolOutput.add(new Property<Document>("document", doc));
			protocolOutput.add(new Property<>("DOM", source));
			protokollAgent.send(protocolOutput, "Protocol");
			StreamResult result = new StreamResult(
					new File("C:\\Users\\jonas\\Documents\\Studium\\DevelopmentLab\\Protokoll.xml"));
			transformer.transform(source, result);

			// Output to console for testing
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	}


