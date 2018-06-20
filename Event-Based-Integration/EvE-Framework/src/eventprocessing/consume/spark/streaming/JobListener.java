package eventprocessing.consume.spark.streaming;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.spark.streaming.scheduler.StreamingListener;
import org.apache.spark.streaming.scheduler.StreamingListenerBatchCompleted;
import org.apache.spark.streaming.scheduler.StreamingListenerBatchStarted;
import org.apache.spark.streaming.scheduler.StreamingListenerBatchSubmitted;
import org.apache.spark.streaming.scheduler.StreamingListenerOutputOperationCompleted;
import org.apache.spark.streaming.scheduler.StreamingListenerOutputOperationStarted;
import org.apache.spark.streaming.scheduler.StreamingListenerReceiverError;
import org.apache.spark.streaming.scheduler.StreamingListenerReceiverStarted;
import org.apache.spark.streaming.scheduler.StreamingListenerReceiverStopped;
//import org.apache.spark.streaming.scheduler.StreamingListenerStreamingStarted;
import org.apache.spark.streaming.scheduler.StreamingListenerStreamingStarted;

import eventprocessing.utils.factory.LoggerFactory;

/**
 * Der StreamingListener kann dem JavaStreamingContext hinzugefügt werden. Er liefert
 * Informationen die den Stream betreffen und gibt diese auf der Konsole sowie
 * in der Log aus.
 * 
 * @author IngoT
 *
 */
public class JobListener implements StreamingListener {

	private static Logger LOGGER = LoggerFactory.getLogger(JobListener.class.getName());

	/**
	 * Bei Aktivierung des JobListener werden alle Ergeinisse in eine Logdatei
	 * geschrieben
	 */

	@Override
	public void onBatchCompleted(StreamingListenerBatchCompleted arg0) {
		LOGGER.log(Level.FINEST, () -> String.format("Batch Class: %s", arg0.batchInfo().streamIdToInputInfo()));
		LOGGER.log(Level.FINEST, () -> String.format("Batch RecordNumber: ", arg0.batchInfo().numRecords()));
		LOGGER.log(Level.FINEST, () -> String.format("Batch RecordNumber: ", arg0.batchInfo().streamIdToInputInfo()));
	}

	@Override
	public void onBatchStarted(StreamingListenerBatchStarted arg0) {
		LOGGER.log(Level.FINEST, () -> String.format("Batch started: ", arg0));
	}

	@Override
	public void onBatchSubmitted(StreamingListenerBatchSubmitted arg0) {
		LOGGER.log(Level.FINEST, () -> String.format("Batch Submitted: ", arg0));
	}

	@Override
	public void onReceiverError(StreamingListenerReceiverError arg0) {
		LOGGER.log(Level.FINEST, () -> String.format("Errormessage: ", arg0));
	}

	@Override
	public void onReceiverStarted(StreamingListenerReceiverStarted arg0) {
		LOGGER.log(Level.FINEST, () -> String.format("Receiver Streamid:", arg0.receiverInfo().streamId()));
		LOGGER.log(Level.FINEST, () -> String.format("Receiver name:", arg0.receiverInfo().name()));
	}

	@Override
	public void onReceiverStopped(StreamingListenerReceiverStopped arg0) {
		LOGGER.log(Level.FINEST, () -> String.format("Receiver stopped: ", arg0));
	}

	@Override
	public void onOutputOperationCompleted(StreamingListenerOutputOperationCompleted arg0) {
		LOGGER.log(Level.FINEST, () -> String.format("Operationen completed: ", arg0));
	}

	@Override
	public void onOutputOperationStarted(StreamingListenerOutputOperationStarted arg0) {
		LOGGER.log(Level.FINEST, () -> String.format("Operation started: ", arg0));
	}

	@Override
	public void onStreamingStarted(StreamingListenerStreamingStarted arg0) {
		LOGGER.log(Level.FINEST, () -> String.format("Streaming started: ", arg0));
	}

}