package eventprocessing.events;

import java.util.Formatter;

import eventprocessing.event.AtomicEvent;
import eventprocessing.utils.SystemUtils;

/**
 * Ein Ereignis, welches unmittelbar von der Sensorik im Verkehrsnetz
 * ausgesendet werden. Jedes Ereignis enthält Rohdaten, die für die weitere
 * Verarbeitung nicht modifiziert wurden. Als elementare Daten eines jeden
 * Sensors lassen sich folgende Eigenschaften identizifizieren: Location = Wo
 * befindet sich der Sensor (Position); SensorID = eindeutige ID für jeden
 * Sensor
 * 
 * @author IngoT
 *
 */
public class GuiEvent extends AtomicEvent {

	private static final long serialVersionUID = -2818823998212079914L;



}
