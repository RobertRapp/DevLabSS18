package hdm.developmentlab.ebi.eve_implementation.protocolService;

import java.awt.Event;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

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
		Property<ArrayList<AbstractEvent>> user = (Property<ArrayList<AbstractEvent>>) EventUtils.findPropertyByKey(protocolEvent, "user");
		Property<ArrayList<AbstractEvent>> topics = (Property<ArrayList<AbstractEvent>>) EventUtils.findPropertyByKey(protocolEvent, "topic");
		Property<ArrayList<AbstractEvent>> projects = (Property<ArrayList<AbstractEvent>>) EventUtils.findPropertyByKey(protocolEvent, "project");
		Property<ArrayList<AbstractEvent>> propDocs = (Property<ArrayList<AbstractEvent>>) EventUtils.findPropertyByKey(protocolEvent, "propDoc");
		Property<ArrayList<AbstractEvent>> clickedDocs = (Property<ArrayList<AbstractEvent>>) EventUtils.findPropertyByKey(protocolEvent, "clickedDoc");
		Property<Integer> duration = (Property<Integer>) EventUtils.findPropertyByKey(protocolEvent, "duration");
		
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			Date now = new Date();
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:S.SSS");
			dateformat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
			String strDate = dateformat.format(now);	
			String strEventDate = dateformat.format(startEvent.getValue().getCreationDate());
			String endEventDate = dateformat.format(endEvent.getValue().getCreationDate());
			
			// Complete new formatting
			// root element
			Element rootElement = doc.createElement("Protocol");
			doc.appendChild(rootElement);


			// id element
			Element id = doc.createElement("ID");
			// id.appendChild(doc.createTextNode("ID ERSTELLEN"));
			//id.appendChild(doc.createTextNode(sessionID.getValue().toString()));
			//id.setValue((sessionID.getValue().toString()));
			id.appendChild(doc.createTextNode(sessionID.getValue().toString()));
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
			participant1.appendChild(doc.createTextNode(user.getValue().get(0).getPropertyByKey("name").getValue().toString()));
			rootElement.appendChild(participant1);		
			
			// participant2 element
			Element participant2 = doc.createElement("participant2");
			participant2.appendChild(doc.createTextNode(user.getValue().get(1).getPropertyByKey("name").getValue().toString()));
			rootElement.appendChild(participant2);

			// duration element
			Element duration1 = doc.createElement("duration");
			duration1.appendChild(doc.createTextNode(duration.getValue().toString() + " seconds"));
			rootElement.appendChild(duration1);
/*
 * *++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * *++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * *++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * *++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * *++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++			
 */
			// action2 element für Projekte
			Element action1 = doc.createElement("projects");
			rootElement.appendChild(action1);
			
			// action element für Topics
			Element action2 = doc.createElement("topics");
			rootElement.appendChild(action2);
			
			// action2 element für vorgeschlagene Docs 
			Element action3 = doc.createElement("proposedDocs");
			rootElement.appendChild(action3);
			
			// action2 element für geöffnete Docs
			Element action4 = doc.createElement("clickedDocs");
			rootElement.appendChild(action4);
			

			
	
			for (int i = 0; i < projects.getValue().size(); i++) {
			// project element
			Element actionid0 = doc.createElement("project"+i);
			action1.appendChild(actionid0);	
			
			// time element
			Element timeP = doc.createElement("time");
			timeP.appendChild(doc.createTextNode(projects.getValue().get(i).getCreationDate().toString()));
			actionid0.appendChild(timeP);	
			
			// type element
			Element typeP = doc.createElement("type");
			typeP.appendChild(doc.createTextNode(projects.getKey().substring(0, 1).toUpperCase() + projects.getKey().substring(1)));
			actionid0.appendChild(typeP);
			
			// project element
			Element project1 = doc.createElement("project");
			project1.appendChild(doc.createTextNode(projects.getValue().get(i).getPropertyByKey("project").getValue().toString()));
			actionid0.appendChild(project1);
			
			}
			// Schleife für mehrere ActionIDs 
			//actionid element
			for (int i = 0; i < topics.getValue().size(); i++) {
			Element actionid = doc.createElement("topic"+i);
			action2.appendChild(actionid);
			
			// time element
			Element time = doc.createElement("time");
			time.appendChild(doc.createTextNode(topics.getValue().get(i).getCreationDate().toString()));
			actionid.appendChild(time);
			
			// type element
			Element type = doc.createElement("type");
			type.appendChild(doc.createTextNode(topics.getKey().substring(0, 1).toUpperCase() + topics.getKey().substring(1)));
			actionid.appendChild(type);
			
			// topic element
			Element topic = doc.createElement("topic");
			topic.appendChild(doc.createTextNode(topics.getValue().get(i).getPropertyByKey("topic").getValue().toString()));
			actionid.appendChild(topic);
			
			}
			//Weitere dynamische Elemente:
			for (int i = 0; i < propDocs.getValue().size(); i++) {
			//vorgeschlagene Dokumente: 
			Element actionid2 = doc.createElement("propDoc"+i);
			action3.appendChild(actionid2);
			
			Element timePD = doc.createElement("time");
			timePD.appendChild(doc.createTextNode(propDocs.getValue().get(i).getCreationDate().toString()));
			actionid2.appendChild(timePD);
			
			// type element
			Element typePD = doc.createElement("type");
			typePD.appendChild(doc.createTextNode(propDocs.getKey().substring(0, 1).toUpperCase() + propDocs.getKey().substring(1)));
			actionid2.appendChild(typePD);
			
			// topic element
			Element docname = doc.createElement("docName");
			docname.appendChild(doc.createTextNode(propDocs.getValue().get(i).getPropertyByKey("dokumentName").getValue().toString()));
			actionid2.appendChild(docname);
			
			}
			
			//Geöffente Dokumente:
			for (int i = 0; i < clickedDocs.getValue().size(); i++) {
			Element actionid3 = doc.createElement("clickedDocs"+i);
			action4.appendChild(actionid3);
			
			Element timeCD = doc.createElement("time");
			timeCD.appendChild(doc.createTextNode(clickedDocs.getValue().get(i).getCreationDate().toString()));
			actionid3.appendChild(timeCD);
			
			// type element
			Element typeCD = doc.createElement("type");
			typeCD.appendChild(doc.createTextNode(clickedDocs.getKey().substring(0, 1).toUpperCase() + propDocs.getKey().substring(1)));
			actionid3.appendChild(typeCD);
			
			// topic element
			Element docnameC = doc.createElement("docName");
			docnameC.appendChild(doc.createTextNode(clickedDocs.getValue().get(i).getPropertyByKey("dokumentName").getValue().toString()));
			actionid3.appendChild(docnameC);
			
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