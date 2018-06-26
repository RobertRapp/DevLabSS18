package hdm.developmentlab.ebi.eve_implementation.protocolService.interestprofiles;


import java.util.ArrayList;
import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;
import hdm.developmentlab.ebi.eve_implementation.activityService.interestprofiles.TokenApplicationIP;
import hdm.developmentlab.ebi.eve_implementation.events.SessionEvent;


public class Sessions extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(TokenApplicationIP.class);

	// Factory für die Erzeugung der Events
	private AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private ArrayList<AbstractEvent> tokenEvents = new ArrayList<AbstractEvent>();
	private ArrayList<AbstractEvent> requestEvents = new ArrayList<AbstractEvent>();
	private ArrayList<AbstractEvent> documentProposals = new ArrayList<AbstractEvent>();
	private <AbstractEvent> SessionInfos = new ArrayList<AbstractEvent>();
	
	/**
	 *
	 * In dieser Methode wird ein Gesprächskontext empfangen, sobald ein Gespräch abgeschlossen wird.
	 * Anhand der Informationen die innerhalb des empfangenen Events gespeichert sind, wird ein Protokoll erzeugt.
	 * Das ausgehende Format ist machinell analysiserbar und wird an den Agenten weiter gegeben, damit dieser das Event
	 * auf Google Drive abspeichern kann.
	 *
	 * @param arg0
	 */


	@Override
	protected void doOnReceive(AbstractEvent event) {

		// Erzeugt über die Factory ein neues Event
		AbstractEvent protocolEvent = eventFactory.createEvent(FactoryValues.INSTANCE.getAtomicEvent());
		AbstractEvent tokenEvent = eventFactory.createEvent(FactoryValues.INSTANCE.getAtomicEvent());
		AbstractEvent requestEvent = eventFactory.createEvent(FactoryValues.INSTANCE.getAtomicEvent());
		AbstractEvent documentProposalEvent = eventFactory.createEvent(FactoryValues.INSTANCE.getAtomicEvent());
		AbstractEvent sessionEvent = eventFactory.createEvent(FactoryValues.INSTANCE.getAtomicEvent());

		
		// Prüfe ob das empfangene Event vom Typ SessionEvent ist. Wenn ja, Sessioninfos speichern
		if (EventUtils.isType("SessionInfo", event)) {
			sessionEvent = event;
		} 
		
		// Prüfe ob das empfangene Event vom Typ TokenEvent ist. Wenn ja in TokenListe anfügen 
		if (EventUtils.isType("TokenEvent", event)) {
			tokenEvent = event;
			tokenEvents.add(tokenEvent);
		} 
		
		// Prüfe ob das empfangene Event vom Typ RequestEvent ist. Wenn ja in RequestListe anfügen 
		if (EventUtils.isType("RequestEvents", event)) {
			requestEvent = event;
			requestEvents.add(requestEvent);
		} 		
		
		// Prüfe ob das empfangene Event vom Typ RequestEvent ist. Wenn ja in RequestListe anfügen 
		if (EventUtils.isType("documentEvents", event)) {
			documentProposalEvent = event;
			documentProposals.add(documentProposalEvent);
		} 
		
		//Wenn die Session beendet wurde, werden alle Infos in ProtocolEvent verknüpft und das protocolEvent an DR gesendet
		if (EventUtils.isType("SessionEndEvent", event)) {
			Property<AbstractEvent> sessionInfos = new Property<>();
			Property<ArrayList<AbstractEvent>> tokens = new Property<>();
			Property<ArrayList<AbstractEvent>> requests = new Property<>();
			Property<ArrayList<AbstractEvent>> documents = new Property<>();
			
			//Arraylists mit allen Infos an das protocolEvent als Property anfügen
			sessionInfos.setValue(sessionEvent);
			
			tokens.setValue(tokenEvents);
			protocolEvent.add(tokens);
			
			requests.setValue(requestEvents);
			protocolEvent.add(requests);
			
			documents.setValue(documentProposals);
			protocolEvent.add(documents);
			
			// Sendet das Event an DR (welches Topic ???) 
			try {
				getAgent().send(protocolEvent, "TOPIC??");
			} catch (NoValidEventException e1) {
				java.util.logging.Logger logger = LoggerFactory.getLogger("ProtocolSend");
			} catch (NoValidTargetTopicException e1) {
				java.util.logging.Logger logger = LoggerFactory.getLogger("ProtocolSend");
			}
			
		} 
	
	
	}

}
