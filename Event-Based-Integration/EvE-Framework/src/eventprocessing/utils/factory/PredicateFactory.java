package eventprocessing.utils.factory;

import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;
import eventprocessing.agent.interestprofile.predicates.logical.And;
import eventprocessing.agent.interestprofile.predicates.logical.Not;
import eventprocessing.agent.interestprofile.predicates.logical.Or;
import eventprocessing.agent.interestprofile.predicates.statement.GetEverything;
import eventprocessing.agent.interestprofile.predicates.statement.HasProperty;
import eventprocessing.agent.interestprofile.predicates.statement.HasPropertyContains;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.agent.interestprofile.predicates.statement.IsFromTopic;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.TextUtils;

/**
 * Die Factory erzeugt beim Aufruf ein neues
 * <code>AbstractPredicate</code>
 * 
 * @author IngoT
 *
 */
public final class PredicateFactory extends AbstractFactory {

	private static final long serialVersionUID = -5038445795419783341L;
	private static Logger LOGGER = LoggerFactory.getLogger(InterestProfileFactory.class.getName());

	/**
	 * Erzeugt ein <code>AbstractPredicate</code> anhand des übergebenen
	 * Strings. Die Methode ist nicht case sensitive und wenn kein passendes
	 * AbstractPredicate gefunden wurde, wird null zurückgegeben.
	 * 
	 * @param String,
	 *            Name der Klasse des AbstractPredicate
	 * @return AbstractPredicate, das AbstractPredicate, welcher auf Basis des
	 *         Strings gefunden wurde.
	 */
	@Override
	public AbstractPredicate createPredicate(String predicate) {
		LOGGER.log(Level.INFO, () -> String.format("Try to create a new predicate of type: %s", predicate));
		// Wenn der übergebene String leer ist
		if (TextUtils.isNullOrEmpty(predicate)) {
			LOGGER.log(Level.WARNING, "String is null or empty, no predicate can be created!");
			return null;
		}
		
		AbstractPredicate newPredicate = null;
		
		/**
		 * Auflistung der AbstractPredicate die erzeugt werden können.
		 */
		if (predicate.equalsIgnoreCase(FactoryValues.INSTANCE.getAnd())) {
			newPredicate = new And();
		} else if (predicate.equalsIgnoreCase(FactoryValues.INSTANCE.getNot())) {
			newPredicate = new Not();
		} else if (predicate.equalsIgnoreCase(FactoryValues.INSTANCE.getOr())) {
			newPredicate = new Or();
		} else if (predicate.equalsIgnoreCase(FactoryValues.INSTANCE.getGetEverything())) {
			newPredicate = new GetEverything();
		} else if (predicate.equalsIgnoreCase(FactoryValues.INSTANCE.getHasProperty())) {
			newPredicate = new HasProperty();
		} else if (predicate.equalsIgnoreCase(FactoryValues.INSTANCE.getHasPropertyContains())) {
			newPredicate = new HasPropertyContains();
		} else if (predicate.equalsIgnoreCase(FactoryValues.INSTANCE.getIsEventType())) {
			newPredicate = new IsEventType();
		} else if (predicate.equalsIgnoreCase(FactoryValues.INSTANCE.getIsFromTopic())) {
			newPredicate = new IsFromTopic();
		} 
		// Wenn der Name zu keinem Predicate passt.
		else {
			// Wenn kein passendes Rredicate gefunden wurde.
			LOGGER.log(Level.WARNING, "No predicate type %s found", predicate);
			return null;
		}
		
		LOGGER.log(Level.FINE, () -> String.format("Returning predicate: %s", predicate));
		// die neue Instanz wird zurückgegeben
		return newPredicate;
	}
	
	/**
	 * wird nicht benötigt
	 */

	@Override
	public AbstractAgent createAgent(String agent) {
		return null;
	}

	@Override
	public AbstractEvent createEvent(String event) {
		return null;
	}

	@Override
	public AbstractInterestProfile createInterestProfile(String interestProfile) {
		return null;
	}

}
