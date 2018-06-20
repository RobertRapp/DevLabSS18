package eventprocessing.agent.interestprofile.predicates.statement;

/**
 * Erzeugt ein Predicate, der überprüft ob eine Eigenschaft in einer Nachricht
 * enthalten ist. Sollte diese vorhanden sein, wird true zurückgegeben.
 * 
 * @author IngoT
 *
 */
public final class HasProperty extends StatementPredicate {

	private static final long serialVersionUID = 131709645341890901L;

	public HasProperty() {
		this("empty");
	}
	
	/**
	 * Da nur die Eigenschaft von Interesse ist, wird für den Wert ein default gesetzt.
	 * 
	 * @param property, die von Interesse ist
	 */
	public HasProperty(String property) {
		super(property);
		// Setzt das Pattern für die Suche
		setPattern("\"key\":\"" + this.getFilterAttribute().getProperty() + "\":");
	}
}
