package eventprocessing.agent.interestprofile.predicates.statement;

/**
 * Dieses Predicate akzeptiert alle Nachrichten.
 * 
 * @author IngoT
 *
 */
public final class GetEverything extends StatementPredicate{

	private static final long serialVersionUID = -263595410175122524L;

	public GetEverything() {
		super();
		// Setzt das Pattern für die Suche
		setPattern("");
	}
	
}
