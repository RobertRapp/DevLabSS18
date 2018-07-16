package eventprocessing.agent.DocProposal;



import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		System.out.println("In IP von DocProposalIP von Gui");
		System.out.println("Dieses Event wurde empfangen: " + event);	
		
		
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
				switch (pro.getKey()) {
				case "Documentname":
					docname= pro.getValue().toString();
									break;
				case "Author":
					author= pro.getValue().toString();			
									break;
				case "Editor":
					editor= pro.getValue().toString();
					break;
				case "Project":
					project= pro.getValue().toString();
					break;
				case "Filename":
					filename= pro.getValue().toString();
					break;
				case "LastChangeDate":
					lastChangeDate= pro.getValue().toString();
					break;
				case "Category":
					category= pro.getValue().toString();
					break;
					case "FileID":
						fileId= pro.getValue().toString();	
						break;
					case "DocumentType":
					doctype= pro.getValue().toString();
						break;
					case "URL":
						url = pro.getValue().toString();
						break;
				default:
					break;
				}
					
			}
			
		}
		if(EventUtils.findPropertyByKey(event,"Category") == null || EventUtils.findPropertyByKey(event,"Category").getValue().equals("Application")) {
			docListe.add(new Document(fileId, docname, doctype, url , "50", editor, lastChangeDate, category));
		}
		
			
		
		DocProposalAgent dPA = (DocProposalAgent) this.getAgent();
		DocumentProposal currentProposal = dPA.getProposal();
		
		currentProposal.addDocuments(docListe);
		JSONObject json = currentProposal.toJson();
		AbstractEvent jsonDocEvent = eventFactory.createEvent("AtomicEvent");
		jsonDocEvent.setType("JsonDocEvent");
		LOGGER.log(Level.WARNING, "JSON DOC EVENT: "+jsonDocEvent);
		jsonDocEvent.add(new Property<String>("json", json.toString()));
		
		System.out.println("Test DPI: "+jsonDocEvent.getPropertyByKey("json"));
		
		try {
			this.getAgent().send(jsonDocEvent, "Gui");
			LOGGER.log(Level.INFO, () -> String.format("JsonDocEvent sent to topic Gui: " +jsonDocEvent));
		} catch (NoValidEventException e) {
			LOGGER.log(Level.WARNING, () -> String.format("%s", e));
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			LOGGER.log(Level.WARNING, () -> String.format("%s", e));
			e.printStackTrace();
		}
	}

}
