package eventprocessing.agent.SessionEnd;

import java.util.Formatter;

import org.apache.kafka.clients.consumer.RangeAssignor;
import org.apache.kafka.common.serialization.StringDeserializer;

import eventprocessing.utils.SystemUtils;

/**
 * Alle benötigten Verbindungsinformationen die für das Konsumieren von
 * Nachrichten eines Kafkaservers benötigt werden.
 * 
 * Diese Werte werden von der Klasse <code>ConsumerSettings</code> verwendet.
 * 
 * @author IngoT
 *
 */
public enum ConsumerSettingsSessionEnd {

	INSTANCE;

	/*
	 * Verbindungsinformationen die benötigt werden
	 */
	private String IPv4Bootstrap = null;
	private String portBootstrap = null;
	private String groupID = null;
	private String keyDeserializer = null;
	private String valueDeserializer = null;

	private String partitionAssignmentStrategy = null;

	private ConsumerSettingsSessionEnd() {
		IPv4Bootstrap = "10.142.0.2";
		portBootstrap = "9092";
		groupID = "Diagnosis";
		keyDeserializer = StringDeserializer.class.getName();
		valueDeserializer = StringDeserializer.class.getName();
		partitionAssignmentStrategy = RangeAssignor.class.getName();
	}

	/**
	 * @return the iPv4Bootstrap
	 */
	public String getIPv4Bootstrap() {
		return IPv4Bootstrap;
	}

	/**
	 * @return the portBootstrap
	 */
	public String getPortBootstrap() {
		return portBootstrap;
	}

	/**
	 * @return the groupID
	 */
	public String getGroupId() {
		return groupID;
	}

	/**
	 * @return the keyDeserializer
	 */
	public String getKeyDeserializer() {
		return keyDeserializer;
	}

	/**
	 * @return the valueDeserializer
	 */
	public String getValueDeserializer() {
		return valueDeserializer;
	}

	/**
	 * @return the partitionAssignmentStrategy
	 */
	public String getPartitionAssignmentStrategy() {
		return partitionAssignmentStrategy;
	}

	/**
	 * Rückgabe aller Verbindungsinformationen
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		formatter.format("%s Object {%s", this.getClass().getName(), SystemUtils.getLineSeparator());
		formatter.format("IPv4Bootstrap: %s%s", this.getIPv4Bootstrap(), SystemUtils.getLineSeparator());
		formatter.format("PortBootstrap: %s%s", this.getPortBootstrap(), SystemUtils.getLineSeparator());
		formatter.format("GroupID: %s%s", this.getGroupId(), SystemUtils.getLineSeparator());
		formatter.format("ValueDeserializer: %s%s", this.getValueDeserializer(), SystemUtils.getLineSeparator());
		formatter.format("KeyDeserializer: %s%s", this.getKeyDeserializer(), SystemUtils.getLineSeparator());
		formatter.format("PartitionAssignmentStrategy: %s%s", this.getPartitionAssignmentStrategy(),
				SystemUtils.getLineSeparator());
		formatter.format("}");

		formatter.close();
		return builder.toString();
	}

}
