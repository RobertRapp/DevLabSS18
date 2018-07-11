package eventprocessing.consume.spark.functions;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.api.java.function.Function;

/**
 * Die Nachrichten kommen als ConsumerRecord mit String, String von Kafka an. Im
 * ersten String sind die Metadaten wie Topic und Partition hinterlegt. Im
 * zweiten String verbirgt sich die eigentliche Nachricht.
 * 
 * Aus dem Tupel wird die ID entfernt und nur die Nachricht zurückgegeben.
 * 
 * @author IngoT
 *
 */
public final class ExtractMessage implements Function<ConsumerRecord<String, String>, String> {

	private static final long serialVersionUID = -3957943879008338077L;

	/**
	 * Aus dem ConsumerRecord wird ein String erzeugt
	 * 
	 * @param record,
	 *            Datensatz der von Kafka übermittelt wurde
	 * @return value(), die Nachricht aus dem record.
	 */
	@Override
	public String call(ConsumerRecord<String, String> record) {
		// .value() gibt aus dem key-value Paar den Value zurück, also die Nachricht
		return record.value();
	}
}