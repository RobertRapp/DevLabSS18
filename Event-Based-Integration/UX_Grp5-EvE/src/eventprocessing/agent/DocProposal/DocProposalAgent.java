package eventprocessing.agent.DocProposal;

import eventprocessing.agent.AbstractAgent;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.GetEverything;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.demo.agents.diagnosis.ConsumerSettingsDiagnosis;
import eventprocessing.demo.agents.diagnosis.ProducerSettingsDiagnosis;
import eventprocessing.produce.kafka.ProducerSettings;
import eventprocessing.utils.DocumentProposal;
import values.GUIValues;

/**
 *
 */
public class DocProposalAgent extends AbstractAgent {

	private static final long serialVersionUID = 1372051869173018986L;
	private static DocumentProposal proposal; 

	@Override
	protected void doOnInit() {
		this.setId("DocProposalAgent");
		
		/*
		 * Fuegt dem Agenten ein InteressenProfil hinzu Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		try {
			System.out.println("DocProposalAgent initialisiert");
			AbstractInterestProfile ip = new DocProposalInterestProfile();
			ip.add(new IsEventType("DocProposalEvent"));
			//ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSpeedEvent()));
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		/*
		 * Angabe der Topics, die konsumiert werden sollen. Es koennen mehrere Topics
		 * angegeben werden.
		*/
		try {
			this.add("DocProposal");
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}

	}

	public DocumentProposal getProposal() {
		if(proposal != null) {
			return proposal;
		}
		return new DocumentProposal();
	}

	public void setProposal(DocumentProposal proposal) {
		DocProposalAgent.proposal = proposal;
	}
}
