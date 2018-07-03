package eventprocessing.consume.spark.streaming;

import java.util.Formatter;

import eventprocessing.utils.SystemUtils;

/**
 * Alle relevanten Verbindungsinformationen für den SparkContext. Für die
 * Belegung der Werte: http://spark.apache.org/docs/latest/configuration.html
 * 
 * @author Ingo
 *
 */
public enum SparkContextValues {

	INSTANCE;

	// Gibt in MS an, in welchen Intervall der Stream "geschnitten" werden soll
	private int batchDuration = 0;
	// Name der Anwendung für die Log/UI-Ausgabe
	private String sparkAppName = null;
	private String dynamicAllocationEnable = null;
	// Serializer für den eingehenden Stream
	private String sparkSerializer = null;
	// Anzahl der Threads
	private String local = null;
	private String sparkStreamingStopGracefullyOnShutdown = null;

	/*
	 * Spark Streaming has trouble when the batch-processing time is larger than the
	 * batch interval. This means it cannot read the data from the topic faster than
	 * it arrives. Using backpressure will fix that.
	 */
	private String backpressureEnabled = null;
	/*
	 * the maximum rate (in messages per second) at which each Kafka partition will
	 * be read set
	 * "log4j.logger.org.apache.spark.streaming.scheduler.rate.PIDRateEstimator=TRACE"
	 * in log4j.properties in the framework to check the backpressure behavior it
	 * calc a new rate which should look like that:
	 * "TRACE PIDRateEstimator: New rate = [newRate]" according to some best
	 * practice tips you should set the maxRatePerPartition to 150%-200% of the new
	 * rate
	 */
	private String kafkaMaxRatePerPartition = null;

	/**
	 * Der Konstruktor wird verwendet, um die Werte für die <code>SparkConfig</code>
	 * zu setzen.
	 */
	private SparkContextValues() {
		batchDuration = 50;
		sparkAppName = "Testanwendung";
		dynamicAllocationEnable = "false";
		sparkSerializer = "org.apache.spark.serializer.KryoSerializer";
		local = "local[*]";
		sparkStreamingStopGracefullyOnShutdown = "true";
		backpressureEnabled = "true";
		kafkaMaxRatePerPartition = "500";
	}

	/**
	 * @return the batchDuration
	 */
	public int getBatchDuration() {
		return batchDuration;
	}

	/**
	 * @return the sparkAppName
	 */
	public String getSparkAppName() {
		return sparkAppName;
	}

	/**
	 * @return the dynamicAllocationEnable
	 */
	public String getDynamicAllocationEnable() {
		return dynamicAllocationEnable;
	}

	/**
	 * @return the sparkSerializer
	 */
	public String getSparkSerializer() {
		return sparkSerializer;
	}

	/**
	 * @return the local
	 */
	public String getLocal() {
		return local;
	}

	/**
	 * @return the sparkStreamingStopGracefullyOnShutdown
	 */
	public String getSparkStreamingStopGracefullyOnShutdown() {
		return sparkStreamingStopGracefullyOnShutdown;
	}

	/**
	 * @return the backpressureEnabled
	 */
	public String getBackpressureEnabled() {
		return backpressureEnabled;
	}

	/**
	 * @return the kafkaMaxRatePerPartition
	 */
	public String getKafkaMaxRatePerPartition() {
		return kafkaMaxRatePerPartition;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		formatter.format("%s Object {%s", this.getClass().getName(), SystemUtils.getLineSeparator());
		formatter.format("batchDuration: %d%s", this.getBatchDuration(), SystemUtils.getLineSeparator());
		formatter.format("sparkAppName: %s%s", this.getSparkAppName(), SystemUtils.getLineSeparator());
		formatter.format("dynamicAllocationEnable: %s%s", this.getDynamicAllocationEnable(),
				SystemUtils.getLineSeparator());
		formatter.format("sparkSerializer: %s%s", this.getSparkSerializer(), SystemUtils.getLineSeparator());
		formatter.format("local %s%s", this.getLocal(), SystemUtils.getLineSeparator());
		formatter.format("sparkStreamingStopGracefullyOnShutdown: %s%s",
				this.getSparkStreamingStopGracefullyOnShutdown(), SystemUtils.getLineSeparator());
		formatter.format("backpressureEnabled: %s%s", this.getBackpressureEnabled(), SystemUtils.getLineSeparator());
		formatter.format("kafkaMaxRatePerPartition: %s%s", this.getKafkaMaxRatePerPartition(),
				SystemUtils.getLineSeparator());
		formatter.format("}");

		formatter.close();
		return builder.toString();
	}

}
