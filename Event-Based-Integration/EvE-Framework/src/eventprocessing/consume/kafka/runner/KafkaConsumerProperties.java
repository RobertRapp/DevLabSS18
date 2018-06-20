package eventprocessing.consume.kafka.runner;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import eventprocessing.utils.UIDUtils;

/**
 * Stellt die Eigenschaften für den <code>KafkaConsumerRunner</code> bereit.
 * 
 * @author IngoT
 *
 */
public final class KafkaConsumerProperties {

	/*
	 * Eigenschaften für den Verbindungsaufbau zu Kafka
	 */
	private Properties props = new Properties();

	/**
	 * Setzt bei der Initialisierung die Default-Werte
	 */
	public KafkaConsumerProperties() {
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, UIDUtils.getUID());
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
	}

	/**
	 * Gibt die Properties zurück, mit der die Verbindung zu Kafka aufgebaut werden.
	 *
	 * @return props, Eigenschaften für den Verbindungsaufbau zu Kafka.
	 */
	public Properties getProperties() {
		return this.props;
	}
}
