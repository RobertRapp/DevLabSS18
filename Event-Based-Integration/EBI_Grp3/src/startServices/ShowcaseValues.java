package startServices;

public enum ShowcaseValues {
	INSTANCE;
	
	private String sessionEvent = null;
	
	
	private String firstSensorLocation = null;
	private String secondSensorLocation = null;
	private int firstSensorID = 0;
	private int secondSensorID = 0;
	
	private int amountEvents = 0;
	private int threadSleep = 0;
	
	private String sensorEvent = null;
	private String speedEvent = null;
	private String speedMeasurement = null;
	
	private String firstTopicSensor = null;
	private String secondTopicSensor = null;
	private String trafficDataTopic = null;
	private String diagnosisTopic = null;
	private String storageTopic = null;
	
	private String ipKafka = null;
	private String portKafka = null;
	private String groupIdDiagnosis = null;
	private String groupIdTrafficAnalysis = null;
	private String groupIdSensorProcessing = null;
	
	private int speedLimit = 0;
	private int distance = 500;
	private String SeverityOk = null;
	private String SeverityNotOk = null;
	
	private ShowcaseValues() {
		
		sessionEvent = "SessionEvent";
		
		firstSensorLocation = "West-1";
		secondSensorLocation = "West-2";
		firstSensorID = 1;
		secondSensorID = 2;
		amountEvents = 15;
		threadSleep = 1000;
		sensorEvent = "SensorEvent";
		speedEvent = "SpeedEvent";
		speedMeasurement = "SpeedMeasurement";
		firstTopicSensor = "Sensor-1";
		secondTopicSensor = "Sensor-2";
		trafficDataTopic = "TrafficData";
		diagnosisTopic = "Diagnosis";
		storageTopic = "Storage";
		ipKafka = "10.142.0.2";
		portKafka = "9092";
		groupIdDiagnosis = "Diagnosis";
		groupIdTrafficAnalysis = "TrafficAnalysis";
		groupIdSensorProcessing = "SensorProcessing";
		speedLimit = 50;
		distance = 500;
		SeverityOk = "everything is fine, no action is needed";
		SeverityNotOk = "too fast! take action!";
	}

	public int getAmountEvents() {
		return amountEvents;
	}
	
	public String getFirstSensorLocation() {
		return firstSensorLocation;
	}
	
	public String getSecondSensorLocation() {
		return secondSensorLocation;
	}
	
	public int getFirstSensorID() {
		return firstSensorID;
	}
	
	public int getSecondSensorID() {
		return secondSensorID;
	}
	
	public int getThreadSleep() {
		return threadSleep;
	}
	
	public String getSensorEvent() {
		return sensorEvent;
	}
	
	public String getSpeedEvent() {
		return speedEvent;
	}
	
	public String getSpeedMeasurement() {
		return speedMeasurement;
	}
	
	public String getFirstTopicSensor() {
		return firstTopicSensor;
	}
	
	public String getSecondTopicSensor() {
		return secondTopicSensor;
	}
	
	public String getTrafficDataTopic() {
		return trafficDataTopic;
	}
	
	public String getDiagnosisTopic() {
		return diagnosisTopic;
	}
	
	public String getStorageTopic() {
		return storageTopic;
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

	public String getSessionEvent() {
		return sessionEvent;
	}

	public void setSessionEvent(String sessionEvent) {
		this.sessionEvent = sessionEvent;
	}
}
