package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;


import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;


public class User extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Empfängt UserEvents und speichert diese Information jeweils im korrekten SessionContext ab.
	 * @param arg0
	 */

	@Override
	protected void doOnReceive(AbstractEvent arg0) {
		/*
		 * 
		 * DR FAKE. Bekommt ein SentenceEvent vom Topic ChunkGeneration
		 */
		
		
		try {
			this.getAgent().send(arg0, "DocProposal");
		} catch (NoValidEventException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
