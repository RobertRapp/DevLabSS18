package eventprocessing.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Dient der Verarbeitung von JSON. Findet in der Funktion <code>IsJSON</code> Anwendung.
 * 
 * @author IngoT
 *
 */
public final class JsonUtils {
	private JsonUtils() {
	}

	// Prüft ob ein String ein gültiges JSON-Format vorweist
	public static boolean isValidJson(String jsonInString) {
		// Es wird versucht ein String in die JSON-Struktur zu überführen
		try {
			// Der ObjectMapper wird erzeugt
			final ObjectMapper mapper = new ObjectMapper();
			// Der String wird in das JSON-Format überführt
			mapper.readTree(jsonInString);
			// Sollte kein Fehler aufkommen, dann gebe true zurück
			return true;
		// Sollte eine Überführung nicht möglich sein, gebe false zurück
		} catch (IOException e) {
			return false;
		}
	}
}
