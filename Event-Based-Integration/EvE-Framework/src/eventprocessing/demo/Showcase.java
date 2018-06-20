package eventprocessing.demo;

import org.apache.kafka.clients.producer.ProducerConfig;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.consume.spark.streaming.NoValidAgentException;
import eventprocessing.consume.spark.streaming.StreamingExecution;
import eventprocessing.demo.agents.diagnosis.ProducerSettingsDiagnosis;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.produce.kafka.Despatcher;
import eventprocessing.produce.kafka.ProducerSettings;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.mapping.MessageMapper;;

/**
 * Startpunkt der Anwendung.
 * 
 * hier werden die Agenten initialisiert und die Sparkumgebung ausgeführt.
 * 
 * @author IngoT
 *
 */
public class Showcase {

	// Für die Versendung der DemoEvents an das Topic nötig.
	
	// wandelt die Events in Nachrichten um.
	private static final MessageMapper messageMapper = new MessageMapper();
	// Über diese Factory können Events erzeugt werden
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());

	public static void main(String[] args) {
		/*
		 * Alle Agenten die benötigt werden, werden hier erzeugt.
		 */
		AbstractFactory agentFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getAgentFactory());
		// Erstellung der Agenten
		AbstractAgent diagnosis = (AbstractAgent) agentFactory.createAgent(FactoryValues.INSTANCE.getAgentDiagnosis());
		AbstractAgent sensorProcessing = (AbstractAgent) agentFactory
				.createAgent(FactoryValues.INSTANCE.getAgentSensorProcessing());
		AbstractAgent trafficAnalysis = (AbstractAgent) agentFactory
				.createAgent(FactoryValues.INSTANCE.getAgentTrafficAnalysis());
				
		/*
		 * Die Agenten werden der Sparkumgebung hinzugefügt Nur die Agenten die
		 * hinzugefügt wurden, werden bei der Streamverarbeitung berücksichtigt.
		 */
		try {			
			StreamingExecution.add(sensorProcessing);
			StreamingExecution.add(trafficAnalysis);
			StreamingExecution.add(diagnosis);
		} catch (NoValidAgentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		 * DemoEvents werden an die Topics gesendet. Das Versenden läuft in einem
		 * separierten Thread ab.
		 */
		Runnable myRunnable = new Runnable() {
			public void run() {
				try {
					publishDemoEvents(ShowcaseValues.INSTANCE.getAmountEvents());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		// Thread wird erzeugt und gestartet
		Thread thread = new Thread(myRunnable);
		thread.start();

		/*
		 * Die Anwendung wird gestartet. Es sind bereits alle Agenten instanziiert die
		 * benötigt werden.
		 */
		try {
			StreamingExecution.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Versendet die Events an das Topic
	 * 
	 * @param event,
	 *            welches an das Topic gesendet werden soll
	 * @param topic,
	 *            das Topic, an das die Nachrichten gesendet werden soll
	 */
//	private static void publish(AbstractEvent event, String topic) {
//		String message = messageMapper.toJSON(event);
//		despatcher.deliver(message, topic);
//	}

	/**
	 * Es werden eine vorgegebene Anzahl von Events erzeugt, die dann an die beiden
	 * Topics Sensor-1 und Sensor-2 versendet werden. Nach jeder Versendung wird der
	 * Thread für eine Sekunde angehalten, damit die Events nicht alle nahezu
	 * zeitgleich versendet werden.
	 * 
	 * @param amoutOfEvents,
	 *            Anzahl der Events pro Topic
	 * @throws InterruptedException
	 */
	private static void publishDemoEvents(int amoutOfEvents) throws InterruptedException {
		ProducerSettings pSettings = new ProducerSettings();
		pSettings.add(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getIPv4Bootstrap() + ":" + ProducerSettingsDiagnosis.INSTANCE.getPortBootstrap());
		// Bestätigung für alle gesendeten Nachrichten anfordern
		pSettings.add(ProducerConfig.ACKS_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getAcks());
		// Wie Lang darf die Nachricht sein
		pSettings.add(ProducerConfig.BATCH_SIZE_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getBatchSize());
		// In welchen zeitlichen Abstand werden die Nachrichten vor der Übertragung geschnitten
		pSettings.add(ProducerConfig.LINGER_MS_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getLingerMS());
		// sollen Fehlgeschlagene Versuche wiederholt werden?
		pSettings.add(ProducerConfig.RETRIES_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getRetries());
		// Wie viel darf im Arbeitsspeicher verbleiben
		pSettings.add(ProducerConfig.BUFFER_MEMORY_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getBufferMemory());
		// Für die Serialisierung der Key-/Value Paare
		pSettings.add(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getKeySerializer());
		pSettings.add(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ProducerSettingsDiagnosis.INSTANCE.getValueSerializer());
		
		Despatcher despatcher = new Despatcher(pSettings);
		
		for (int i = 1; i <= amoutOfEvents; i++) {
			AbstractEvent event = eventFactory.createEvent(FactoryValues.INSTANCE.getAtomicEvent());
			event.setType("SensorEvent");
			event.setSource("Street-11-part-1");
			event.add(new Property<Integer>("SensorID", ShowcaseValues.INSTANCE.getFirstSensorID()));
			event.add(new Property<String>("Location", ShowcaseValues.INSTANCE.getFirstSensorLocation()));
			
			//publish(event, ShowcaseValues.INSTANCE.getFirstTopicSensor());
			String message = messageMapper.toJSON(event);
			despatcher.deliver(message, ShowcaseValues.INSTANCE.getFirstTopicSensor());
			Thread.sleep(ShowcaseValues.INSTANCE.getThreadSleep());
		}

		for (int i = 1; i <= amoutOfEvents; i++) {
			AbstractEvent event = eventFactory.createEvent(FactoryValues.INSTANCE.getAtomicEvent());
			event.setType("SensorEvent");
			event.setSource("Street-11-part-2");
			event.add(new Property<Integer>("SensorID", ShowcaseValues.INSTANCE.getSecondSensorID()));
			event.add(new Property<String>("Location", ShowcaseValues.INSTANCE.getSecondSensorLocation()));
			//publish(event, ShowcaseValues.INSTANCE.getSecondTopicSensor());
			String message = messageMapper.toJSON(event);
			despatcher.deliver(message, ShowcaseValues.INSTANCE.getSecondTopicSensor());
			Thread.sleep(ShowcaseValues.INSTANCE.getThreadSleep());
		}
	}
}