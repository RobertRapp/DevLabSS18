package eventprocessing.consume.kafka.runner.conversation;

/**
 * Für die Kommunikation zwischen Agenten ist es wichtig, zu identifizieren von
 * welchem Agenten die Nachricht gesendet wurde. Damit lässt sich anhand der
 * Prädikatenlogik abbilden, dass der Agent der die Kommunikation gestartet hat,
 * nicht auf seine eigene Aussage reagiert, da es sonst zu einer Endlosschleife
 * kommen kann.
 * 
 * @author IngoT
 *
 */
public enum CommunicationIdProvider {
	INSTANCE;

	// Counter für die Ids
	private int nextId = 0;
	
	// Bei der Instanziierung wird über die Methode die Id abgerufen
	public int getUniqueId() {
		// Wenn der Maximalwert überschritten wurde
        if (nextId < 0) {
        	// Wird eine Exception geworfen
            throw new IllegalStateException("Out of IDs!");
        }
        // Id wird inkrementell erhöht
        incrementId();
        // Gibt die Id zurück
        return nextId;
    }
	
	// Erhöht die Id um den Wert 1
	private synchronized void incrementId() {
		nextId = nextId + 1;
	}
}
