package eventprocessing.utils.factory;

import java.util.logging.Level;
import java.util.logging.Logger;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;
import eventprocessing.demo.agents.SensorProcessing.SensorProcessing;
import eventprocessing.demo.agents.TrafficAnalysis.TrafficAnalysis;
import eventprocessing.demo.agents.diagnosis.Diagnosis;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.TextUtils;

/**
 * Die Factory gibt den entsprechenden Agent zurück. Die Auswahl findet anhand
 * des Namen des Agents statt, welcher per String übergeben wird
 * 
 * @author IngoT
 *
 */
public final class AgentFactory extends AbstractFactory {

	private static final long serialVersionUID = -571422063058526096L;
	private static Logger LOGGER = LoggerFactory.getLogger(AgentFactory.class.getName());

	/**
	 * Erzeugt ein <code>AbstractAgent</code> anhand des übergebenen
	 * Strings. Die Methode ist nicht case sensitive und wenn kein passendes
	 * AbstractAgent gefunden wurde, wird null zurückgegeben.
	 * 
	 * @param String,
	 *            Name der Klasse des AbstractAgent
	 * @return AbstractAgent, der Agent, welcher auf Basis des
	 *         Strings gefunden wurde.
	 */
	@Override
	public AbstractAgent createAgent(String agent) {
		LOGGER.log(Level.FINE, () -> String.format("Try to create a new agent of type: %s", agent));
		// Wenn der übergebene String leer ist
		if (TextUtils.isNullOrEmpty(agent)) {
			LOGGER.log(Level.WARNING, "String is null or empty, no agent can be created!");
			return null;
		}

		AbstractAgent newAgent = null;
		
		/**
		 * Auflistung der Agenten die erzeugt werden können.
		 */
		if (agent.equalsIgnoreCase(FactoryValues.INSTANCE.getAgentDiagnosis())) {
			LOGGER.log(Level.FINE, () -> String.format("Returning agent: %s", agent));
			newAgent = new Diagnosis();
		} else if (agent.equalsIgnoreCase(FactoryValues.INSTANCE.getAgentSensorProcessing())) {
			LOGGER.log(Level.FINE, () -> String.format("Returning agent: %s", agent));
			newAgent = new SensorProcessing();
		} else if (agent.equalsIgnoreCase(FactoryValues.INSTANCE.getAgentTrafficAnalysis())) {
			LOGGER.log(Level.FINE, () -> String.format("Returning agent: %s", agent));
			newAgent = new TrafficAnalysis();
			// Wenn der Name zu keinem Agent passt.
		} else {
			// Wenn kein passendes Event gefunden wurde.
			LOGGER.log(Level.WARNING, "No agent %s found", agent);
			return null;
		}

		LOGGER.log(Level.FINE, () -> String.format("Returning agent: %s", agent));
		// die neue Instanz wird zurückgegeben
		return newAgent;
	}

	/**
	 * werden hier nicht benötigt
	 */

	@Override
	public AbstractEvent createEvent(String event) {
		return null;
	}

	@Override
	public AbstractInterestProfile createInterestProfile(String interestProfile) {
		return null;
	}

	@Override
	public AbstractPredicate createPredicate(String predicate) {
		return null;
	}

}
