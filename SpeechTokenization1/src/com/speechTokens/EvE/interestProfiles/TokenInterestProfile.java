package com.speechTokens.EvE.interestProfiles;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.speechTokens.EvE.events.SentenceEvent;
import com.speechTokens.EvE.events.TokenEvent;
import com.speechTokens.tokenizer.Chunker;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.factory.LoggerFactory;

/**
 * Trifft anhand der Sensoren, Durchschnittsgeschwindigkeit sowie mit
 * "Fachwissen" die Entscheidung, ob eine GeschwindigkeitsÃ¼berschreitung
 * stattgefunden hat oder nicht. In beiden FÃ¤llen wird ein
 * <code>ComplexEvent</code> erzeugt und auf ein separates Topic weitergeleitet
 * 
 * @author IngoT
 *
 */
public class TokenInterestProfile extends AbstractInterestProfile {
	/**
	 * 
	 */
	private static final long serialVersionUID = -194575241513454551L;
	private static Logger LOGGER = LoggerFactory.getLogger(TokenInterestProfile.class);
	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {
		TokenEvent e = new TokenEvent();
		if (event instanceof SentenceEvent) {
			SentenceEvent sentenceEvent = (SentenceEvent) event;
			e.add(new Property("UserID", sentenceEvent.getPropertyByKey("UserID")));// Hier die Properties an das neue Event übergebenübergeben
			e.add(new Property("Timestamp", sentenceEvent.getPropertyByKey("Timestamp")));
			e.add(new Property("SessionID", sentenceEvent.getPropertyByKey("SessionID")));
			Chunker semChunks = (Chunker) sentenceEvent.getPropertyByKey("Chunker").getValue();
			for (int i = 0; i < semChunks.size(); i++) {
				String chunk = semChunks.getChunkContentAt(i);
				semChunks.readSemanticOfChunk(chunk);

				
			}

		}

		try {
			getAgent().send(e, "TokenGeneration");
		} catch (NoValidEventException e1) {
			LOGGER.log(Level.WARNING, () -> String.format("%s", e));
		} catch (NoValidTargetTopicException e1) {
			LOGGER.log(Level.WARNING, () -> String.format("%s", e));
		}
	}
}
