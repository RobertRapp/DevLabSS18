package eventprocessing.agent.DocProposal;

import java.util.ArrayList;
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
		
		ArrayList<Document> docListe = new ArrayList<Document>();
		DocProposal dPA = (DocProposal) this.getAgent();
		DocumentProposal currentProposal = dPA.getProposal();
		for(Property<?> p : event.getProperties()) {
			if(p.getKey().equals("document")){
			Document doc = 	new Document(p.getValue().toString());
			docListe.add(doc);
			}
		}
		currentProposal.addDocuments(docListe);
		JSONObject json = currentProposal.toJson();
		AbstractEvent jsonDocEvent = eventFactory.createEvent("AtomicEvent");
		jsonDocEvent.setType("JsonDocEvent");
		LOGGER.log(Level.WARNING, "JSON DOC EVENT: "+jsonDocEvent);
		jsonDocEvent.add(new Property<String>("json", json.toString()));
		
		
		try {
			getAgent().send(jsonDocEvent, "Gui");
		} catch (NoValidEventException e) {
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
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
