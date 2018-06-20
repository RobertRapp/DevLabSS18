package eventprocessing.consume.spark.functions;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.api.java.function.Function;
import eventprocessing.utils.JsonUtils;

/**
 * Diese Funktion dient der Prüfung, ob eine Nachricht ein valides JSON-Format
 * vorweist.
 * 
 * @author Ingo
 *
 */
public final class IsJson implements Function<ConsumerRecord<String, String>, Boolean>{

	private static final long serialVersionUID = 7080315823779445918L;

	/**
	 * Prüfung ob die Nachricht aus dem DStream ein valides JSON beinhaltet.
	 * 
	 * @param record,
	 *            die Inhalte eines DStreams ist ein ConsumerRecord. 
	 *            Der erste String beinhaltet die Metadaten der Übertragung
	 *            Der zweite String beinhaltet die eigentliche Nachricht.
	 * 
	 * @return Boolean, true wenn es einem JSON-Format entspricht, false wenn es
	 *         keinem JSON-Format entspricht.
	 */
	@Override
	public Boolean call(ConsumerRecord<String, String> record) {
		// Das Ergebnis wird zurückgegeben.
		return JsonUtils.isValidJson(record.value());
	}

}
