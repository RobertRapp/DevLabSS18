package hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles;



import java.util.logging.Logger;


import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.TimeUtils;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;

/**
 * Das Interessensprofil @TokenApplicationIP verarbeitet Events vom Typ CalenderEvent, presentation & spreadsheets die vom Topic TokenGeneration konsumiert wurden.
 * Abhängig vom Typ des Events werden den Events der passende Applikations-Link als newProperty angehängt.
 * 
 */

public class TokenApplicationIP extends eventprocessing.agent.interestprofile.AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(TokenApplicationIP.class);
	
	// Factory für die Erzeugung der Events
	private AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	
	@Override
	protected void doOnReceive(AbstractEvent event) { 
	
		 	String type = (String) event.getPropertyByKey("ApplicationType").getValue();
			type.toLowerCase();
			switch (type) {
			case "calendar":
				event.add(new Property<String>("URL","http://docs.google.com/presentation"));	
				break;
			case "presentation":
				event.add(new Property<String>("URL","http://docs.google.com/presentation"));	
				break;
			case "spreadsheets":
				event.add(new Property<String>("URL","http://docs.google.com/spreadsheets"));	
				break;
			default:
				
				event.add(new Property<String>("URL","http://"+type+".google.com"));
				break;
			}
			
				try {
					
					event.setType("DocProposalEvent");
					event.add(new Property<String>("Documentname","Google "+event.getPropertyByKey("ApplicationType").getValue()));
					event.add(new Property<String>("Author","Google"));
					event.add(new Property<String>("Editor",event.getValueByKey("userID").toString()));
					event.add(new Property<String>("Project","Google"));
					event.add(new Property<String>("Filename",(String) event.getValueByKey("ApplicationType")));					;
					event.add(new Property<String>("LastChangeDate","16.07.2018"));
					event.add(new Property<String>("Category","Application"));
					event.add(new Property<String>("FileID", String.valueOf(event.getId())));
					event.add(new Property<String>("DocumentType","Application"));
					
					//Für die GUI Attribute type, docid, category, Author, URL, Filename, Category
					
					
					
					getAgent().send(event, "DocProposal");
				} catch (NoValidEventException e1) {
					LoggerFactory.getLogger("ApplicationSend");
				} catch (NoValidTargetTopicException e1) {
					LoggerFactory.getLogger("ApplicationSend");
				}
				
		}
		
		
		
	
}
