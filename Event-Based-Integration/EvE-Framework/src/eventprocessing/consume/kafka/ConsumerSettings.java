package eventprocessing.consume.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.RangeAssignor;
import org.apache.kafka.common.serialization.StringDeserializer;

import eventprocessing.utils.TextUtils;
import eventprocessing.utils.model.ModelUtils;

/**
 * Diese Klasse erzeugt eine Map, die die Verbindungsinformationen zum
 * Kafkaserver beinhaltet.
 * 
 * http://kafka.apache.org/documentation.html#newconsumerconfigs
 * 
 * Auf der Website befinden sich die Konfigurationen, die vorgenommen werden
 * können.
 * 
 * Created by IngoT on 06.07.2017.
 */
public class ConsumerSettings {

	// Hier werden alle Verbindungsinformationen gespeichert
	private Map<String, Object> kafkaParams = new HashMap<String, Object>();

	/**
	 * die Map gibt dem Agent die Parameter für die Verbindung zum schreiben auf
	 * Topics zurück
	 * 
	 * @return kafkaParams, die Map beinhaltet alle Verbindungsinformationen
	 */
	public ConsumerSettings() {
		
	}
	
	public ConsumerSettings(String hostip, String port, String groupId) {
		this.add(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, hostip + ":" + port);
		this.add(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		this.add(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		this.add(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		this.add(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RangeAssignor.class.getName());
	}
	
	public Map<String, Object> getKafkaParameters() {
		return this.kafkaParams;
	}

	/**
	 * Fügt der Map ein key-/ value-Paar hinzu.
	 * 
	 * @param key, Name des Schlüssels
	 * @param value, Wert des Schlüssels
	 */
	public void add(String key, String value) {
		// Wenn der key und value nicht leer sind
		if (!(TextUtils.isNullOrEmpty(key) || TextUtils.isNullOrEmpty(value))) {
			this.kafkaParams.put(key, value);
		}
	}

	private Object[] getSignificantFields() {
		return new Object[] { this.getKafkaParameters() };
	}

	@Override
	public int hashCode() {
		return ModelUtils.hashCodeFor(getSignificantFields());
	}

	@Override
	public boolean equals(Object obj) {
		Boolean result = ModelUtils.quickEquals(this, obj);
		if (result == null) {
			ConsumerSettings that = (ConsumerSettings) obj;
			result = ModelUtils.equalsFor(this.getSignificantFields(), that.getSignificantFields());
		}
		return result;
	}

	@Override
	public String toString() {
		return ModelUtils.toStringFor(this);
	}

}