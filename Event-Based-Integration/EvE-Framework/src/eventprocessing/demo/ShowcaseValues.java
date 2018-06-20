package eventprocessing.demo;

/**
 * Werte für die Demo.
 * Hier können unter anderem die Anzahl der DemoEvents festgelegt werden.
 * 
 * @author IngoT
 *
 */
public enum ShowcaseValues {
	INSTANCE;
	
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
		
	private int speedLimit = 0;
	private int distance = 500;
	private String SeverityOk = null;
	private String SeverityNotOk = null;
	
	private ShowcaseValues() {
		firstSensorLocation = "West-1";
		secondSensorLocation = "West-2";
		firstSensorID = 1;
		secondSensorID = 2;
		amountEvents = 10;
		threadSleep = 1000;
		sensorEvent = "SensorEvent";
		speedEvent = "SpeedEvent";
		speedMeasurement = "SpeedMeasurement";
		firstTopicSensor = "Sensor-1";
		secondTopicSensor = "Sensor-2";
		trafficDataTopic = "TrafficData";
		diagnosisTopic = "Diagnosis";
		storageTopic = "Storage";
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
}
