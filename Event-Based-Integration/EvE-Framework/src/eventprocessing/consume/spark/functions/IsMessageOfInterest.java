package eventprocessing.consume.spark.functions;

import java.util.logging.Level;

import org.apache.spark.api.java.function.Function;

import com.esotericsoftware.minlog.Log.Logger;

import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.ModelUtils;

/**
 * Diese Funktion wird für die Filterung des JavaDStreams verwendet. Sie prüft,
 * ob die Nachrict für das <code>AbstractInterestProfile</code> von Interesse
 * ist.
 * 
 * @author IngoT
 *
 */
public final class IsMessageOfInterest implements Function<String, Boolean> {

	private static final long serialVersionUID = -5096811895821603141L;
	// Für die Anwendung der FilterFunctions nötig.
	private final IsMessageOfInterestPredicate filterPredicate = new IsMessageOfInterestPredicate();
	private static java.util.logging.Logger LOGGER = LoggerFactory.getLogger(IsMessageOfInterest.class);
	/*
	 * Das InteressenProfil, von dem die FilterQueue abgerufen wird, um den Stream
	 * zu filtern.
	 */
	private final AbstractInterestProfile interestProfile;

	/**
	 * Für die Prüfung wird das InterestProfile benötigt.
	 * Anhand des InterestProfiles wird im Dispatcher die dazugehörige FilterQueue
	 * abgerufen, die für die anschließende Filterung verwendet wird.
	 * 
	 * @param interestProfile,
	 *            das zu prüfende InterestProfile, ob die Nachricht von Interesse
	 *            ist.
	 */
	public IsMessageOfInterest(AbstractInterestProfile interestProfile) {
		this.interestProfile = interestProfile;
	}

	/**
	 * Disese Methode wird bei jedem Funktionsaufruf ausgeführt und überprüft das
	 * Streamfragment, ob die registrierten <code>AbstractInterestProfile</code>
	 * Interesse aufweisen.
	 * 
	 * @param message,
	 *            die Nachricht aus dem Stream, die geprüft werden soll
	 * 
	 * @return boolean, bei true wird die Nachricht weiterverarbeitet, bei false
	 *         wird die Nachricht verworfen
	 */
	@Override
	public Boolean call(String message) {
		/*
		 * Es werden alle FilterFunctions in der Queue abgerufen und nacheinander
		 * abgearbeitet. Sollte bei einem Filter ein false zurückkommen, wird durch
		 * .allMatch die weitere Verarbeitung stoppen und false zurückgegeben. Das
		 * Predicate wird gebraucht, um die einzelnen FilterFunctions abzurufen.
		 */
		
		if((interestProfile.getAgent().getDispatcher().getFilterQueueOf(this.interestProfile).getFilters()).stream()
		.allMatch(filterPredicate.isMessageOfInterest(message))) {
			LOGGER.log(Level.INFO, "Ein Event war von Interesse.");
		}
		
		return (interestProfile.getAgent().getDispatcher().getFilterQueueOf(this.interestProfile).getFilters()).stream()
				.allMatch(filterPredicate.isMessageOfInterest(message));
	}
	
	@Override
	public String toString() {
		return ModelUtils.toStringFor(this);
	}

}
