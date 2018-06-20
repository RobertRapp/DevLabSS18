package eventprocessing.consume.spark.streaming;

import java.util.HashMap;
import java.util.Map;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.utils.TextUtils;

/**
 * Alle Agenten die sich in der Sparkumgebung existieren, werden hier
 * vorgehalten. Die <code>StreamingExecution</code> nutzt die Registry, um für
 * jeden Agenten einen eigenen DStream zu Kafka bereitzustellen.
 * 
 * @author IngoT
 *
 */
public enum AgentRegistry {

	INSTANCE;

	/*
	 * Die Agenten werden in einer Map gespeichert. Über die ID können Agenten
	 * eindeutig identifiziert werden.
	 * 
	 * @key String, Id des Agenten
	 * 
	 * @value AbstractAgent, die Agenteninstanz, die zu der Id passt.
	 */
	private Map<String, AbstractAgent> registry = new HashMap<String, AbstractAgent>();

	/**
	 * Gibt die Registry zurück mit allen Agenten
	 * 
	 * @return registry, Map mit allen Agenten
	 */
	public Map<String, AbstractAgent> getRegistry() {
		return this.registry;
	}

	/**
	 * Fügt der Registry einen Agenten hinzu. Sollte die Id bereits vorhanden sein,
	 * wird der alte Agent überschrieben.
	 * 
	 * @param agent,
	 *            der der Registry hinzugefügt werden soll.
	 * @throws NoValidAgentException,
	 *             wenn der Agent null ist oder keine Id besitzt.
	 */
	public void add(AbstractAgent agent) throws NoValidAgentException {
		// Prüfung ob der Agent nicht null ist und ob er eine Id besitzt
		if (agent != null && !TextUtils.isNullOrEmpty(agent.getId())) {
			this.registry.put(agent.getId(), agent);
		} else {
			throw new NoValidAgentException(String.format("the committed agent is null or has no id: %s", agent));
		}
	}

	/**
	 * Entfernt einen Agenten aus der Registry.
	 * 
	 * @param agent,
	 *            der entfernt werden soll
	 */
	public void remove(AbstractAgent agent) {
		if (agent != null) {
			this.registry.remove(agent.getId());
		}
	}

	/**
	 * Entfernt einen Agenten anhand der Id aus der Registry
	 * 
	 * @param id,
	 *            Id des Agenten, die ihn eindeutig identifizieren.
	 */
	public void remove(String id) {
		if (!TextUtils.isNullOrEmpty(id)) {
			this.registry.remove(id);
		}
	}
}