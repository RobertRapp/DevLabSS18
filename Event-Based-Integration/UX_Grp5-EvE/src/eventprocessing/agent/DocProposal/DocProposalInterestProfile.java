package eventprocessing.agent.DocProposal;



import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.Document;
import eventprocessing.utils.DocumentProposal;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;

/**
 *Das DocProposalInterestProfile fügt die Properties des DocProposalEvents bzw. des Dokumentenvorschlags
 *der globalen ArrayList vom Typ Document hinzu. In dieser ArrayListe werden alle Dokumentenvorschläge 
 *einer Session abgelegt. Die Klasse Document verwaltet hierbei die maximale Anzahl der Dokumentenvorschläge
 *innerhalb der ArrayList die in einer Session möglich sind. Aus dieser ArrayList wird ein JSON-String generiert,
 *welcher an das Topic Gui gesendet wird.
 * 
 */
public class DocProposalInterestProfile extends AbstractInterestProfile {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8699861536301449664L;
	private static Logger LOGGER = LoggerFactory.getLogger(DocProposalInterestProfile.class);
	private AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {
	//
	//	
		
		if(event.getType().equalsIgnoreCase("SessionEndEvent")) {
			
			DocProposalAgent dpA = (DocProposalAgent)this.getAgent();
			dpA.getProposal().clearProposal();
		} else {
			
//		
//		if(event.getProperties().size() == 26 || event.getProperties().size() == 0) {
//			return;
//		}
		
		ArrayList<Document> docListe = new ArrayList<Document>(); //property.getValue() L9inked Hashmap
		
		String docname = "";
		String editor= "";
		String author= "";
		String project= "";
		String filename= "";
		String lastChangeDate= "";
		String category= "";
		String fileId= "";
		String doctype= "";
		String url = "";
		for(Property<?> pro : event.getProperties()) {
			if (pro.getValue() instanceof LinkedHashMap) {	
				docListe.add(new Document((LinkedHashMap<?, ?>) pro.getValue()));	
			}else {				
				switch (pro.getKey().toLowerCase()) {
				case "documentname":
					docname= pro.getValue().toString();
									break;
				case "author":
					author= pro.getValue().toString();			
									break;
				case "editor":
					editor= pro.getValue().toString();
					break;
				case "project":
					project= pro.getValue().toString();
					break;
				case "filename":
					filename= pro.getValue().toString();
					break;
				case "lastchangedate":
					lastChangeDate= pro.getValue().toString();
					break;
				case "category":
					category= pro.getValue().toString();
					break;
					case "fileid":
						fileId= pro.getValue().toString();	
						break;
					case "documenttype":
					doctype= pro.getValue().toString();
						break;
					case "url":
						url = pro.getValue().toString();
						break;
				default:
					break;
				}
					
			}
			}
		
		//if(EventUtils.findPropertyByKey(event,"Category").getValue().equals("Application")|| EventUtils.hasProperty(event,"Category")) {
			
			docListe.add(new Document(fileId, docname, doctype, url , "50", editor, lastChangeDate, category));
			
	//	}	
		
		
		DocProposalAgent dPA = (DocProposalAgent) this.getAgent();
		
		
		
		dPA.getProposal().addDocuments(docListe);
		JSONObject json = toJson(dPA.getProposal().getDocuments(), dPA.getProposal().getCategories());
		AbstractEvent jsonDocEvent = eventFactory.createEvent("AtomicEvent");
		jsonDocEvent.setType("JsonDocEvent");
		
		jsonDocEvent.add(new Property<String>("json", json.toString()));
		LOGGER.log(Level.WARNING, "JSON DOC EVENT: "+jsonDocEvent);
		//
		
		try {
			this.getAgent().send(jsonDocEvent, "Gui");
			LOGGER.log(Level.INFO, () -> String.format("JsonDocEvent sent to topic Gui: " +jsonDocEvent));
		} catch (NoValidEventException | NoValidTargetTopicException e) {
			LOGGER.log(Level.WARNING, () -> String.format("%s", e));
			e.printStackTrace();
		}
	}
}
	
	public JSONObject toJson(ArrayList<Document> documents, ArrayList<String> categories) {
		JSONObject docProposalJson = new JSONObject();
		JSONObject requestJson = new JSONObject();
		
//		JSONObject categoryInformation= new JSONObject();
		JSONObject document= new JSONObject();
		// Array f�r alle
		JSONArray childrenAllCategory = new JSONArray();
		
		for (String category : categories) {
			JSONArray docsOneCategory = new JSONArray();
			JSONObject childrenOneCategory = new JSONObject();
			
			for (Document doc : documents) {
				if (doc.getCategorie().equalsIgnoreCase(category)) {
					document= new JSONObject();
					document.put("Ersteller", doc.getLastEditor());
					document.put("size", 50);
					document.put("name", doc.getName());
					document.put("path", doc.getPath());
					document.put("fontcolor", "white");
					document.put("docID", doc.getDocID());
					document.put("category", doc.getCategorie());
					document.put("color", doc.getColor(doc.getType()));
					docsOneCategory.put(document);
					
				}
			}	
				//JSONObject categoryInformation= new JSONObject();
				childrenOneCategory.put("children", docsOneCategory);
				childrenOneCategory.put("fontcolor", "white");
				childrenOneCategory.put("color", "#B768F6");
				childrenOneCategory.put("name", category);
				//childrenOneCategory.put(categoryInformation);
				childrenAllCategory.put(childrenOneCategory);
				
		}
		docProposalJson.put("children", childrenAllCategory);
		docProposalJson.put("fontcolor", "black");
		docProposalJson.put("color", "white");
		docProposalJson.put("name", "docProposal");
		requestJson.put("docProposal", docProposalJson);
		requestJson.put("type", "newDocProposal");
		return requestJson;
		
		
		
	}

}
