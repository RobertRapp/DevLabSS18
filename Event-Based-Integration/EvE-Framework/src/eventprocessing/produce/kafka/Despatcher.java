package eventprocessing.produce.kafka;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;

import eventprocessing.utils.SystemUtils;
import eventprocessing.utils.factory.LoggerFactory;

import java.io.Serializable;

/**
 * Ist für die Übertragung der Nachrichten an die Topics zuständig. Pro
 * <code>AbstractAgent<code> gibt es eine Instanz.
 * 
 * Der Despatcher trägt eine Instanz eines <code>EventProducer</code> in sich.
 * Über diesen werden die Nachrichten an das Topic übermittelt.
 * 
 * @author IngoT
 *
 */
public final class Despatcher implements Serializable {

	private static final long serialVersionUID = -1994709962183870968L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Despatcher.class.getName());
	// Für den Versand der Nachrichten
	private EventProducer<String, String> producer = null;

	/**
	 * Der Despatcher benötigt die <code>ProducerSettings</code> um die Nachricht an
	 * den gewünschten Broker sowie Topic zu senden.
	 * 
	 * Achtung: Die Inhalt der Konfiguration wird nicht geprüft ob diese valide
	 * sind.
	 * 
	 * @param producerSettings,
	 *            Verbindungsinformationen zu dem Brokern
	 */
	public Despatcher(ProducerSettings producerSettings) {
		// Wenn die Settings nicht null sind und die Properties nicht null sind
		if (producerSettings != null && producerSettings.getProperties() != null) {
			// Wird eine Instanz des EventProducer erzeugt mit den Settings
			producer = new EventProducer<String, String>(producerSettings);
			// Sonst wird eine Exception geworfen
		} else {
			throw new IllegalArgumentException("invalid ProducerSettings passed!");
		}
	}

	/**
	 * Überträgt die Nachrichten an das angegebene Topic. Die Übertragung findet
	 * asynchron statt. Der Server wartet auf den Eingang der Nachricht. Das
	 * Acknowledge kann in der Klasse <code>ProducerSettings</code> gesetzt werden.
	 * 
	 * @param message,
	 *            Die Nachricht die übertragen werden soll auf das Topic
	 * @param topic,
	 *            Topic auf welches die Nachricht versendet wird.
	 * @param partition,
	 *            Partiton des Topics
	 */
	public void deliver(String message, String topic, Integer partition) {
		LOGGER.log(Level.FINEST,
				String.format("sent record: %s%sto:%s", message, SystemUtils.getLineSeparator(), topic));
		try {
			// key wird automatisch von Kafka gesetzt.
			ProducerRecord<String, String> record = new ProducerRecord<>(topic, partition, null, message);
			// Nachricht wird versendet
			producer.getProducer().send(record);
		} catch (KafkaException e1) {
			LOGGER.log(Level.WARNING,
					String.format("kafka exception: %s%s", e1.getMessage(), SystemUtils.getLineSeparator(), topic));
		}
	}

	/**
	 * Überträgt die Nachrichten an das angegebene Topic. Die Übertragung findet
	 * asynchron statt. Der Server wartet auf den Eingang der Nachricht. Das
	 * Acknowledge kann in der Klasse <code>ProducerSettings</code> gesetzt werden.
	 * 
	 * @param message,
	 *            Die Nachricht die übertragen werden soll auf das Topic
	 * @param topic,
	 *            Topic auf welches die Nachricht versendet wird.
	 */
	public void deliver(String message, String topic) {
		deliver(message, topic, null);
	}

	// try {
	// // Ein Record für den Versand wird erstellt.
	// ProducerRecord<String, String> record = new ProducerRecord<>(targetTopic,
	// message);
	// // Der KafkaProducer sendet die Nachricht und wartet auf Rückantwort vom
	// Server
	// producer.send(record, (metadata, exception) -> {
	// // Wenn die Metadaten nicht null sind, war die Übertragung erfolgreich
	// if (metadata != null) {
	// // Loggen der Metainformationen
	// LOGGER.log(Level.FINEST,
	// String.format("sent record(key=%s value=%s) " + "meta(partition=%d,
	// offset=%d) time=%d%s",
	// record.key(), record.value(), metadata.partition(), metadata.offset(),
	// System.currentTimeMillis(), SystemUtils.getLineSeparator()));
	// // Sollte bei der Übertragung ein Fehler auftreten, sind die Metadaten null.
	// } else {
	// LOGGER.log(Level.WARNING, exception.getMessage());
	// }
	// });
	// // Bei einem Fehler in der Übertragung wird der Fehler geloggt
	// } catch (Exception e) {
	// LOGGER.log(Level.WARNING, e.getMessage());
	// }
}
