package eventprocessing.agent.interestprofile.predicates.statement;

/**
 * Ein Predicate, welches die Nachrichten auf ein bestimmten Eventtyp prüft und
 * bei erfolgreicher Prüfung true zurückgibt
 * 
 * @author IngoT
 *
 */
public final class IsEventType extends StatementPredicate {

	private static final long serialVersionUID = 5419538270220704311L;

	public IsEventType() {
		this("undefined event type");
	}
	
	public IsEventType(String eventType) {
		super(eventType);
		// Setzt das Pattern für die Suche
		setPattern("\"type\":\"" + this.getFilterAttribute().getProperty() + "\"");
	}
}
