package eventprocessing.event;

import org.apache.hadoop.util.hash.Hash;

/**
 * Jedes <code>AbstractEvent</code> das erzeugt wird, erhält eine eindeutige ID.
 * Bei jeder Instanziierung eines Events wird der Zähler inkrementell erhöht.
 * 
 * @author IngoT
 *
 */
public enum EventIdProvider {
	INSTANCE;
	
	// Counter für die Ids
	private long nextId = 0;
	
	
	// Bei der Instanziierung wird über die Methode die Id abgerufen
	public long getUniqueId() {
		String s = String.valueOf(nextId);
		
        // Id wird inkrementell erhöht
        incrementId();
        // Gibt die Id zurück
        return s.hashCode() + nextId;
    }
	
	// Erhöht die Id um den Wert 1
	private synchronized void incrementId() {
		nextId = nextId + 1;
	}
}
