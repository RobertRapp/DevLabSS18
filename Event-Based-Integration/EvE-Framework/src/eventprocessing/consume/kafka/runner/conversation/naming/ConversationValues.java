package eventprocessing.consume.kafka.runner.conversation.naming;

/**
 * Beinhaltet die Werte für die Nutzung der Namensgebung des Agenten. Damit muss
 * bei einer Änderungen nur eine Stelle angepasst werden.
 * 
 * @author Ingo
 *
 */
public enum ConversationValues {

	INSTANCE;
	// Das Topic, über den kommuniziert wird
	private String conversationTopic = null;

	// Setzen der Values
	private ConversationValues() {
		// Setzt den Namen für das Topic
		this.conversationTopic = "Conversation";
	}

	/**
	 * Die Klassen rufen diese Methode auf und erhalten den Namen des Topics zurück.
	 * 
	 * @return the topicName
	 */
	public String getConversationTopic() {
		return this.conversationTopic;
	}

}
