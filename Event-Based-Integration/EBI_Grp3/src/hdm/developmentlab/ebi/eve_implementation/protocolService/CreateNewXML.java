package hdm.developmentlab.ebi.eve_implementation.protocolService;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.model.EventUtils;

/*
 * 
 * Property<AbstractEvent> sessionInfos = new Property<>();
		Property<ArrayList<AbstractEvent>> tokens = new Property<>();
		Property<ArrayList<AbstractEvent>> requests = new Property<>();
		Property<ArrayList<AbstractEvent>> documents = new Property<>();
 * 
 * 
 */

// TODO Auto-generated constructor stub

public class CreateNewXML {

	public void CreateNewXMl(AbstractEvent protocolEvent) {
		System.out.println("XML WIRD ERSTELLT! ");
		Property<Long> sessionID = (Property<Long>) EventUtils.findPropertyByKey(protocolEvent, "sessionID");
		Property<AbstractEvent> startEvent = (Property<AbstractEvent>) EventUtils.findPropertyByKey(protocolEvent,
				"sessionStart");
		Property<AbstractEvent> endEvent = (Property<AbstractEvent>) EventUtils.findPropertyByKey(protocolEvent,
				"sessionEnd");
		Property<ArrayList> user = (Property<ArrayList>) EventUtils.findPropertyByKey(protocolEvent, "user");
		Property<ArrayList> topics = (Property<ArrayList>) EventUtils.findPropertyByKey(protocolEvent, "topics");
		Property<ArrayList> projects = (Property<ArrayList>) EventUtils.findPropertyByKey(protocolEvent, "projects");
		Property<Integer> duration = (Property<Integer>) EventUtils.findPropertyByKey(protocolEvent, "duration");

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date datum = new Date();
			
			

			// Complete new formatting
			// root element
			Element rootElement = doc.createElement("Protocol");
			doc.appendChild(rootElement);


			// id element
			Element id = doc.createElement("ID");
			// id.appendChild(doc.createTextNode("ID ERSTELLEN"));
			//id.appendChild(doc.createTextNode(sessionID.getValue().toString()));
			rootElement.appendChild(id);
			
			Attr attr = doc.createAttribute("id");
			attr.setValue((sessionID.getValue().toString()));
			id.setAttributeNode(attr);
			
			
			// date element
			Element date = doc.createElement("date");
			date.appendChild(doc.createTextNode(datum.toString()));
			rootElement.appendChild(date);
			// starttime element
			Element starttime = doc.createElement("starttime");
			starttime.appendChild(doc.createTextNode(startEvent.getValue().getCreationDate().toString()));
			rootElement.appendChild(starttime);
			// endtime element
			Element endtime = doc.createElement("endtime");
			endtime.appendChild(doc.createTextNode(endEvent.getValue().getCreationDate().toString()));
			rootElement.appendChild(endtime);
			// participant1 element
			Element participant1 = doc.createElement("participant1");
			participant1.appendChild(doc.createTextNode(user.getValue().get(0).toString()));
			rootElement.appendChild(participant1);
			// participant2 element
			Element participant2 = doc.createElement("participant2");
			participant2.appendChild(doc.createTextNode(user.getValue().get(1).toString()));
			rootElement.appendChild(participant2);
			// project element
			Element project1 = doc.createElement("project");
			project1.appendChild(doc.createTextNode(projects.getValue().get(0).toString()));
			rootElement.appendChild(project1);
			// duration element
			Element duration1 = doc.createElement("duration");
			duration1.appendChild(doc.createTextNode(duration.toString()));
			rootElement.appendChild(duration1);
			
			// action element
			Element action = doc.createElement("actions");
			// action.appendChild(doc.createTextNode("action ERSTELLEN"));
			rootElement.appendChild(action);
			
			Attr attr2 = doc.createAttribute("ActionID");
			attr2.setValue((topics.getValue().toString()));
			action.setAttributeNode(attr2);

			// loop the actionid child node
			// Liste die den Inhalt wie "Dokument geöffnet", "Dokument geschlossen",
			// "Applikation aufgerufen",
			// jegliche Action eben
			for (int i = 0; i < projects.getValue().lastIndexOf(topics); i++) {
				Element actionid = doc.createElement("ActionID " + i);
				actionid.appendChild(doc.createTextNode(topics.getValue().get(i).toString()));
				action.appendChild(actionid);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(
					new File("C:\\Users\\jonas\\Documents\\Studium\\Development Lab\\Protokoll.xml"));
			transformer.transform(source, result);

			// Output to console for testing
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}