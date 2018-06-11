package hdm.developmentlab.ebi.eve_implementation.protocolService.interestprofiles;


import eventprocessing.event.AbstractEvent;
import eventprocessing.interestprofile.AbstractInterestProfile;
import hdm.developmentlab.ebi.eve_implementation.events.SessionEvent;


public class Sessions extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 * In dieser Methode wird ein Gesprächskontext empfangen, sobald ein Gespräch abgeschlossen wird.
	 * Anhand der Informationen die innerhalb des empfangenen Events gespeichert sind, wird ein Protokoll erzeugt.
	 * Das ausgehende Format ist machinell analysiserbar und wird an den Agenten weiter gegeben, damit dieser das Event
	 * auf Google Drive abspeichern kann.
	 *
	 * @param arg0
	 */


	@Override
	protected void doOnReceive(AbstractEvent arg0) {

		SessionEvent session = (SessionEvent) arg0; //Hier kommt in jeden Fall ein SessionEvent rein, das bereits alle Infos für das Protokoll enthält.
	}

}
