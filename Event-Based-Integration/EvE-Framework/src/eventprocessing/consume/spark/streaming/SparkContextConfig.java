package eventprocessing.consume.spark.streaming;

import java.util.Formatter;

import org.apache.spark.SparkConf;

import eventprocessing.utils.SystemUtils;

/**
 * Hier wird die SparkConfig erzeugt für den SparkContext. Die
 * <code>SparkExecution</code>-Klasse ruft die SparkConfig auf, um eine
 * JavaSparkContext-Instanz zu erzeugen.
 * 
 * @author IngoT
 *
 */
public enum SparkContextConfig {

	INSTANCE;

	/*
	 * Die SparkConf, die für die Konfiguration des SparkContext verwendet wird.
	 */
	private final SparkConf conf;

	/**
	 * Sobald eine Instanz erzeugt wird, wird die SparkConfig erzeugt und befüllt.
	 * Die SparkConfig beinhaltet die Verbindungsinformationen für den SparkContext
	 */
	SparkContextConfig() {
		/*
		 * Wenn es sich um Windows handelt, wird der Pfad in die Konfiguration gesetzt,
		 * um die winutils.exe zu laden.
		 */
		if (SystemUtils.isWindows()) {
			
			SystemUtils.setProperty(SystemUtils.getHadoopHomeDirectory(),
					String.format("%s%sSpark", SystemUtils.getProjectPath(), SystemUtils.getFileSeparator()));
		}
		conf = new SparkConf();
		// Name der Anwendung, so wird diese auch in dem UI und der Log angezeigt.
		conf.set("spark.app.name", SparkContextValues.INSTANCE.getSparkAppName());
		conf.set("dynamicAllocation.enabled", SparkContextValues.INSTANCE.getDynamicAllocationEnable());
		conf.set("spark.serializer", SparkContextValues.INSTANCE.getSparkSerializer());
		conf.setMaster(SparkContextValues.INSTANCE.getLocal());

		conf.set("spark.streaming.stopGracefullyOnShutdown",
				SparkContextValues.INSTANCE.getSparkStreamingStopGracefullyOnShutdown());
		conf.set("spark.streaming.backpressure.enabled", SparkContextValues.INSTANCE.getBackpressureEnabled());
		conf.set("spark.streaming.kafka.maxRatePerPartition",
				SparkContextValues.INSTANCE.getKafkaMaxRatePerPartition());
	}

	/**
	 * Gibt die SparkConfig zurück.
	 * 
	 * @return SparkConf, für den SparkContext
	 */
	public SparkConf getSparkConfig() {
		return this.conf;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		formatter.format("%s Object {%s", this.getClass().getName(), SystemUtils.getLineSeparator());
		formatter.format("}");

		formatter.close();
		return builder.toString();
	}

}