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
 * Dieses Interessenprofil führt die Reinigung sowie Filterung durch. Es wird
 * nach fehlerhaften Nachrichten ausschau gehalten, die aus dem Datenstrom
 * gefiltert werden, bevor diese an den <code>TrafficAnalysis</code> Agenten
 * geschickt werden. Fehlerhafte Nachrichten sind beispielsweise die ID vom Wert
 * 0 oder wenn keine Location angegeben wurde.
 * Doppelungen von Nachrichten werden hier nicht geprüft.
 * 
 * @author IngoT
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
//		System.out.println("Event: " + event.getValueByKey("FileID").toString());
//		
//		String docID = event.getValueByKey("FileID").toString();
//		String name = event.getValueByKey("Documentname").toString();
//		String type = event.getValueByKey("DocumentType").toString();
//		String path = event.getValueByKey("URL").toString();
//		String size = "50";
//		String lastEditor  = event.getValueByKey("Editor").toString();		
//		String lastEdit  = event.getValueByKey("LastChangeDate").toString();
//		String category  = event.getValueByKey("Category").toString();
//		
		
		
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
		
//		for(Property<?> p :event.getProperties()) {
//			
//			if(p.getValue() instanceof LinkedHashMap<?, ?>) {
//				
//				System.out.println("ist eine HASHMAP"+p.toString());
//				LinkedHashMap<String, ?> hashmap1 =  (LinkedHashMap<String, ?>) p.getValue();
//				String value = (String) hashmap1.get("type");
//				System.out.println("value:"+value);
//		}else {
			
		
			
		
		DocProposalAgent dPA = (DocProposalAgent) this.getAgent();
		DocumentProposal currentProposal = dPA.getProposal();
		
	//	Document doc = 	new Document(docID, name, type, path, size, lastEditor, lastEdit, category);
	//	docListe.add(doc);
//		for(Property<?> p : event.getProperties()) {
//			if(p.getKey().equals("document")){
//			Document doc = 	new Document(p.getValue().toString());
//			docListe.add(doc);
//			}
//		}
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
//				} catch (NoValidEventException e1) {
//					LOGGER.log(Level.WARNING, () -> String.format("%s", event));
//				} catch (NoValidTargetTopicException e1) {
//					LOGGER.log(Level.WARNING, () -> String.format("%s", "Gui"));
//				}
//	}

}
