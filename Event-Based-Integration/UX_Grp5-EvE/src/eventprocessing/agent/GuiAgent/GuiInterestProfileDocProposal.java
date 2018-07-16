package eventprocessing.agent.GuiAgent;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import eventprocessing.utils.*;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.events.DocProposalEvent;
import eventprocessing.events.SessionEndEvent;
import eventprocessing.demo.events.SensorEvent;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.TextUtils;
import eventprocessing.utils.factory.LoggerFactory;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;


import eventprocessing.demo.ShowcaseValues;
import eventprocessing.demo.events.SensorEvent;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.TextUtils;
import eventprocessing.utils.factory.LoggerFactory;

/**
 * In diesem Interessensprofil wird der Dokumentenvorschlag als JSON-String über die Websocket an
 * die GUI gesendet. Der JSON-String ist in dem Format, wie er für die D3-Visualisierung verwendet wird.
 */
public class GuiInterestProfileDocProposal extends AbstractInterestProfile {

	private static final long serialVersionUID = -210735813565569965L;
	private static Logger LOGGER = LoggerFactory.getLogger(GuiInterestProfileDocProposal.class);

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {
		
		JSONObject returnJson = new JSONObject(event.getValueByKey("json").toString());
		
		returnJson.put("type", "newDocProposal");
		System.out.println("return JSON "+returnJson.toString());
		Websocket.broadcast(returnJson.toString());
		//GuiAgent.addAndPublishDocsToProposalList(newDocuments);
		LOGGER.log(Level.INFO, "Event sent to Websocket: "+event);
		
		
// Testdaten
		
//		ArrayList<Document> newDocuments = new ArrayList<Document>();
//		
//		Document document2 = new Document("001", "docZwei", "Powerpoint",
//				"https://drive.google.com/open?id=17LFPYVxbBQK5Wdt5SrNgo3_iYxXJiXfXq6jYoLl8H4Y", "50mb", "PAtrick",
//				"01.01.1991", "Projektplan");
	
		//Document d = new Document(document2);
//		Document d = new Document(event.getValueByKey("document").toString());
//		double dou = Math.random();
//		if(dou > 0.2) {
//			document2.setType("Excel");
//			newDocuments.add(document2);
//		}else {
//			System.out.println("MATH RANDOM "+dou);
//		}
//		
//		newDocuments.add(document2);

	}

}
