package eventprocessing.produce.kafka;

import java.io.Serializable;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;

import eventprocessing.utils.TextUtils;
import eventprocessing.utils.model.ModelUtils;

/**
 * Hier stehen alle relevanten Verbindungsinformationen für die Verbindung von
 * Spark zu Kafka drin, um Nachrichten auf ein Topic zu schreiben. Das Topic
 * selber wird hier nicht angegeben
 * 
 * Created by IngoT on 06.08.2017.
 */
public final class ProducerSettings implements Serializable {

	private static final long serialVersionUID = 756378637434343618L;
	// Hier werden alle Verbindungsinformationen gespeichert
	private Properties properties = new Properties();
	
	
	public ProducerSettings() {
		
	}
	public ProducerSettings(String host, String port) {
		// IPv4-Adresse des Kafkaservers. Port ist Standardmäßig 9092
		this.add(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, host + ":" + port);
		// Bestätigung für alle gesendeten Nachrichten anfordern
		this.add(ProducerConfig.ACKS_CONFIG, ProducerSettingsDefaultValues.INSTANCE.getAcks());
		// Wie Lang darf die Nachricht sein
		this.add(ProducerConfig.BATCH_SIZE_CONFIG, ProducerSettingsDefaultValues.INSTANCE.getBatchSize());
		// In welchen zeitlichen Abstand werden die Nachrichten vor der Übertragung geschnitten
		this.add(ProducerConfig.LINGER_MS_CONFIG, ProducerSettingsDefaultValues.INSTANCE.getLingerMS());
		// sollen Fehlgeschlagene Versuche wiederholt werden?
		this.add(ProducerConfig.RETRIES_CONFIG, ProducerSettingsDefaultValues.INSTANCE.getRetries());
		// Wie viel darf im Arbeitsspeicher verbleiben
		this.add(ProducerConfig.BUFFER_MEMORY_CONFIG, ProducerSettingsDefaultValues.INSTANCE.getBufferMemory());
		// Für die Serialisierung der Key-/Value Paare
		this.add(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ProducerSettingsDefaultValues.INSTANCE.getKeySerializer());
		this.add(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ProducerSettingsDefaultValues.INSTANCE.getValueSerializer());
	}

	/**
	 * wird von den <code>EventProducer</code> verwendet, um sich die
	 * Verbindungsinformationen zum Kafkaserver zu holen
	 * 
	 * @return properties, alle Informationen für den Verbindungsaufbau
	 */
	public Properties getProperties() {
		return this.properties;
	}
	
	/**
	 * Fügt den Properties ein key-/ value-Paar hinzu.
	 * 
	 * @param key, Name des Schlüssels
	 * @param value, Wert des Schlüssels
	 */
	public void add(String key, String value) {
		// Wenn der key und value nicht leer sind
		if (!(TextUtils.isNullOrEmpty(key) && TextUtils.isNullOrEmpty(value))) {
			this.properties.put(key, value);
		}
	}
	
	/**
	 * Fügt den Properties ein key-/ value-Paar hinzu.
	 * 
	 * @param key, Name des Schlüssels
	 * @param value, Wert des Schlüssels
	 */
	public void add(String key, int value) {
		// Wenn der key nicht null ist
		if (!TextUtils.isNullOrEmpty(key)) {
			this.properties.put(key, value);
		}
	}
		
	@Override
	public String toString() {
		return ModelUtils.toStringFor(this);
	}

}