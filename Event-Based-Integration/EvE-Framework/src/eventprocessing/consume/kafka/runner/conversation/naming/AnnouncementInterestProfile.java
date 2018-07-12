//package eventprocessing.consume.kafka.runner.conversation.naming;
//
//import eventprocessing.agent.NoValidEventException;
//import eventprocessing.agent.NoValidTargetTopicException;
//import eventprocessing.agent.dispatch.NoValidInterestProfileException;
//import eventprocessing.agent.interestprofile.predicates.statement.HasPropertyContains;
//import eventprocessing.event.AbstractEvent;
//import eventprocessing.utils.UIDUtils;
//import eventprocessing.utils.factory.AbstractFactory;
//import eventprocessing.utils.factory.FactoryProducer;
//import eventprocessing.utils.factory.FactoryValues;
//
///**
// * Nachdem sich der Agent im Netzwerk vorgestellt hat, wartet er auf die
// * Reaktionen der anderen Agenten. Sollte bereits ein anderer Agent denselben
// * Namen tragen, denkt sich der Agent einen neuen Namen aus und stellt sich
// * erneut im Netzwerk vor.
// * 
// * @author IngoT
// *
// */
//public final class AnnouncementInterestProfile extends NameInterestProfile {
//
//	private static final long serialVersionUID = 5662705068066670896L;
//	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
//
//	@Override
//	public void doOnReceive(AbstractEvent event) {
//		if (event != null) {
//			CommentEvent cEvent = (CommentEvent) event;
//			// Wenn der Name im Netzwerk bereits vorhanden ist
//			if (!cEvent.isAnswer()) {
//				// Wird ein neues Event erzeugt für die Bekanntmachung
//				AnnouncementEvent newAnnouncement = (AnnouncementEvent) eventFactory
//						.createEvent(FactoryValues.INSTANCE.getAnnouncementEvent());
//
//				String agentName = generateID();
//				this.getAgent().setId(agentName);
//				newAnnouncement.setAgentName(agentName);
//				// Wichtig: die KommunikationsId muss beibehalten werden
//				newAnnouncement.setId(cEvent.getConversationId());
//				try {
//					publishAgentName(newAnnouncement);
//				} catch (NoValidInterestProfileException | NoValidEventException | NoValidTargetTopicException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}
//
//	/**
//	 * 
//	 */
//	public void init(AnnouncementEvent event) {
//
//		this.add(new HasPropertyContains("conversationId", Integer.toString(event.getConversationId())));
//		String agentName = generateID();
//		this.getAgent().setId(agentName);
//		event.setAgentName(agentName);
//		try {
//			publishAgentName(event);
//		} catch (NoValidInterestProfileException | NoValidEventException | NoValidTargetTopicException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * Der Agent gibt die ID dem Netzwerk bekannt, indem es die ID in das Topic
//	 * "NegotationName" gesendet wird. Die anderen Agenten im Netzwerk können sich
//	 * die Nachricht abrufen und überprüfen, ob diese ID bereits in Verwendung ist.
//	 * 
//	 * @throws NoValidInterestProfileException
//	 * @throws NoValidTargetTopicException
//	 * @throws NoValidEventException
//	 * 
//	 */
//	private void publishAgentName(AnnouncementEvent event)
//			throws NoValidInterestProfileException, NoValidEventException, NoValidTargetTopicException {
//
//		// Das Event wird über den Agent an das entsprechende Topic gesendet
//		this.getAgent().send(event, ConversationValues.INSTANCE.getConversationTopic());
//	}
//
//	/**
//	 * Aus dem Klassennamen sowie einer zufällig generierten UID wird eine 120
//	 * Zeichen langer String in Form von Hexdezimal erzeugt.
//	 * 
//	 * @return String, der die neue ID des Ageten repräsentiert
//	 */
//	private String generateID() {
//		return this.getAgent().getClass().getSimpleName() + " : " + UIDUtils.getUID();
//	}
//
//}
