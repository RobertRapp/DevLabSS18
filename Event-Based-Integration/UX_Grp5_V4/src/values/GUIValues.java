package values;

public enum GUIValues {
	INSTANCE;
	
	private String user=null;
	private String sessionId =null;
	
	private int amountEvents = 0;
	private int threadSleep = 0;
	
	private String userInteractionEvent=null;
	private String sessionEndEvent=null;
	private String docProposalEvent=null;
	private String guiEvent=null;
	
	private String userInteractionTopic=null;
	private String sessionEndTopic=null;
	private String docProposalTopic=null;
	private String guiTopic=null;
	
	private String ipKafka = null;
	private String portKafka = null;
	private String groupIdDiagnosis = null;
	private String groupIdTrafficAnalysis = null;
	private String groupIdSensorProcessing = null;
	private String groupIdUserInteraction = null;
	private String groupIdSessionEnd = null;
	private String groupIdDocProposal = null;
	
	private int speedLimit = 0;
	private int distance = 500;
	private String SeverityOk = null;
	private String SeverityNotOk = null;
	
	private GUIValues() {
		
		user="m.sautter89@gmail.com";
		sessionId="-testSessionID-";
		userInteractionEvent="UserInteractionEvent";
		sessionEndEvent = "SessionEndEvent";
		docProposalEvent = "DocProposalEvent";
		guiEvent = "GuiEvent";
		userInteractionTopic="UserInteraction";
		sessionEndTopic="SessionEnd";
		docProposalTopic = "DocProposal";
		guiTopic = "Gui";
		
		amountEvents = 20;
		threadSleep = 1000;
		//ipKafka = "192.168.56.101";
		ipKafka = "10.142.0.2";
		portKafka = "9092";
		groupIdDiagnosis = "Diagnosis";
		groupIdTrafficAnalysis = "TrafficAnalysis";
		groupIdSensorProcessing = "SensorProcessing";
		groupIdUserInteraction = "UserInteraction";
		speedLimit = 50;
		distance = 500;
		SeverityOk = "everything is fine, no action is needed";
		SeverityNotOk = "too fast! take action!";
	}

	public int getAmountEvents() {
		return amountEvents;
	}
	
	
	public int getThreadSleep() {
		return threadSleep;
	}
	
	
	public String getIpKafka() {
		return ipKafka;
	}
	
	public String getPortKafka() {
		return portKafka;
	}
	
	public String getGroupIdDiagnosis() {
		return groupIdDiagnosis;
	}
	
	public String getGroupIdTrafficAnalysis() {
		return groupIdTrafficAnalysis;
	}
	
	public String getGroupIdSensorProcessing() {
		return groupIdSensorProcessing;
	}
	
	
	public String getGroupIdUserInteraction() {
		return groupIdUserInteraction;
	}

	public int getSpeedLimit() {
		return speedLimit;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public String getSeverityOk() {
		return SeverityOk;
	}
	
	public String getSeverityNotOk() {
		return SeverityNotOk;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String docId) {
		this.sessionId = docId;
	}

	public String getUserInteractionTopic() {
		return userInteractionTopic;
	}

	public void setUserInteractionTopic(String userInteractionTopic) {
		this.userInteractionTopic = userInteractionTopic;
	}

	public String getUserInteractionEvent() {
		return userInteractionEvent;
	}

	public void setUserInteractionEvent(String userInteractionEvent) {
		this.userInteractionEvent = userInteractionEvent;
	}

	public String getSessionEndEvent() {
		return sessionEndEvent;
	}

	public void setSessionEndEvent(String sessionEndEvent) {
		this.sessionEndEvent = sessionEndEvent;
	}

	public String getSessionEndTopic() {
		return sessionEndTopic;
	}

	public void setSessionEndTopic(String sessionEndTopic) {
		this.sessionEndTopic = sessionEndTopic;
	}

	public String getDocProposalEvent() {
		return docProposalEvent;
	}

	public void setDocProposalEvent(String docProposalEvent) {
		this.docProposalEvent = docProposalEvent;
	}

	public String getDocProposalTopic() {
		return docProposalTopic;
	}

	public void setDocProposalTopic(String docProposalTopic) {
		this.docProposalTopic = docProposalTopic;
	}

	public String getGuiEvent() {
		return guiEvent;
	}

	public String getGuiTopic() {
		return guiTopic;
	}
	
	
	
	
	
}
