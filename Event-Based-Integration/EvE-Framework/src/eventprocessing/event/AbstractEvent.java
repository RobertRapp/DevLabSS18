package eventprocessing.event;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import eventprocessing.utils.TextUtils;
import eventprocessing.utils.model.ModelUtils;

/**
 * Die Klasse bildet alle benötigten Member für ein Event ab.
 * 
 * Created by IngoT on 10.06.2017.
 * 
 */
		@JsonSubTypes({ @Type(value = AtomicEvent.class, name = "AtomicEvent"),
		@Type(value = ComplexEvent.class, name = "ComplexEvent") })
public abstract class AbstractEvent implements Serializable {

	private static final long serialVersionUID = 8012429675518827139L;
	
	private String type = null;
	/*
	 * Jedes Event besitzt eine eindeutige Id. Die Id wird automatisch gesetzt,
	 * sobald ein Event über die <code>EventFactory</code> erzeugt wurde.
	 */
	private long id = 0;
	// Liste mit allen Eigenschaften, die das Event besitzt.
	private List<Property<?>> properties = new ArrayList<Property<?>>();
	/*
	 * Der Zeitstempel wird über die <code>EventFactory</code> gesetzt.
	 */
	private Timestamp creationDate = null;
	// Das Topic von dem das Event abgerufen wurde.
	private String source = null;

	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Gibt die Id des Events zurück
	 * 
	 * @return long, Id des Events.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setzt die Id für ein Event.
	 * 
	 * @param id
	 */
	public void setId(long id) {
		// Wenn ein ungültiger Wert angegeben wurde
		if (id < 0) {
			throw new IllegalArgumentException(String.format("id is less than 0, committed value: %L", id));
		}
		this.id = id;
	}

	/**
	 * Gibt die Liste mit allen Eigenschaften des Events zurück.
	 * 
	 * @return List, mit allen Eigenschaften die das Event auszeichnen.
	 */
	public List<Property<?>> getProperties() {
		return properties;
	}

	/**
	 * Fügt dem Event eine Eigenschaft hinzu.
	 * 
	 * @param property,
	 *            Eigenschaft die hinzugefügt werden soll.
	 */
	public void add(Property<?> property) {
		// Wenn die Eigenschaft nicht null ist
		if (property != null) {
			// Wird sie der Liste hinzugefügt
			this.properties.add(property);
			// Sonst wird eine Exception geworfen.
		} else {
			throw new IllegalArgumentException(String.format("null values are not allowed"));
		}
	}
	
	public void addOrReplace(Property<?> property) {
		// Wenn die Eigenschaft nicht null ist
		if (property != null) {
			// Wird sie der Liste hinzugefügt
			boolean bereitesvorhanden = false;
			for(Property<?> property2 : this.properties) {
				if(property2.getKey().equals(property.getKey())) {
					bereitesvorhanden = true;
				}
			}
			if(!bereitesvorhanden) {
				this.properties.add(property);
			}
			
			// Sonst wird eine Exception geworfen.
		} else {
			throw new IllegalArgumentException(String.format("null values are not allowed"));
		}
	}

	/**
	 * entfernt eine Eigenschaft aus der Liste.
	 * 
	 * @param property,
	 *            die entfernt werden soll
	 */
	public void remove(Property<?> property) {
		this.properties.remove(property);
	}

	/**
	 * Liefert den Zeitstempel der Erzeugung zurück
	 * 
	 * @return Timestamp, Zeitpunkt der Erzeugung des Events.
	 */
	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	/**
	 * Setzt das Erzeugungsdatum.
	 * 
	 * @param creationDate,
	 *            Zeitstempel der Erstellung.
	 */
	public void setCreationDate(Timestamp creationDate) {
		if (creationDate != null) {
			this.creationDate = creationDate;
		} else {
			throw new IllegalArgumentException(
					String.format("the specified date has an invalid value: %d", creationDate));
		}
	}

	/**
	 * Setzt die Quelle aus der das Event stammt.
	 * 
	 * @param source
	 */
	public void setSource(String source) {
		// Wenn die Quelle nicht leer oder null ist
		if (!TextUtils.isNullOrEmpty(source)) {
			// wird der Wert gesetzt
			this.source = source;
			// Sonst setzte "unknown"
		} else {
			this.source = "unknown";
		}
	}

	/**
	 * Gibt die Quelle des Events zurück
	 * 
	 * @return String, mit der Quelle des Events.
	 */
	public String getSource() {
		return this.source;
	}

	/**
	 * Alle Felder die für hashCode() und equals(Object) relevant sind.
	 * 
	 * @return Liste mit allen relevanten Feldern der Klasse
	 */
	private Object[] getSignificantFields() {
		return new Object[] { this.getId(), this.getCreationDate(), this.getSource(), this.getProperties() };
	}

	@Override
	public int hashCode() {
		return ModelUtils.hashCodeFor(getSignificantFields());
	}

	@Override
	public boolean equals(Object obj) {
		Boolean result = ModelUtils.quickEquals(this, obj);
		if (result == null) {
			AbstractEvent that = (AbstractEvent) obj;
			result = ModelUtils.equalsFor(this.getSignificantFields(), that.getSignificantFields());
		}
		return result;
	}

	@Override
	public String toString() {
		return ModelUtils.toStringFor(this);
	}

	/**
	 * Throws CloneNotSupportedException as a Event can not be meaningfully cloned.
	 * Construct a new Event instead.
	 *
	 * @throws CloneNotSupportedException
	 *             always
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	public Property<?> getPropertyByKey(String key){
		for(Property<?> p : this.getProperties()) {
			if(p.getKey().equalsIgnoreCase(key)) {
				return p;
			}
			else {
				continue;
			}
		}
		return null;
	}
	public Object getValueByKey(String key){
		for(Property<?> p : this.getProperties()) {
			if(p.getKey().equalsIgnoreCase(key)) {
				return p.getValue();
			}
			else {
				continue;
			}
		}
		return null;
	}
	public Object getValueBySecoundMatch(String key){
		int i = 0;
		for(Property<?> p : this.getProperties()) {
			if(p.getKey().equalsIgnoreCase(key)) {
				i++;
				if(i==1)return p.getValue();
			}
			else {
				continue;
			}
		}
		return null;
	}
}