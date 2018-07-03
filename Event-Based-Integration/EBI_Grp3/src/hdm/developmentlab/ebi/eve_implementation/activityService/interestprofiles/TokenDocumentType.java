package hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.speechTokens.tokenizer.Chunker;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.TimeUtils;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;
import eventprocessing.utils.model.OWLResultUtils;
import javafx.event.Event;
import sun.java2d.pipe.OutlineTextRenderer;


public class TokenDocumentType extends eventprocessing.agent.interestprofile.AbstractInterestProfile {

	/**
	 * Verarbeitet DocumentEvents, SessionContext, PersonEvent, ProjectEvent, UncertainEvent
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(TokenDocumentType.class);
	

	// Factory für die Erzeugung der Events
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private AbstractEvent requestEvent = eventFactory.createEvent("AtomicEvent");
	private static AbstractEvent lastSessionContextEvent = eventFactory.createEvent("AtomicEvent");
	
	
	/**
	 * Empfängt Tokentypen und leitet damit eine neue Dokumentenvorschlagsanfrage ein.
	 * @param arg0
	 */

	@Override
	protected void doOnReceive(AbstractEvent event) {
		System.out.println("In TokenDocType "+event);
		
		AbstractEvent output = eventFactory.createEvent("AtomicEvent");
		output.setType("DocRequestEvent");
		if(lastSessionContextEvent.getProperties().size() < 1) {
			lastSessionContextEvent.add(new Property<String>("teilnehmer1","Vanessa_Keller"));
			lastSessionContextEvent.add(new Property<String>("teilnehmer2","Jennifer_Tran"));
		}
		if(EventUtils.hasProperty(lastSessionContextEvent, "teilnehmer1")) output.add(new Property<String>("participant1", (String) lastSessionContextEvent.getValueByKey("teilnehmer1")));
		if(EventUtils.hasProperty(lastSessionContextEvent, "teilnehmer2")) output.add(new Property<String>("participant2", (String) lastSessionContextEvent.getValueByKey("teilnehmer2")));
		
		if(event.getType().equalsIgnoreCase("uncertainEvent")) {
			System.out.println("EVENT VOM NICHT FIXEN TYP"+event);
			String key = null;
			String value= null;
			for(Property<?> p : event.getProperties()) {
				switch (p.getKey().toLowerCase()) {
				case "type":
					key = (String)p.getValue();
				break;
				case "name": 
					value = (String) p.getValue();
				case "keyword":
					//{ "type": "literal" , "value": "cost; expenses; expense; costs;" }
					
					JSONObject js = new JSONObject((String) p.getValue());
					output.add(new Property<String>("keyword", js.get("value").toString().split(";")[0]));			
					break;				
					default:
					//output.add(p);
					break;					
				}
				output.add(new Property<String>(key, value));
			}				
		}else {
						
			for(Property<?> p : event.getProperties()) {
				switch (p.getKey().toLowerCase()) {
				case "person":					
					LinkedHashMap<String, ?> hashmap =  (LinkedHashMap<String, ?>) p.getValue();
					output.addOrReplace(new Property<>("keyword", hashmap.get("value").toString().split(";")[0]));
					output.addOrReplace(new Property<>("person", hashmap.get("key").toString()));
				break;
				case "project": 
						
					LinkedHashMap<String, ?> hashmap1 =  (LinkedHashMap<String, ?>) p.getValue();
					output.addOrReplace(new Property<>("keyword", hashmap1.get("value").toString().split(";")[0]));
					output.addOrReplace(new Property<>("project", hashmap1.get("key").toString()));
				default:
						if(p.getValue() instanceof LinkedHashMap<?, ?>) {					
							LinkedHashMap<String, ?> hashmap2 =  (LinkedHashMap<String, ?>) p.getValue();
							output.addOrReplace(new Property<>("keyword", hashmap2.get("value").toString().split(";")[0]));	
					break;					
				}
				}
		}
		}
		System.out.println("Der letzte Session COntext ist: " + lastSessionContextEvent);
		if(EventUtils.hasProperty(lastSessionContextEvent, "project") && !EventUtils.hasProperty(output, "project")) output.add(new Property<String>("project", (String) lastSessionContextEvent.getValueByKey("project")));
		
		try {
			System.err.println("OUTPUT EVENT AN DR GRP"+output);
			this.getAgent().send(output, "DocRequest");
		} catch (NoValidEventException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
		
	}



