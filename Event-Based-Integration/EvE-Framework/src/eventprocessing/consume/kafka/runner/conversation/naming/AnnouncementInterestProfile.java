package eventprocessing.consume.kafka.runner.conversation.naming;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.predicates.statement.HasPropertyContains;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.UIDUtils;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;

/**
 * Nachdem sich der Agent im Netzwerk vorgestellt hat, wartet er auf die
 * Reaktionen der anderen Agenten. Sollte bereits ein anderer Agent denselben
 * Namen tragen, denkt sich der Agent einen neuen Namen aus und stellt sich
 * erneut im Netzwerk vor.
 * 
 * @author IngoT
 *
 */
public final class AnnouncementInterestProfile extends NameInterestProfile {

	private static final long serialVersionUID = 5662705068066670896L;
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());

	@Override
	public void doOnReceive(AbstractEvent event) {
	}
}
