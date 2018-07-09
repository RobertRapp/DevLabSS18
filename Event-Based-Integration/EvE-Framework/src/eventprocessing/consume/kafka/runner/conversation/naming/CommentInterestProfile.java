package eventprocessing.consume.kafka.runner.conversation.naming;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;

import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.TextUtils;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;

/**
 * Ist für das reagieren einer Konversation in der Verhandlung der Namensgebung.
 * Wenn sich ein Agent im Netzwerk vorstellt mit seinem Namen, muss überprüft
 * werden ob dieser Name bereits von einem anderen Agenten in Benutzung ist.
 * Entsprechend wird eine Antwort in einfacher Form eines Boolean
 * zurückgesendet. Wenn das <code>AnnouncementInterestProfile</code> ein false
 * erhält, wird es sich einen neuen Namen ausdenken und sich erneut vorstellen.
 * 
 * @author IngoT
 *
 */
public final class CommentInterestProfile extends NameInterestProfile {

	private static final long serialVersionUID = 2817439672084885730L;

	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());

	@Override
	public void doOnReceive(AbstractEvent event) {
		
	}

}
