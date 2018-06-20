package eventprocessing.consume.spark.streaming.window;

/**
 * Realisierung für die Nutzung der Windows-Funktionalität.
 * 
 * @author IngoT
 *
 */
public class Window extends AbstractWindow {

	private static final long serialVersionUID = -2007522502413148340L;

	/**
	 * Angabe der Fensterlänge, idealerweise ist sie ein vielfaches der batch
	 * duration.
	 * 
	 * @param windowLength,
	 *            Länge des Fensters
	 * 
	 * @throws NoValidWindowSettingsException,
	 *             wenn die Fensterlänge oder der Intervall kleiner als der
	 *             Batchintervall ist.
	 */
	public Window(int windowLength) throws NoValidWindowSettingsException {
		super(windowLength);
	}

	/**
	 * Angabe der Fensterlänge und Intervalls. Wenn die Argumente kleiner sind als
	 * die batch duration wird eine Exception geworfen.
	 * 
	 * @param windowLength,
	 *            Länge des Fensters
	 * @param slideInterval,
	 *            Intervall für die Erzeugung des neuen DStreams
	 * 
	 * @throws NoValidWindowSettingsException,
	 *             wenn die Fensterlänge oder der Intervall kleiner als der
	 *             Batchintervall ist.
	 */
	public Window(int windowLength, int slideInterval) throws NoValidWindowSettingsException {
		super(windowLength, slideInterval);
	}
}
