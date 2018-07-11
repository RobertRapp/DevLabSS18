package eventprocessing.consume.spark.streaming.window;

import java.io.Serializable;

import eventprocessing.consume.spark.streaming.SparkContextValues;

/**
 * Basisklasse für Window-Operationen. Jedes Fenster besitzt eine Länge und ein
 * Interval. Das Interval gibt an, ab welchen Zeitpunkt ein neuer DStream
 * erzeugt wird, mit den Elementen die sich im Fenster befinden.
 * 
 * @author IngoT
 *
 */
public abstract class AbstractWindow implements Serializable {

	private static final long serialVersionUID = -7294978826636921815L;

	// Länge des Fensters
	private int windowLength = 0;
	// Intervall für die Erzeugung des neuen DStream
	private int slideInterval = 0;

	/**
	 * Angabe der Fensterlänge, idealerweise ist sie ein vielfaches der batch
	 * duration. Wenn kein slideInterval angegeben wird, wird die Fensterlänge als
	 * Interval genutzt.
	 * 
	 * @throws NoValidWindowSettingsException
	 *             wenn die Fensterlänge oder der Intervall kleiner als der
	 *             Batchintervall ist.
	 */
	public AbstractWindow() throws NoValidWindowSettingsException {
		this(0);
	}

	/**
	 * Angabe der Fensterlänge, idealerweise ist sie ein vielfaches der batch
	 * duration. Wenn kein slideInterval angegeben wird, wird die Fensterlänge als
	 * Interval genutzt.
	 * 
	 * @param windowLength
	 *            Länge des Fensters
	 *
	 * @throws NoValidWindowSettingsException
	 *             wenn die Fensterlänge oder der Intervall kleiner als der
	 *             Batchintervall ist.
	 */
	public AbstractWindow(int windowLength) throws NoValidWindowSettingsException {
		this(windowLength, windowLength);
	}

	/**
	 * Angabe der Fensterlänge und Intervalls. Wenn die Argumente kleiner sind als
	 * die batch duration wird eine Exception geworfen.
	 * 
	 * @param windowLength
	 *            Länge des Fensters
	 * @param slideInterval
	 *            Intervall für die Erzeugung des neuen DStreams
	 * 
	 * @throws NoValidWindowSettingsException
	 *             wenn die Fensterlänge oder der Intervall kleiner als der
	 *             Batchintervall ist.
	 */
	public AbstractWindow(int windowLength, int slideInterval) throws NoValidWindowSettingsException {
		// Prüfung ob die Argumente größer als die batch duration sind.
		if (windowLength < SparkContextValues.INSTANCE.getBatchDuration()
				&& slideInterval < SparkContextValues.INSTANCE.getBatchDuration()) {
			// Sonst wirf eine Exception
			throw new NoValidWindowSettingsException();
		} else {
			// Zuweisung der Argumente
			this.windowLength = windowLength;
			this.slideInterval = slideInterval;
		}
	}

	/**
	 * ruft die Länge des Fensters ab
	 * 
	 * @return int windowLength, Länge des Fensters.
	 */
	public int getWindowLength() {
		return windowLength;
	}

	/**
	 * Ruft das Interval ab.
	 * 
	 * @return int slideInterval, Interval für die Erzeugung des neuen DStreams
	 */
	public int getSlideInterval() {
		return slideInterval;
	}

}
