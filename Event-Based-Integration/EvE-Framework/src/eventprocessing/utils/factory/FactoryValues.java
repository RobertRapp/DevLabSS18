package eventprocessing.utils.factory;

/**
 * Beinhaltet alle String literale die für den Abruf der verschiedenen Factories
 * benötigt werden.
 * 
 * @author IngoT
 *
 */
public enum FactoryValues {
	INSTANCE;

	// Für den Aufruf der <code>AgentFactory</code>
	private String agent = null;
	// Für den Aufruf der <code>EventFactory</code>
	private String event = null;
	// Für den Aufruf der <code>AgentFactory</code>
	private String interestProfile = null;
	// Für den Aufruf der <code>EventFactory</code>
	private String predicate = null;

	// Klassennamen der InterestProfiles
	private String diagnosisInterestProfile = null;
	private String sensorProcessingInterestProfile = null;
	private String trafficAnalysisInterestProfile = null;
	// Klassennamen der Predicates
	private String and = null;
	private String not = null;
	private String or = null;
	private String getEverything = null;
	private String hasProperty= null;
	private String hasPropertyContains= null;
	private String isEventType= null;
	private String isFromTopic = null;
	// Klassennamen der Agenten, die verwendet werden
	private String agentDiagnosis = null;
	private String agentSensorProcessing = null;
	private String agentTrafficAnalysis = null;
	// Klassennamen der Events, die verwendet werden
	// Demo Events
	private String atomicEvent = null;
	private String complexEvent = null;

	/**
	 * Beim Start werden die Werte geschrieben.
	 */
	private FactoryValues() {
		agent = "AgentFactory";
		event = "EventFactory";
		interestProfile = "InterestProfileFactory";
		predicate = "PredicateFactory";

		diagnosisInterestProfile = "DiagnosisInterestProfile";
		sensorProcessingInterestProfile = "SensorProcessingInterestProfile";
		trafficAnalysisInterestProfile = "SpeedometerInterestProfile";
		
		and = "And";
		not = "Not";
		or = "Or";
		getEverything = "GetEverything";
		hasProperty = "HasProperty";
		hasPropertyContains = "HasPropertyContains";
		isEventType = "IsEventType";
		isFromTopic = "IsFromTopic";
		
		agentDiagnosis = "Diagnosis";
		agentSensorProcessing = "SensorProcessing";
		agentTrafficAnalysis = "TrafficAnalysis";

		atomicEvent = "AtomicEvent";
		complexEvent = "ComplexEvent";
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
	
	public String getDiagnosisInterestProfile() {
		return diagnosisInterestProfile;
	}
	
	public String getSensorProcessingInterestProfile() {
		return sensorProcessingInterestProfile;
	}
	
	public String getSpeedometerInterestProfile() {
		return trafficAnalysisInterestProfile;
	}
	
	public String getAnd() {
		return and;
	}
	
	public String getNot() {
		return not;
	}
	
	public String getOr() {
		return or;
	}
	
	public String getGetEverything() {
		return getEverything;
	}
	
	public String getHasProperty() {
		return hasProperty;
	}
	
	public String getHasPropertyContains() {
		return hasPropertyContains;
	}
	
	public String getIsEventType() {
		return isEventType;
	}
	
	public String getIsFromTopic() {
		return isFromTopic;
	}
	
	public String getAtomicEvent() {
		return atomicEvent;
	}

	public String getComplexEvent() {
		return complexEvent;
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
	
	/**
	 * @return the interestProfileFactory
	 */
	public String getInterestProfileFactory() {
		return interestProfile;
	}
	
	/**
	 * @return the predicateFactory
	 */
	public String getPredicateFactory() {
		return predicate;
	}
}
