package eventprocessing.agent.interestprofile.predicates.statement;

/**
 * Überprüft eine Nachricht, ob diese eine bestimmte Eigenschaft sowie den
 * passenden Wert enthält. Sollte beides zutreffen, wird true zurückgemeldet.
 * 
 * @author IngoT
 *
 */
public final class HasPropertyContains extends StatementPredicate {

	private static final long serialVersionUID = 6246941758057040441L;

	// Muster für die Erkennung der Eigenschaft
	private String propertyPattern = "\"key\":\"" + this.getFilterAttribute().getProperty() + "\",\"value\":";

	public HasPropertyContains() {
		this("empty", "empty");
	}

	/**
	 * Da beide Parameter notwendig sind, wird der Default-Konstruktur
	 * überschrieben.
	 * 
	 * @param property,
	 *            Eigenschaft die gesucht wird
	 * @param value,
	 *            der zugehörige Wert als String
	 */
	public HasPropertyContains(String property, String value) {
		super(property, value);
		// Setzt das Pattern für die Suche
		setPattern(propertyPattern + "\"" + this.getFilterAttribute().getValue() + "\"");
	}

	/**
	 * Da beide Parameter notwendig sind, wird der Default-Konstruktur
	 * überschrieben.
	 * 
	 * @param property,
	 *            Eigenschaft die gesucht wird
	 * @param value,
	 *            der zugehörige Wert als int
	 */
	public HasPropertyContains(String property, int value) {
		super(property, value);
		// Setzt das Pattern für die Suche
		setPattern(propertyPattern + this.getFilterAttribute().getValue());
	}

	/**
	 * Da beide Parameter notwendig sind, wird der Default-Konstruktur
	 * überschrieben.
	 * 
	 * @param property,
	 *            Eigenschaft die gesucht wird
	 * @param value,
	 *            der zugehörige Wert als boolean
	 */
	public HasPropertyContains(String property, boolean value) {
		super(property, value);
		// Setzt das Pattern für die Suche
		setPattern(propertyPattern + this.getFilterAttribute().getValue());
	}

}
