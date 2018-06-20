package eventprocessing.utils.factory;

import java.util.logging.Logger;

/**
 * Für die Erzeugung der Logger
 * 
 * @author IngoT
 *
 */
public final class LoggerFactory {

	/*
	 * Erzeugt den Loger und gibt ihn einen individuellen Namen
	 */
	public static Logger getLogger(String name) {
		return Logger.getLogger(name);
	}

	/*
	 * Der Logger erhält den selben Namen wie die Klasse, die ihn aufruft.
	 */
	public static Logger getLogger(Class<?> name) {
		return Logger.getLogger(name.getSimpleName());
	}
}
