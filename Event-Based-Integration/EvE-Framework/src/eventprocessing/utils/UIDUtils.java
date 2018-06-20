package eventprocessing.utils;

import java.util.UUID;

/**
 * Die Klasse erzeugt eine UID.
 * Sie wird für die Vergebung der ID für Agenten verwendet.
 * 
 * @author IngoT
 *
 */
public final class UIDUtils {

	// Instanz wird nicht benötigt
	private UIDUtils() {}
	
	/**
	 * erzeugt eine neue UID und gibt diese zurück
	 * 
	 * @return UID
	 */
	public static String getUID() {
		return UUID.randomUUID().toString();
	}
}
