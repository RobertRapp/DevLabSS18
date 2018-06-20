package eventprocessing.agent.interestprofile.predicates;

import java.io.Serializable;
import java.util.function.Predicate;

import eventprocessing.utils.model.ModelUtils;

/**
 * Predicates werden benötigt, um die Aussage einer Nachricht zu überprüfen. Mit
 * der Prädikatenlogik kann eine Nachricht auf ein bestimmtes Muster geprüft
 * werden und bei zutreffen true zurückliefern, ansonsten false.
 * 
 * Die eigentliche Implementierung der Methode für die Prüfung, befindet sich in
 * den Subklassen.
 * 
 * @author IngoT
 *
 */
public abstract class AbstractPredicate implements Predicate<String>, Serializable {

	private static final long serialVersionUID = 6278499665840237889L;

	private Object[] getSignificantFields() {
		return new Object[] { };
	}
	
	@Override
	public int hashCode() {
		return ModelUtils.hashCodeFor(getSignificantFields());
	}

	@Override
	public boolean equals(Object obj) {
		Boolean result = ModelUtils.quickEquals(this, obj);
		if (result == null) {
			AbstractPredicate that = (AbstractPredicate) obj;
			result = ModelUtils.equalsFor(this.getSignificantFields(), that.getSignificantFields());
		}
		return result;
	}

	@Override
	public String toString() {
		return ModelUtils.toStringFor(this);
	}
	
	/**
     * Throws CloneNotSupportedException as a Event can not be meaningfully
     * cloned. Construct a new Event instead.
     *
     * @throws  CloneNotSupportedException
     *          always
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

}
