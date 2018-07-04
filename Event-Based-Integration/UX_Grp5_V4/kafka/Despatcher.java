package eventprocessing.output.kafka;

//import java.util.logging.Level;
//import java.util.logging.Logger;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

//import eventprocessing.utils.SystemUtils;
//import eventprocessing.utils.factory.LoggerFactory;

import java.io.Serializable;

/**
 * Ist für die Übertragung der Nachrichten an die Topics zuständig. Pro
 * <code>AbstractAgent<code> gibt es eine Instanz.
 * 
 * Die Instanzen teilen sich untereinander den
 * <code>ProducerEnum</code> um die Nachrichten an das Topic zu übertragen.
 * 
 * @author IngoT
 *
 */
public final class Despatcher implements Serializable {

	private static final long serialVersionUID = -1994709962183870968L;
	//private static final Logger LOGGER = LoggerFactory.getLogger(Despatcher.class.getName());
	// Für den Versand der Nachrichten
	private static final Producer<String, String> producer = ProducerEnum.INSTANCE.getKafkaProducer();;

	/**
	 * Überträgt die Nachrichten an das angegebene Topic. Die Übertragung findet
	 * asynchron statt. Der Server wartet auf den Eingang der Nachricht. Das
	 * Acknowledge kann in der Klasse <code>ProducerSettings</code> gesetzt werden.
	 * 
	 * @param message,
	 *            Die Nachricht die übertragen werden soll auf das Topic
	 * @param targetTopic,
	 *            Topic auf welches die Nachricht versendet wird.
	 */
	public void deliver(String message, String targetTopic) {
		ProducerRecord<String, String> record = new ProducerRecord<>(targetTopic, message);
		producer.send(record);

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
}
