package eventprocessing.output.kafka.settings;

import java.util.Formatter;
import java.util.Properties;

import eventprocessing.utils.SystemUtils;

/**
 * Hier stehen alle relevanten Verbindungsinformationen für die Verbindung von
 * Spark zu Kafka drin, um Nachrichten auf ein Topic zu schreiben. Das Topic
 * selber wird hier nicht angegeben
 * 
 * Created by IngoT on 06.08.2017.
 */
public enum ProducerSettings {

	INSTANCE;

	// Hier werden alle Verbindungsinformationen gespeichert
	private Properties properties = new Properties();

	/**
	 * Bei der Erstellung der Instanz, werden alle Konfigurationen eingetragen.
	 * Möglich wäre ebenfalls die Informationen über eine Konfigurationsdatei zu
	 * laden.
	 */
	ProducerSettings() {
		// IPv4-Adresse des Kafkaservers. Port ist Standardmäßig 9092
		properties.put("bootstrap.servers", ProducerSettingsValues.INSTANCE.getIPv4Bootstrap() + ":"
				+ ProducerSettingsValues.INSTANCE.getPortBootstrap());
		// Bestätigung für alle gesendeten Nachrichten anfordern
		properties.put("acks", ProducerSettingsValues.INSTANCE.getAcks());
		// sollen Fehlgeschlagene Versuche wiederholt werden?
		properties.put("retries", ProducerSettingsValues.INSTANCE.getRetries());
		// Wie Lang darf die Nachricht sein
		properties.put("batch.size", ProducerSettingsValues.INSTANCE.getBatchSize());
		// In welchen zeitlichen Abstand werden die Nachrichten vor der Übertragung
		// geschnitten
		properties.put("linger.ms", ProducerSettingsValues.INSTANCE.getLingerMS());
		// Wie viel darf im Arbeitsspeicher verbleiben
		properties.put("buffer.memory", ProducerSettingsValues.INSTANCE.getBufferMemory());
		// Für die Serialisierung der Key-/Value Paare
		properties.put("key.serializer", ProducerSettingsValues.INSTANCE.getKeySerializer());
		properties.put("value.serializer", ProducerSettingsValues.INSTANCE.getValueSerializer());
	}

	/**
	 * wird von den <code>KafkaProducerEnum</code> verwendet, um sich die
	 * Verbindungsinformationen zum Kafkaserver zu holen
	 * 
	 * @return properties, alle Informationen für den Verbindungsaufbau
	 */
	public Properties getProperties() {
		return this.properties;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		formatter.format("%s Object {%s", this.getClass().getName(), SystemUtils.getLineSeparator());
		formatter.format("Properties:%s", SystemUtils.getLineSeparator());
		this.getProperties().forEach((k, v) -> formatter.format("%s : %s%s", k, v, SystemUtils.getLineSeparator()));
		formatter.format("}");

		formatter.close();
		return builder.toString();
	}

}