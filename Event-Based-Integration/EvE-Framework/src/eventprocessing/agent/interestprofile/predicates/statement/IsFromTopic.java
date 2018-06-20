package eventprocessing.agent.interestprofile.predicates.statement;

/**
 * Prüft eingehende Nachrichten, ob es von dem angegebenen Topic stammt.
 * 
 * @author IngoT
 *
 */
public final class IsFromTopic extends StatementPredicate {

	private static final long serialVersionUID = -7129743626408345144L;

	public IsFromTopic() {
		this("undefined");
	}
	
	public IsFromTopic(String topic) {
		super(topic);
		// Setzt das Pattern für die Suche
		setPattern("\"source\":\"" + this.getFilterAttribute().getProperty() + "\"");
	}
}
