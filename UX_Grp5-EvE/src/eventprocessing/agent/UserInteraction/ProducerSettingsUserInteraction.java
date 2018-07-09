package eventprocessing.agent.UserInteraction;

import java.util.Formatter;

import eventprocessing.utils.SystemUtils;

/**
 * Beinhaltet alle Verbindungsinformationen, die für das Senden von Nachrichten
 * an den Kafkaserver benötigt wird.
 * 
 * @author IngoT
 *
 */
public enum ProducerSettingsUserInteraction {

	INSTANCE;

	/*
	 * Verbindungsinformationen die benötigt werden
	 */
	// IPv4+Port von Kafka
	private String IPv4Bootstrap = null;
	private String portBootstrap = null;
	// Soll auf eine Rückantwort des Servers gewartet werden
	private String acks = null;
	// Wie viele Versuche sollen unternommen werden bei einem Fehler
	private int retries = 0;
	// Größe der Batchsize
	private int batchSize = 0;
	private int lingerMS = 0;
	private int bufferMemory = 0;
	// Serialisierungsklassen für die key-/value pairs
	private String keySerializer = null;
	private String valueSerializer = null;

	/**
	 * setzen der Parameter im Konstruktor bei der Erzeugung.
	 */
	private ProducerSettingsUserInteraction() {
		IPv4Bootstrap = "10.142.0.2";
		portBootstrap = "9092";
		acks = "0";//"all";
		retries = 0;
		batchSize = 8000;//16384;
		lingerMS = 0;
		bufferMemory = 33554432;
		keySerializer = "org.apache.kafka.common.serialization.StringSerializer";
		valueSerializer = "org.apache.kafka.common.serialization.StringSerializer";
	}

	/**
	 * @return the iPv4
	 */
	public String getIPv4Bootstrap() {
		return IPv4Bootstrap;
	}

	/**
	 * @return the port
	 */
	public String getPortBootstrap() {
		return portBootstrap;
	}

	/**
	 * @return the acks
	 */
	public String getAcks() {
		return acks;
	}

	/**
	 * @return the retries
	 */
	public int getRetries() {
		return retries;
	}

	/**
	 * @return the batchSize
	 */
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * @return the lingerMS
	 */
	public int getLingerMS() {
		return lingerMS;
	}

	/**
	 * @return the bufferMemory
	 */
	public int getBufferMemory() {
		return bufferMemory;
	}

	/**
	 * @return the keySerializer
	 */
	public String getKeySerializer() {
		return keySerializer;
	}

	/**
	 * @return the valueSerializer
	 */
	public String getValueSerializer() {
		return valueSerializer;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		formatter.format("%s Object {%s", this.getClass().getName(), SystemUtils.getLineSeparator());
		formatter.format("IPv4Bootstrap: %s%s", this.getIPv4Bootstrap(), SystemUtils.getLineSeparator());
		formatter.format("PortBootstrap: %s%s", this.getPortBootstrap(), SystemUtils.getLineSeparator());
		formatter.format("acks: %s%s", this.getAcks(), SystemUtils.getLineSeparator());
		formatter.format("Batchsize: %d%s", this.getBatchSize(), SystemUtils.getLineSeparator());
		formatter.format("BufferMemory %d%s", this.getBufferMemory(), SystemUtils.getLineSeparator());
		formatter.format("KeySerializer: %s%s", this.getKeySerializer(), SystemUtils.getLineSeparator());
		formatter.format("ValueSerializer: %s%s", this.getValueSerializer(), SystemUtils.getLineSeparator());
		formatter.format("retries: %d%s", this.getRetries(), SystemUtils.getLineSeparator());
		formatter.format("lingerMS: %d%s", this.getLingerMS(), SystemUtils.getLineSeparator());
		formatter.format("}");

		formatter.close();
		return builder.toString();
	}

}
