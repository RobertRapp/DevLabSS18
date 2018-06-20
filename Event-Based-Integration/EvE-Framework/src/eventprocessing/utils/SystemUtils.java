package eventprocessing.utils;

/**
 * Hilfsklasse für das Setzen und Abrufen der Systemeigenschaften.
 * 
 * @author IngoT
 *
 */
public final class SystemUtils {

	private SystemUtils() {

	}

	/**
	 * Gibt den Line Separator zurück. Wird für die textuellen Ausgaben genutzt, um
	 * einen Zeilenumbruch zu erzeugen. Verwendung sinnvoll, da sich der line
	 * separator bei den Betriebssystemen unterscheidet.
	 * 
	 * @return line separator des aktuellen Betriebssystems
	 */
	static public String getLineSeparator() {
		return System.getProperty("line.separator");
	}

	/**
	 * Gibt den File Separator zurück. Wird für die Angabe von Verzeichnissen verwendet.
	 * Bei Windows ist es ein \ und bei Unix ein /
	 * 
	 * @return file separator des aktuellen Betriebssystems
	 */
	static public String getFileSeparator() {
		return System.getProperty("file.separator");
	}

	/**
	 * Der Projektpfad wird über die Systemeigenschaft abgerufen.
	 * 
	 * @return Der Projektpfad
	 */
	static public String getProjectPath() {
		return System.getProperty("user.dir");
	}

	/**
	 * Gibt das Verzeichnis von Hadoop zurück.
	 * 
	 * @return das Homedirectory von Hadoop
	 */
	static public String getHadoopHomeDirectory() {
		return "hadoop.home.dir";
	}

	/**
	 * Setzt eine Eigenschaft
	 * 
	 * @param property,
	 *            Name der Eigenschaft
	 * @param value,
	 *            Wert für die Eigenschaft
	 */
	static public void setProperty(String property, String value) {
		// Nur wenn beide Werte nicht null sind, wird es in das System geschrieben.
		if (!TextUtils.isNullOrEmpty(property) && !TextUtils.isNullOrEmpty(value)) {
			System.setProperty(property, value);
		}
	}

	/**
	 * prüft ob es sich bei dem Betriebssystem um Windows handelt
	 * 
	 * @returnm true wenn es sich um Windows handelt, false wenn es sich nicht um
	 *          Windows handelt
	 */
	static public boolean isWindows() {
		return ((System.getProperty("os.name")).toLowerCase().contains("windows")) ? true : false;
	}
}
