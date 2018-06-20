package eventprocessing.utils.factory;

import java.io.Serializable;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.AbstractPredicate;
import eventprocessing.event.AbstractEvent;

/**
 * Die AbstractFactory stellt alle Methoden bereit, die die konkreten Factories
 * benötigen. Sollten Methoden überflüssig sein, werden sie leer implementiert.
 * 
 * @author IngoT
 *
 */
public abstract class AbstractFactory implements Serializable {

	private static final long serialVersionUID = 613247079704303483L;

	// Für die <code>AgentFactory</code>Factory
	public abstract AbstractAgent createAgent(String agent);

	// Für die <code>EventFactory</code>Factory
	public abstract AbstractEvent createEvent(String event);

	// Für die <code>InterestProfileFactory</code>
	public abstract AbstractInterestProfile createInterestProfile(String interestProfile);

	// Für die <code>PredicateFactory</code>
	public abstract AbstractPredicate createPredicate(String predicate);

}
