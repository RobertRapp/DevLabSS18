package eventprocessing.produce.kafka;

import java.io.Serializable;
import java.util.Formatter;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import eventprocessing.utils.SystemUtils;

/**
 * Erzeugt eine Instanz des KafkaProducers mit den dazugehörigen
 * <code>ProducerSettings</code>.
 * 
 * @author IngoT
 *
 */
public final class EventProducer<K, V> implements Serializable {

	private static final long serialVersionUID = 1456042004867316231L;
	// Der KafkaProducer, der die Übermittelung übernimmt
	private transient Producer<K, V> producer;
	/*
	 * Für den Versand von Nachrichten werden die ProducerSettings benötigt.
	 */
	private final ProducerSettings producerSettings;

	/**
	 * Bei der Erstellung der Distanz sind die ProducerSettings zwingend
	 * erforderlich, da sonst keine Verbindung zu dem Kafka-broker aufgebaut werden
	 * kann.
	 * 
	 * @param settings,
	 *            die ProducerSettings für die Verbindung zu dem Kafka-broker
	 */
	public EventProducer(ProducerSettings settings) {
		if (settings != null && settings.getProperties() != null) {
			// Mit den ProducerSettings wird der KafkaProducer erzeugt.
			this.producerSettings = settings;
		} else {
			throw new IllegalArgumentException("no valid Settings");
		}
	}

	/**
	 * gibt den KafkaProducer zurück
	 * 
	 * @return kafkaProducer, mit den ProducerSettings
	 */
	public Producer<K, V> getProducer() {
		// Mit den ProducerSettings wird der KafkaProducer erzeugt.
		producer = new KafkaProducer<K, V>(producerSettings.getProperties());
		return producer;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		formatter.format("%s Object {%s", this.getClass().getName(), SystemUtils.getLineSeparator());
		formatter.format("KafkaProducer: %s%s", this.getProducer(), SystemUtils.getLineSeparator());
		formatter.format("}");

		formatter.close();
		return builder.toString();
	}
}
