package eventprocessing.demo.agents.diagnosis;

import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.TimeUtils;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;

/**
 * Trifft anhand der Sensoren, Durchschnittsgeschwindigkeit sowie mit
 * "Fachwissen" die Entscheidung, ob eine Geschwindigkeitsüberschreitung
 * stattgefunden hat oder nicht. In beiden Fällen wird ein
 * <code>ComplexEvent</code> erzeugt und auf ein separates Topic weitergeleitet
 * 
 * @author IngoT
 *
 */
public class DiagnosisInterestProfile extends AbstractInterestProfile {

	private static final long serialVersionUID = -6108185466150892913L;
	private static Logger LOGGER = LoggerFactory.getLogger(DiagnosisInterestProfile.class);
	// Factory für die Erzeugung der Events
	private AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doOnReceive(AbstractEvent event) {
		// Erzeugt über die Factory ein neues Event
		AbstractEvent newEvent = eventFactory.createEvent(FactoryValues.INSTANCE.getAtomicEvent());
		
		// Prüfe ob das empfangene Event vom Typ SpeedEvent ist
		if (EventUtils.isType("SpeedEvent", event)) {
//			Property<AbstractEvent> firstEvent = (Property<AbstractEvent>) EventUtils.findPropertyByKey(event, "FirstEvent");
//			Property<AbstractEvent> secondEvent = (Property<AbstractEvent>) EventUtils.findPropertyByKey(event, "SecondEvent");
//			Property<Double> averageSpeed = (Property<Double>) EventUtils.findPropertyByKey(event,
//					"AverageSpeed");
//
//			newEvent.add(firstEvent);
//			newEvent.add(secondEvent);
//			newEvent.add(averageSpeed);
//
//			newEvent.setType("ProblemEvent");
			
			/*
			 * Hier wird das Fachwissen repräsentiert. Wenn die Durchschnittsgeschwindigkeit
			 * über der erlaubten Höchstgeschwindigkeit liegt, muss eine angemessene
			 * Reaktion erfolgen. Momentan wird entsprechend ein Text dem
			 * SpeedMeasurementEvent mitgegeben und auf ein Topic gespeichert.
			 
			if (averageSpeed != null) {
				if (averageSpeed.getValue() > ShowcaseValues.INSTANCE.getSpeedLimit()) {
					newEvent.add(new Property<String>("Severity", ShowcaseValues.INSTANCE.getSeverityNotOk()));
				} else {
					newEvent.add(new Property<String>("Severity", ShowcaseValues.INSTANCE.getSeverityOk()));
				}
			}
			*/
			// Sendet das Event an das Storage Topic
			try {
				getAgent().send(newEvent, ShowcaseValues.INSTANCE.getStorageTopic());
				newEvent.add(new Property<>("REPORT","DIESES EVENT WURDE AUS DIAGNOSIS INTERPROFIL ERSTELLT."));
				getAgent().send(newEvent, "Sessions");
			} catch (NoValidEventException e1) {
				LOGGER.log(Level.WARNING, () -> String.format("%s", newEvent));
			} catch (NoValidTargetTopicException e1) {
				LOGGER.log(Level.WARNING, () -> String.format("%s", ShowcaseValues.INSTANCE.getStorageTopic()));
			}
		}
	}
}
