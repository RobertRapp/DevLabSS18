package eventprocessing.demo.agents.TrafficAnalysis;

import java.util.ArrayList;
import java.util.List;
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
 * Ist für die Messung der Durchschnittsgeschwindigkeit verantwortlich. Für die
 * Erzeugung der Kennzahl werden die zwei Zeitstempel der beiden Sensoren
 * verrechnet.
 * 
 * @author IngoT
 *
 */
public final class SpeedometerInterestProfile extends AbstractInterestProfile {

	private static final long serialVersionUID = 7091510178375032439L;
	private static Logger LOGGER = LoggerFactory.getLogger(SpeedometerInterestProfile.class);
	// Factory für die Erzeugung der Events
	private AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	// Liste mit allen Events, die empfangen werden
	private List<AbstractEvent> events = new ArrayList<AbstractEvent>();

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {
		AbstractEvent pair = merge(event);
		// Wenn ein Paar gefunden wurde
		if (pair != null) {
			/*
			 * Wird ein neues Event erzeugt, dass die ID der beiden Sensoren sowie die
			 * Durchschnittsgeschwindigkeit beinhaltet
			 */
			// Wird das Event versendet für die Diagnose
			try {
				getAgent().send(pair, ShowcaseValues.INSTANCE.getDiagnosisTopic());
			} catch (NoValidEventException e) {
				LOGGER.log(Level.WARNING, () -> String.format("%s", e));
			} catch (NoValidTargetTopicException e) {
				LOGGER.log(Level.WARNING, () -> String.format("%s", ShowcaseValues.INSTANCE.getDiagnosisTopic()));
			}
		}
	}

	/**
	 * Sucht zusammengehörende SensorEvents und fügt diese zusammen. Sollte kein
	 * Pärchen gefunden werden, wird das Event der Liste hinzugefügt. Es findet
	 * keine zeitliche- oder Plausibiltätsprüfung statt, sobald ein passendes Paar
	 * gefunden wird, wird ein Pärchen gebildet.
	 * 
	 * @param receivedEvent,
	 *            vom Interessenprofil entgegengenommen
	 * @return PairTriggeredSensor, mit zwei zusammengehörenden SensorIDs sowie der
	 *         Durchschnittsgeschwindigkeit
	 */
	@SuppressWarnings("unchecked")
	private AbstractEvent merge(AbstractEvent receivedEvent) {
		// der Standort, der gesucht wird
		String location;
		// Abhänging vom empfangenen Event, wird entsprechend das Gegenstück gesucht
		Property<String> locationProperty = (Property<String>) EventUtils.findPropertyByKey(receivedEvent, "Location");
		if (locationProperty.getValue().equals(ShowcaseValues.INSTANCE.getFirstSensorLocation())) {
			location = ShowcaseValues.INSTANCE.getSecondSensorLocation();
		} else {
			location = ShowcaseValues.INSTANCE.getFirstSensorLocation();
		}

		/*
		 * Die Liste wird sequentiell abgearbeitet und wenn ein passendes Gegenstück
		 * gefunden wurde, wird die Suche abggebrochen oder null zurückgegeben
		 */
		AbstractEvent otherEvent = events.stream()
				.filter(listEvent -> EventUtils.findPropertyByKey(listEvent, "Location").getValue().equals(location))
				.findFirst().orElse(null);
		// Wenn kein Gegenstück gefunden wurde
		if (otherEvent == null) {
			// Wird das Event der Liste hinzugefügt
			events.add(receivedEvent);
			// und null zurückgegeben
			return null;
			// Wurde das Gegenstück gefunden
		} else {
			// Werden die beiden Events zusammengeführt
			AbstractEvent pair = eventFactory.createEvent(FactoryValues.INSTANCE.getAtomicEvent());
			pair.setType("SpeedEvent");
			pair.add(new Property<AbstractEvent>("FirstEvent", otherEvent));
			pair.add(new Property<AbstractEvent>("SecondEvent", receivedEvent));
			double averageSpeed = computateSpeed(receivedEvent, otherEvent);
			pair.add(new Property<Double>("AverageSpeed", averageSpeed));
			// das gefundene Gegenstück aus der Liste entfernt
			events.remove(otherEvent);
			// und zurückgegeben.
			return pair;
		}
	}
	
	private double computateSpeed(AbstractEvent first, AbstractEvent second) {
		/*
		 * Rechnet den Zeitstempel in Sekunden um, Achtung: Wenn ein negativer Wert
		 * resultiert, wird das Ergebnis mit -1 multipliziert.
		 */
		int seconds = TimeUtils.getDifferenceInSeconds(first.getCreationDate(), second.getCreationDate());
		int distance = ShowcaseValues.INSTANCE.getDistance();
		double averageSpeed = distance / seconds;

		return averageSpeed;
	}
}
