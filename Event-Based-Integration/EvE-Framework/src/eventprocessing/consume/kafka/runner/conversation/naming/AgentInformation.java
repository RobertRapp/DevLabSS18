package eventprocessing.consume.kafka.runner.conversation.naming;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Die Agenten sind an ihrer Umwelt interessiert und möchten wissen welche
 * Agenten sich noch im Netzwerk befinden. Um ihr Weltbild zu erweitern, können
 * daher diese Informationen imnplizit angefordert werden.
 * 
 * 
 * @author Ingo
 *
 */
public class AgentInformation implements Serializable {

	private static final long serialVersionUID = -6415681603444677311L;
	// Typ des Agenten
	private String AgentType = null;
	// Zeitstempel der Anfrage
	private Timestamp RequestTime = null;
	// Zugewiesene ID
	private long id = 0;

	/**
	 * @return the agentType
	 */
	public String getAgentType() {
		return AgentType;
	}

	/**
	 * @param agentType
	 *            the agentType to set
	 */
	public void setAgentType(String agentType) {
		AgentType = agentType;
	}

	/**
	 * @return the requestTime
	 */
	public Timestamp getRequestTime() {
		return RequestTime;
	}

	/**
	 * @param requestTime
	 *            the requestTime to set
	 */
	public void setRequestTime(Timestamp requestTime) {
		RequestTime = requestTime;
	}

	/**
	 * @return the assignedId
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param assignedId
	 *            the assignedId to set
	 */
	public void setId(long id) {
		this.id = id;
	}

}
