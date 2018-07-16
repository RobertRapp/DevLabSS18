package eventprocessing.agent.GuiAgent;

import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.demo.ShowcaseValues;
import eventprocessing.events.DocProposalEvent;
import eventprocessing.events.GuiEvent;
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
import values.GUIValues;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.demo.events.SensorEvent;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.TextUtils;
import eventprocessing.utils.factory.LoggerFactory;

/**
 * Dieses Interessenprofil führt die Reinigung sowie Filterung durch. Es wird
 * nach fehlerhaften Nachrichten ausschau gehalten, die aus dem Datenstrom
 * gefiltert werden, bevor diese an den <code>GuAgent</code> Agenten
 * geschickt werden.
 * 
 * @author IngoT
 *
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
			
		
		GuiAgent guiAgent = (GuiAgent) this.getAgent();
		LOGGER.log(Level.WARNING, "Ich will dieses tolle Event fatal broadcasten"+event);
		guiAgent.broadcast(event.getPropertyByKey("json").getValue().toString());
		
		//GuiEvent e = (GuiEvent) event;
		//LOGGER.log(Level.WARNING, "doOnReceiveGuiIP");
		/*		
		try {
					getAgent().send(event, "Gui");
					System.out.println("getAgent");
				} catch (NoValidEventException e1) {
					System.out.println("NogetAgent");
					LOGGER.log(Level.WARNING, () -> String.format("%s", event));
				} catch (NoValidTargetTopicException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", "Gui"));
				
				}
				
				System.out.println("Consumer");
				Property<?> name=EventUtils.findPropertyByKey(event, "name");
				Property<?> type=EventUtils.findPropertyByKey(event, "type");
				Property<?> path=EventUtils.findPropertyByKey(event, "path");
				Property<?> lastEditor=EventUtils.findPropertyByKey(event, "lastEditor");
				Property<?> lastEdit=EventUtils.findPropertyByKey(event, "lastEdit");
				Property<?> docProposalId=EventUtils.findPropertyByKey(event, "docProposalId");
				Property<?> category=EventUtils.findPropertyByKey(event, "category");
				System.out.println(name);
				System.out.println(type);
				System.out.println(path);
				System.out.println(lastEditor);
				System.out.println(lastEdit);
				System.out.println(docProposalId);
				System.out.println(category);
			
		
		
	/*	
		// Prüfung ob es vom Typ SensorEvent ist.
		if (event instanceof SensorEvent) {
			// Wenn ja, cast.
			SensorEvent e = (SensorEvent) event;
			// Aussortierung von fehlerhaften Sensorwerten.
			if (e.getSensorID() != 0 && !TextUtils.isNullOrEmpty(e.getLocation())) {
				try {
					// Das erzeugte Event wird über den Agenten an das Topic "TrafficData" versendet
					getAgent().send(e, ShowcaseValues.INSTANCE.getTrafficDataTopic());
				} catch (NoValidEventException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", e));
				} catch (NoValidTargetTopicException e1) {
					LOGGER.log(Level.WARNING, () -> String.format("%s", e));
				}
			}
		} */
	}

}
