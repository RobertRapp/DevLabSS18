package startServices;

/**
 * Beinhaltet alle String literale die für den Abruf der verschiedenen Factories
 * benötigt werden.
 * 
 * @author RobertRapp
 *
 */
public enum FactoryValues {
	INSTANCE;

	// Für den Aufruf der <code>AgentFactory</code>
	private String agent = null;
	// Für den Aufruf der <code>EventFactory</code>
	private String event = null;
	// Klassennamen der Agenten, die verwendet werden
	private String agentDiagnosis = null;
	private String agentSensorProcessing = null;
	private String agentTrafficAnalysis = null;
	// Klassennamen der Events, die verwendet werden
	// Demo Events
	private String eventSensor = null;
	private String eventSpeed = null;
	private String eventSpeeding = null;
	// Conversation Events
	private String eventAnnouncement = null;
	private String eventComment = null;
	// Logging Events
	private String eventCommand = null;
	private String eventCountReceived = null;
	private String eventCountSend = null;

	/**
	 * Beim Start werden die Werte geschrieben.
	 */
	private FactoryValues() {
		agent = "AgentFactory";
		event = "EventFactory";

		agentDiagnosis = "Diagnosis";
		agentSensorProcessing = "SensorProcessing";
		agentTrafficAnalysis = "TrafficAnalysis";

		eventSensor = "SensorEvent";
		eventSpeed = "SpeedEvent";
		eventSpeeding = "SpeedMeasurement";
		eventAnnouncement = "AnnouncementEvent";
		eventComment = "RequestNewId";

		eventCommand = "CommandEvent";
		eventCountReceived = "CountReceivedEvent";
		eventCountSend = "CountSendEvent";
	}

	public String getAgentDiagnosis() {
		return agentDiagnosis;
	}

	public String getAgentSensorProcessing() {
		return agentSensorProcessing;
	}

	public String getAgentTrafficAnalysis() {
		return agentTrafficAnalysis;
	}

	public String getEventSensor() {
		return eventSensor;
	}

	public String getEventSpeed() {
		return eventSpeed;
	}

	public String getSpeedMeasurement() {
		return eventSpeeding;
	}

	public String getAnnouncementEvent() {
		return eventAnnouncement;
	}

	public String getCommentEvent() {
		return eventComment;
	}

	public String getCommandEvent() {
		return eventCommand;
	}

	public String getCountReceivedEvent() {
		return eventCountReceived;
	}

	public String getCountSendEvent() {
		return eventCountSend;
	}

	/**
	 * @return the agentFactory
	 */
	public String getAgentFactory() {
		return agent;
	}

	/**
	 * @return the eventFactory
	 */
	public String getEventFactory() {
		return event;
	}
}
