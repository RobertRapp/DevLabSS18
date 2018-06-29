package hdm.developmentlab.ebi.eve_implementation.sessionContextService;

import java.util.ArrayList;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.event.AbstractEvent;
import hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles.SessionContextIP;




public class SessionContextAgent extends AbstractAgent {
	
	ArrayList<AbstractEvent> sessions = new ArrayList<AbstractEvent>();
	
	protected void doOnInit() {
		
		
		//Ohne ID geht der Agent nicht 
		this.setId("SessionContextAgent");
		
		
		AbstractInterestProfile sessionContextIP = new SessionContextIP();
		sessionContextIP.add(new IsEventType("TokenEvent"));
		try {
			this.add(sessionContextIP);

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
