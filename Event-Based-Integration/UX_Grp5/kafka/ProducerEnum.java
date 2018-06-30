package eventprocessing.output.kafka;

import java.util.Formatter;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import eventprocessing.output.kafka.settings.ProducerSettings;
import eventprocessing.utils.SystemUtils;

/**
 * Erzeugt eine Instanz des KafkaProducers mit den dazugehörigen
 * <code>ProducerSettings</code>. In der Kafka-API
 * (https://kafka.apache.org/0102/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html)
 * steht: "The producer is thread safe and sharing a single producer instance
 * across threads will generally be faster than having multiple instances."
 * 
 * Daher wurde diese Klasse als Singleton erstellt.
 * 
 * @author IngoT
 *
 */
public enum ProducerEnum {

	INSTANCE;

	// Der KafkaProducer, der die Übermittelung übernimmt
	private final Producer<String, String> kafkaProducer;

	private ProducerEnum() {
		// Mit den ProducerSettings wird der KafkaProducer erzeugt.
		kafkaProducer = new KafkaProducer<>(ProducerSettings.INSTANCE.getProperties());
	}

	/**
	 * gibt den KafkaProducer zurück
	 * 
	 * @return kafkaProducer
	 */
	public Producer<String, String> getKafkaProducer() {
		return this.kafkaProducer;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		formatter.format("%s Object {%s", this.getClass().getName(), SystemUtils.getLineSeparator());
		formatter.format("KafkaProducer: %s%s", this.getKafkaProducer(), SystemUtils.getLineSeparator());
		formatter.format("}");
		
		formatter.close();
		return builder.toString();
	}
}
