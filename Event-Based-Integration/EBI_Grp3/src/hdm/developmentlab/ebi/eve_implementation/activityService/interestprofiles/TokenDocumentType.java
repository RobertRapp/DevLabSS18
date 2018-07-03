package hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles;


import java.util.ArrayList;
import java.util.logging.Logger;

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
		AbstractEvent output = eventFactory.createEvent("AtomicEvent");
		output.setType("DocRequestEvent");
		output.add(new Property<String>("teilnehmer1", (String) lastSessionContextEvent.getValueByKey("teilnehmer1")));
		output.add(new Property<String>("teilnehmer2", (String) lastSessionContextEvent.getValueByKey("teilnehmer2")));
		
		Chunker chunks = new Chunker();
		if (EventUtils.isType("ProjectEvent", event)) {	
			ArrayList<String> list = chunks.readChunks();
			for (int i = 0; i < list.size(); i++) {
				ArrayList<String> list1 = (ArrayList<String>) chunks.readSemanticOfChunk(list.get(i));
				list1.get(i).contains("project");
				System.out.println("Hier projekt wert: ");
				System.out.println(list1.get(i).contains("project"));
			}
			chunks.printList();
		}
		
		if (EventUtils.isType("SessionContext", event)) {		
			lastSessionContextEvent = event;
			lastSessionContextEvent.setType("SessionContextEvent");
			lastSessionContextEvent.setCreationDate(event.getCreationDate());
		}else {
			
			ArrayList<Property<?>> allProperties = new ArrayList<>();
			Chunker chunker = new Chunker();
			chunker.parseArrayList((ArrayList<Object>)event.getPropertyByKey("chunks").getValue());
			for(int i = 0; i < chunker.size(); i++ ) {
				String semantic = (String) chunker.getSemanticAt(i);
				allProperties.addAll(OWLResultUtils.convertBindingElementInPropertySet(semantic));			
			}
				
			AbstractEvent randomevent = eventFactory.createEvent("AtomicEvent");
			allProperties.forEach((prop) -> randomevent.add(prop));
			String s = (String) randomevent.getValueByKey("Classname");
			switch (s) {
			case "Project":
				output.add(new Property<>("project", randomevent.getValueByKey("Instanzname")));
				break;
			case "Person":
				output.add(new Property<>("person", randomevent.getValueByKey("Instanzname")));
				break;

			default:
				
				output.add(new Property<>("keyword", randomevent.getValueByKey("Keyword").toString().split(";")[0]));
				break;
			}			
			}
					
		System.out.println("Received" + event);
		//Wird ein neues SessionContextEvent empfangen, so wird dies als letzter und damit aktuellster SessionContext abgespeichert
		if (EventUtils.isType("SessionContext", event)) {
			System.out.println("ES IST EIN SESSIONCONTEXT");
			lastSessionContextEvent = event;
			lastSessionContextEvent.setType("SessionContextEvent");
			lastSessionContextEvent.setCreationDate(event.getCreationDate());
		}
		
	
		//Hier if mit Zeitabprüfug und session context auf 30 sekunden oder so; TokenEvent ist es eigentlich schon
		// Prüfe ob das empfangene Event vom Typ TokenEvent ist undeinen Dokumententyp beinhaltet 
		if (EventUtils.isType("TokenEvent", event) && EventUtils.hasProperty(event, "topic")) {
			System.out.println("ES ist ein TOPIC EVENT!!!! ");
				requestEvent = event; 
				requestEvent.setType("RequestEvent");
				requestEvent.setCreationDate(event.getCreationDate());
				
				System.out.println("RequestEvent: " + requestEvent);
				System.out.println("SC Event: " + lastSessionContextEvent);
				
				//Wenn letzter SessionContext existiert und nicht zu weit zurück liegt, wird das Tokenevent (bei Bedarf) um den aktuellen SC angereichert
				if(lastSessionContextEvent != null && TimeUtils.getDifferenceInSeconds(requestEvent.getCreationDate(), lastSessionContextEvent.getCreationDate()) <= 10) {
					System.out.println("ES LIEGT IN DER ZEIT!! ");
				
					//Enthält TokenEvent keine Property namens project (oder eine der folgenden Namen) oder ist der jeweilige Wert gleich null, so wird das Projekt des SC angehängt 
					if(EventUtils.hasProperty(requestEvent, "project")) {
						requestEvent.add(EventUtils.findPropertyByKey(lastSessionContextEvent, "project"));
					} else 
						if(EventUtils.findPropertyByKey(requestEvent, "project").getValue() == null) {
							requestEvent.remove(EventUtils.findPropertyByKey(requestEvent, "project"));
							requestEvent.add(EventUtils.findPropertyByKey(lastSessionContextEvent, "project"));
						
					}
					
//					if(EventUtils.hasProperty(requestEvent, "timereference")) {
//						requestEvent.add(EventUtils.findPropertyByKey(lastSessionContextEvent, "timereference"));
//
//						
//					} else 
//						if(EventUtils.findPropertyByKey(requestEvent, "timereference").getValue() == null) {
//							requestEvent.remove(EventUtils.findPropertyByKey(requestEvent, "timereference"));
//							requestEvent.add(EventUtils.findPropertyByKey(lastSessionContextEvent, "timereference"));
//							System.out.println("ACHSO2");
//					}
//					
					if(EventUtils.hasProperty(requestEvent, "latestActivity")) {
						requestEvent.add(EventUtils.findPropertyByKey(lastSessionContextEvent, "latestActivity"));
					} else 
						if(EventUtils.findPropertyByKey(requestEvent, "latestActivity").getValue() == null) {
							requestEvent.remove(EventUtils.findPropertyByKey(lastSessionContextEvent, "latestActivity"));
							requestEvent.add(EventUtils.findPropertyByKey(lastSessionContextEvent, "latestActivity"));
					}
					
					if(EventUtils.hasProperty(requestEvent, "users")) {
						requestEvent.add(EventUtils.findPropertyByKey(lastSessionContextEvent, "users"));
					} else 
						if(EventUtils.findPropertyByKey(requestEvent, "users").getValue() == null) {
							requestEvent.remove(EventUtils.findPropertyByKey(requestEvent, "users"));
							requestEvent.add(EventUtils.findPropertyByKey(lastSessionContextEvent, "users"));
						
					}
					
					if(EventUtils.hasProperty(requestEvent, "sessionId")) {
						requestEvent.add(EventUtils.findPropertyByKey(lastSessionContextEvent, "sessionId"));
					} else 
						if(EventUtils.findPropertyByKey(requestEvent, "sessionId").getValue() == null) {
							requestEvent.remove(EventUtils.findPropertyByKey(requestEvent, "sessionId"));
							requestEvent.add(EventUtils.findPropertyByKey(lastSessionContextEvent, "sessionId"));
					}
				
				
				}
				
				// Sendet das Event an DR (welches Topic ???) 
				try {
					getAgent().send(requestEvent, "DocRequest");
				} catch (NoValidEventException e1) {
					LoggerFactory.getLogger("RequestSend");
				} catch (NoValidTargetTopicException e1) {
					LoggerFactory.getLogger("RequestSend");
				}
				
		}
		
		
		
	}
		
	}



