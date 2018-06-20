package eventprocessing.consume.spark.streaming.window;

public class NoValidWindowSettingsException extends Exception {

	private static final long serialVersionUID = -2221875031108348568L;

	/**
	 * Erzeugt eine neue NoValidWindowSettingsException
	 */
	public NoValidWindowSettingsException() {
	}

	/**
	 * Erzeugt eine neue NoValidWindowSettingsException mit einer detailierten Fehlermeldung
	 * 
	 * @param message
	 *            von der NoValidWindowSettingsException ausgegeben wird.
	 */
	public NoValidWindowSettingsException(String message) {
		super(message);
	}
}
