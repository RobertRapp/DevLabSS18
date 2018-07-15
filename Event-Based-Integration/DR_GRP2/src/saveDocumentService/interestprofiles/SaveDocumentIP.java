package saveDocumentService.interestprofiles;


import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;



import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.TimeUtils;
import eventprocessing.utils.model.EventUtils;

public class SaveDocumentIP extends AbstractInterestProfile{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8849435572415656139L;

	@Override
	protected void doOnReceive(AbstractEvent event) { System.out.println(this.getClass().getSimpleName() + " : Event angekommen "+event.getType()+" - " + TimeUtils.getCurrentTime());
		System.out.println("XML WIRD ERSTELLT! ");
		System.out.println("Topiclist des Protokolls:" + EventUtils.findPropertyByKey(event, "Topics").getValue());
		System.out.println("Userlist des Protokolls:" + EventUtils.findPropertyByKey(event, "User").getValue());
		System.out.println("Projectlist des Protokolls:" + EventUtils.findPropertyByKey(event, "Projects").getValue());
		System.out.println("Timestamp der ankommt: " + EventUtils.findPropertyByKey(event, "SessionStart").getValue());
		
		
		String strSessionStart = (String) EventUtils.findPropertyByKey(event, "SessionStart").getValue();
		String strSessionEnd = (String) EventUtils.findPropertyByKey(event, "SessionEnd").getValue();
		Date datesessionstart = null;
		try {
			datesessionstart = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).parse(strSessionStart.replaceAll("Z$", "+0000"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Neues dateformat start: " + datesessionstart);
		
		Date datesessionend = null;
		try {
			datesessionend = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).parse(strSessionEnd.replaceAll("Z$", "+0000"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		String duration = String.valueOf(EventUtils.findPropertyByKey(event, "Duration").getValue());
		duration.concat(" seconds");
		ArrayList<String> user = (ArrayList<String>) EventUtils.findPropertyByKey(event, "User").getValue();
		ArrayList<String> projects = (ArrayList<String>) EventUtils.findPropertyByKey(event, "Projects").getValue();
		ArrayList<String> topics = (ArrayList<String>) EventUtils.findPropertyByKey(event, "Topics").getValue();
		ArrayList<AbstractEvent> propDocs = (ArrayList<AbstractEvent>) EventUtils.findPropertyByKey(event, "ProposedDocs").getValue();
		ArrayList<AbstractEvent> clickedDocs = (ArrayList<AbstractEvent>) EventUtils.findPropertyByKey(event, "ClickedDocs").getValue();
		
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			Date now = new Date();
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:S.SSS");
			SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd");
			dateformat.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
			dateformat2.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
			
			String strDate = dateformat2.format(now);	
			String strStartDate = dateformat.format(datesessionstart);
			String strEndDate = dateformat.format(datesessionend);
			// Complete new formatting
			// root element
			Element rootElement = doc.createElement("Protocol");
			doc.appendChild(rootElement);


			// id element
			Element id = doc.createElement("ID");
			// id.appendChild(doc.createTextNode("ID ERSTELLEN"));
			//id.appendChild(doc.createTextNode(sessionID.getValue().toString()));
			//id.setValue((sessionID.getValue().toString()));
			id.appendChild(doc.createTextNode(EventUtils.findPropertyByKey(event, "SessionID").getValue().toString()));
			rootElement.appendChild(id);
			

			// date element
			Element date = doc.createElement("date");
			date.appendChild(doc.createTextNode(strDate));
			rootElement.appendChild(date);
			// starttime element
			Element starttime = doc.createElement("starttime");
			starttime.appendChild(doc.createTextNode(strStartDate));
			rootElement.appendChild(starttime);
			// endtime element
			Element endtime = doc.createElement("endtime");
			endtime.appendChild(doc.createTextNode(strEndDate));
			rootElement.appendChild(endtime);
			// duration element
			Element durationEl = doc.createElement("duration");
			durationEl.appendChild(doc.createTextNode(duration.toString()));
			rootElement.appendChild(durationEl);
			// participant1 element
			Element participant1 = doc.createElement("participant1");
			participant1.appendChild(doc.createTextNode(user.get(0)));
			rootElement.appendChild(participant1);		
			
			// participant2 element
			Element participant2 = doc.createElement("participant2");
			participant2.appendChild(doc.createTextNode(user.get(1)));
			rootElement.appendChild(participant2);


			// action2 element für Projekte
			Element action1 = doc.createElement("Projekte");
			rootElement.appendChild(action1);
			
			// action element für Topics
			Element action2 = doc.createElement("Gesprächsthemen");
			rootElement.appendChild(action2);
			
			// action2 element für vorgeschlagene Docs 
			Element action3 = doc.createElement("vorgeschlageneDokumente");
			rootElement.appendChild(action3);
			
			// action2 element für geöffnete Docs
			Element action4 = doc.createElement("geklickteDokumente");
			rootElement.appendChild(action4);
			

			
	
			for (int i = 0; i < projects.size(); i++) {
			// project element
			Element actionid0 = doc.createElement("Projekt-Nummer:"+i);
			action1.appendChild(actionid0);	
						
			// type element
			Element typeP = doc.createElement("Typ");
			typeP.appendChild(doc.createTextNode("Projekt"));
			actionid0.appendChild(typeP);
			
			// project element
			Element project1 = doc.createElement("Projektbezeichnung");
			project1.appendChild(doc.createTextNode(projects.get(i)));
			actionid0.appendChild(project1);
			action1.appendChild(actionid0);	
			}
			
			for (int i = 0; i < topics.size(); i++) {				
				action2.appendChild(doc.createTextNode(topics.get(i)));				
				}
			
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
			System.out.println("Protokoll wird abgelegt");
			StreamResult result = new StreamResult(
					new File("C:\\Users\\jonas\\Documents\\Studium\\DevelopmentLab\\Protokoll.xml"));
			transformer.transform(source, result);
 
			
			//Output to console for testing
			System.out.println("Protokoll Result als Stream");
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	}
	
		
	  

