package eventprocessing.utils.mapping;

/**
 * Diese Exception soll genutzt werden, wenn ungültige Werte als Argumente übergeben wurden.
 * Bisher findet diese Exception Anwendung in:
 * 
 * <code>MessageMapper</code>
 * 
 * @author IngoT
 *
 */
public class NoValidInputException extends Exception {

	private static final long serialVersionUID = 815885371725353941L;

	public NoValidInputException() {
	}

	public NoValidInputException(String message) {
		super(message);
	}

}
