package eventprocessing.consume.kafka.runner.logging;

/**
 * Beinhaltet die Werte für die Nutzung des Logging der Agenten. Damit muss
 * bei einer Änderungen nur eine Stelle angepasst werden.
 * 
 * @author Ingo
 *
 */
public enum LoggingValues {

	INSTANCE;
	// Das Topic, über den kommuniziert wird
	private String loggingTopic = null;

	// Setzen der Values
	private LoggingValues() {
		// Setzt den Namen für das Topic
		this.loggingTopic = "Logging";
	}

	/**
	 * Die Klassen rufen diese Methode auf und erhalten den Namen des Topics zurück.
	 * 
	 * @return the topicName
	 */
	public String getLoggingTopic() {
		return this.loggingTopic;
	}

}
