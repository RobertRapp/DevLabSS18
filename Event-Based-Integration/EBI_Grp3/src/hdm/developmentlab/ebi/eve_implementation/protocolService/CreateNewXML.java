 package hdm.developmentlab.ebi.eve_implementation.protocolService;
 
 import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
 import javax.xml.parsers.DocumentBuilderFactory;
 import javax.xml.transform.Transformer;
 import javax.xml.transform.TransformerFactory;
 import javax.xml.transform.dom.DOMSource;
 import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
 import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.User;

 
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

			public void CreateNewXMl(long sessionID, AbstractEvent startEvent, AbstractEvent EndEvent, Property<ArrayList<AbstractEvent>> user, Property<ArrayList<AbstractEvent>> topics, AbstractEvent projects, int duration) {
				try {
			         DocumentBuilderFactory dbFactory =
			         DocumentBuilderFactory.newInstance();
			         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			         Document doc = dBuilder.newDocument();
			         
			         // root element
			         Element rootElement = doc.createElement("Protocol");
			         doc.appendChild(rootElement);
			         
			     
			         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			        		 
			         // id element
			         Element id = doc.createElement("id");
//			         id.appendChild(doc.createTextNode("ID ERSTELLEN"));
			         id.appendChild(doc.createTextNode("df"));
			         rootElement.appendChild(id);
			         // date element
			         Element date = doc.createElement("date");
			         date.appendChild(doc.createTextNode("DATE ERSTELLEN"));
			         rootElement.appendChild(date);
			         // starttime element
			         Element starttime = doc.createElement("starttime");
			         starttime.appendChild(doc.createTextNode("starttime ERSTELLEN"));
			         rootElement.appendChild(starttime);
			         // participant element
			         Element participant = doc.createElement("participant");
			         participant.appendChild(doc.createTextNode("participant ERSTELLEN"));
			         rootElement.appendChild(participant);
			         // project element
			         Element project1 = doc.createElement("project");
			         project1.appendChild(doc.createTextNode("project ERSTELLEN"));
			         rootElement.appendChild(project1);
			         // duration element
			         Element duration1 = doc.createElement("duration");
			         duration1.appendChild(doc.createTextNode("duration ERSTELLEN"));
			         rootElement.appendChild(duration1);
			         // action element
			         Element action = doc.createElement("action");
			         action.appendChild(doc.createTextNode("action ERSTELLEN"));
			         rootElement.appendChild(action);
			         
			         
			         // actionid element MUSS skalierbar SEIN
			         Element actionid = doc.createElement("actionid");
			         actionid.appendChild(doc.createTextNode("ACTIONID ERSTELLEN"));
			         action.appendChild(actionid);
			         
			         
			         // loop the actionid child node
			         //Liste die den Inhalt wie "Dokument geöffnet", "Dokument geschlossen", "Applikation aufgerufen",
			         //jegliche Action eben
	
			         
			         
			         
			         // write the content into xml file
			         TransformerFactory transformerFactory = TransformerFactory.newInstance();
			         Transformer transformer = transformerFactory.newTransformer();
			         DOMSource source = new DOMSource(doc);
			         StreamResult result = new StreamResult(new File("C:\\WOHIN.xml"));
			         transformer.transform(source, result);
			         
			         // Output to console for testing
			         StreamResult consoleResult = new StreamResult(System.out);
			         transformer.transform(source, consoleResult);
			      } catch (Exception e) {
			         e.printStackTrace();
				}
			}
	    }		
		