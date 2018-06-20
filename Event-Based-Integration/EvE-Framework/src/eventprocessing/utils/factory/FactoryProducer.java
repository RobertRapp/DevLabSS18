package eventprocessing.utils.factory;

/**
 * Diese Klasse gibt die gewünschte Factory zurück, die erstellt werden soll.
 * 
 * @author IngoT
 *
 */
public final class FactoryProducer {

	private FactoryProducer() {
	}

	/**
	 * Gibt die Instanz der Factory zurück, die angefragt wird.
	 * 
	 * @param choice,
	 *            Name der gewünschten Factory
	 * @return die Factory, die auf choice passt.
	 */
	public static AbstractFactory getFactory(String choice) {

		/**
		 * Die Groß-/Kleinschreibung wird ignoriert und die entsprechende Factory wird
		 * zurückgegeben.
		 */
		if (choice.equalsIgnoreCase(FactoryValues.INSTANCE.getAgentFactory())) {
			return new AgentFactory();

		} else if (choice.equalsIgnoreCase(FactoryValues.INSTANCE.getEventFactory())) {
			return new EventFactory();
		}

		// Wenn keine Factory zum übergebenen Namen passt.
		return null;
	}
}
