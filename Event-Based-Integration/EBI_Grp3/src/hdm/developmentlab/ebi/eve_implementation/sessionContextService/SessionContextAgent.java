package hdm.developmentlab.ebi.eve_implementation.sessionContextService;

import java.util.ArrayList;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.GetEverything;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.agent.interestprofile.predicates.statement.IsFromTopic;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.demo.agents.diagnosis.DiagnosisInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.InterestProfileFactory;
import eventprocessing.utils.factory.PredicateFactory;
import hdm.developmentlab.ebi.eve_implementation.events.SessionEvent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.SessionState;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.TimeReference;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.Tokens;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.User;




public class SessionContextAgent extends AbstractAgent {
	
	ArrayList<AbstractEvent> sessions = new ArrayList<AbstractEvent>();
	
	protected void doOnInit() {
		
		
		//Ohne ID geht der Agent nicht 
		this.setId("SessionContextAgent");
		try {
			//this.add("Tokens");
			this.add("test");
		} catch (NoValidConsumingTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AbstractInterestProfile tokensip = new User();
		tokensip.add(new GetEverything());
		try {
			this.add(tokensip);
			
		} catch (NoValidInterestProfileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			SessionState ssIP = new SessionState();
			ssIP.add(new GetEverything());
			this.add(ssIP);
			
		} catch (NoValidInterestProfileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		AbstractInterestProfile sessionState = new SessionState();
//		AbstractInterestProfile ip = new DiagnosisInterestProfile();
//		ip.add(new IsEventType(ShowcaseValues.INSTANCE.getSpeedEvent()));
//		try {
//			this.add(ip);
//		} catch (NoValidInterestProfileException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//		this.setId("SessionContextAgent");
//		
//		try {
//			sessionState.add(new GetEverything());
//			this.add(sessionState);
//		} catch (NoValidInterestProfileException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//			this.add("SessionState");			
//		} catch (NoValidConsumingTopicException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	


	public void addSession(AbstractEvent session) {
		sessions.add(session);
	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}





	


}
