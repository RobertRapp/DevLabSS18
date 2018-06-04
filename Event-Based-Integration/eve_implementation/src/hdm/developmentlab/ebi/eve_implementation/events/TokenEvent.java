package hdm.developmentlab.ebi.eve_implementation.events;

import eventprocessing.event.AbstractEvent;

public class TokenEvent extends AbstractEvent {

	/**
	 * 
	 */
		
	String TokenID;
	String UserID;
	String SessionID;
	String Timestamp;
	String SentenceID;
	String ChunkID;
	String ChunkContent;
	String ChunkSemantic;
	String ChunkType;
	String documentType;
	
	
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	private static final long serialVersionUID = 6649640980097463080L;
	
	public String getTokenID() {
		return TokenID;
	}
	public void setTokenID(String tokenID) {
		TokenID = tokenID;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getSessionID() {
		return SessionID;
	}
	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}
	public String getTimestamp() {
		return Timestamp;
	}
	public void setTimestamp(String timestamp) {
		Timestamp = timestamp;
	}
	public String getSentenceID() {
		return SentenceID;
	}
	public void setSentenceID(String sentenceID) {
		SentenceID = sentenceID;
	}
	public String getChunkID() {
		return ChunkID;
	}
	public void setChunkID(String chunkID) {
		ChunkID = chunkID;
	}
	public String getChunkContent() {
		return ChunkContent;
	}
	public void setChunkContent(String chunkContent) {
		ChunkContent = chunkContent;
	}
	public String getChunkSemantic() {
		return ChunkSemantic;
	}
	public void setChunkSemantic(String chunkSemantic) {
		ChunkSemantic = chunkSemantic;
	}
	public String getChunkType() {
		return ChunkType;
	}
	public void setChunkType(String chunkType) {
		ChunkType = chunkType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
