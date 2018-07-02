package eventprocessing.utils.model;

import java.util.ArrayList;
import java.util.Iterator;

import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;

import org.json.JSONObject;

public final class OWLResultUtils {

	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	
	
	private OWLResultUtils() {
		
	}
	/**
	 * Just a helper method to match the value we are looking for in the semantic data and the json value that was found
	 * @param lookup the lookup value that we want to match to the semantic is. Will be matched to a string
	 * @param jsonValue the value that was found in the semantic data
	 * @return the semantic JSON Object as a String that was found
	 * always when no semantic match was found, the return value of this function is null
	 */
	public static ArrayList<Property<String>> convertBindingElementInPropertySet(String owlResultObjectString) {
		
		ArrayList<Property<String>> properties = new ArrayList<>();
		
		JSONObject object = new JSONObject(owlResultObjectString);
		//System.out.println("Object: "+object);
		String docName;	
		//for (int i = 0; i < values.length(); i++) {			
			
				 for (Iterator<String> iterator = object.keys(); iterator.hasNext();) {
					String key = (String) iterator.next();					
					JSONObject singleobjekt = object.getJSONObject(key);
					if(!singleobjekt.has("value")) continue;
					String value = String.valueOf(singleobjekt.get("value")); //{ "type": "uri" , "value": "http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostPlan"
					if(singleobjekt.has("type") && singleobjekt.get("type").toString().equalsIgnoreCase("uri")) {
						value = value.split("#")[1];
					}
					properties.add(new Property<String>(key, value));					
				}
					//System.out.println(properties.toString());
	return properties;
	}
	
public static AbstractEvent getDocProposalEventOfBindingElement(ArrayList<String> bindingList) {
	
		AbstractEvent docProposalEvent = eventFactory.createEvent("AtomicEvent");
		for(String binding : bindingList) {
			
			ArrayList<Property<String>> documentProps = convertBindingElementInPropertySet(binding);
			Property<ArrayList<Property<String>>> document = new Property<ArrayList<Property<String>>>("Document", documentProps);
			docProposalEvent.add(document);
		}
		
	return docProposalEvent;
	}
	
	
	
		//}
		


		
		
		
	

}
