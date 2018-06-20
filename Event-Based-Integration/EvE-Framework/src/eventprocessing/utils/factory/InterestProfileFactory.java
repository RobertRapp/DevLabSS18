package eventprocessing.utils.factory;

import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;
import eventprocessing.demo.agents.SensorProcessing.SensorProcessingInterestProfile;
import eventprocessing.demo.agents.TrafficAnalysis.SpeedometerInterestProfile;
import eventprocessing.demo.agents.diagnosis.DiagnosisInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.TextUtils;

/**
 * Die Factory erzeugt beim Aufruf ein neues
 * <code>AbstractInterestProfile</code>
 * 
 * @author IngoT
 *
 */
public final class InterestProfileFactory extends AbstractFactory {

	private static final long serialVersionUID = -1047328067433018833L;
	private static Logger LOGGER = LoggerFactory.getLogger(InterestProfileFactory.class.getName());

	/**
	 * Erzeugt ein <code>AbstractInterestProfile</code> anhand des übergebenen
	 * Strings. Die Methode ist nicht case sensitive und wenn kein passendes
	 * InterestProfile gefunden wurde, wird null zurückgegeben.
	 * 
	 * @param String,
	 *            Name der Klasse des InterestProfiles
	 * @return AbstractInterestProfile, das InterestProfile, welches auf Basis des
	 *         Strings gefunden wurde.
	 */
	@Override
	public AbstractInterestProfile createInterestProfile(String interestProfile) {
		LOGGER.log(Level.INFO, () -> String.format("Try to create a new interestProfile of type: %s", interestProfile));
		// Wenn der übergebene String leer ist
		if (TextUtils.isNullOrEmpty(interestProfile)) {
			LOGGER.log(Level.WARNING, "String is null or empty, no interestProfile can be created!");
			// Wird null zurückgegeben
			return null;
		}
		// Erzeugung der Instanz
		AbstractInterestProfile newInterestProfile = null;
		/**
		 * Auflistung der InterestProfiles die erzeugt werden können.
		 */
		if (interestProfile.equalsIgnoreCase(FactoryValues.INSTANCE.getDiagnosisInterestProfile())) {
			newInterestProfile = new DiagnosisInterestProfile();
		} else if (interestProfile.equalsIgnoreCase(FactoryValues.INSTANCE.getSensorProcessingInterestProfile())) {
			newInterestProfile = new SensorProcessingInterestProfile();
		} else if (interestProfile.equalsIgnoreCase(FactoryValues.INSTANCE.getSpeedometerInterestProfile())) {
			newInterestProfile = new SpeedometerInterestProfile();
			// Wenn der Name zu keinem InterestProfile passt.
		} else {
			// Wenn kein passendes InterestProfile gefunden wurde.
			LOGGER.log(Level.WARNING, "No interestProfile type %s found", interestProfile);
			// Wird null zurückgeliefert
			return null;
		}
		LOGGER.log(Level.FINE, () -> String.format("Returning interestProfile: %s", interestProfile));
		// die neue Instanz wird zurückgegeben
		return newInterestProfile;
	}

	/**
	 * wird hier nicht benötigt
	 */

	@Override
	public AbstractPredicate createPredicate(String predicate) {
		return null;
	}

	@Override
	public AbstractAgent createAgent(String agent) {
		return null;
	}

	@Override
	public AbstractEvent createEvent(String event) {
		return null;
	}
}
