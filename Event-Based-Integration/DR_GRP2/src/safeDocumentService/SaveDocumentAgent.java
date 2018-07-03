package safeDocumentService;

import documentProposalService.interestprofiles.DocumentProposalIP;
import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;

public class SaveDocumentAgent extends AbstractAgent{

	private static final long serialVersionUID = 1L;

	protected void doOnInit() {
		
		this.setId("SafeDocumentAgent");
		/*
		 * Angabe der Topics, die konsumiert werden sollen. Es können mehrere Topics
		 * angegeben werden.
		 */
		try {
			this.add("XX");
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}
		
		/*
		 * Fügt dem Agenten ein InteressenProfil hinzu. Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		try {
			AbstractInterestProfile ip = new DocumentProposalIP();
			ip.add(new IsEventType("XX"));
			this.add(ip);
		
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
	}

	
	
}
