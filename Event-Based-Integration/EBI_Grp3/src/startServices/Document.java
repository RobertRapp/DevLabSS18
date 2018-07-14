package startServices;

import org.json.JSONArray;
import org.json.JSONObject;

public class Document {
	/**
	 * All attributes of a document as Strings.
	 */
	String name;
	String type;
	String path;
	String size;
	String lastEditor;
	String color;
	String lastEdit;
	String context;
	String categorie;
	String docID;
	long docProposalID;

	/**
	 * Content of every Document
	 * 
	 * @param name
	 * @param type
	 * @param path
	 * @param size
	 * @param lastEditor
	 * @param lastEdit
	 * @param docProposalID
	 * @param categorie
	 */
	public Document(String name, String type, String path, String size, String lastEditor, String lastEdit,
			long docProposalID, String categorie) {
		super();
		this.name = name;
		this.type = type;
		this.path = path;
		this.size = size;
		this.lastEditor = lastEditor;
		this.lastEdit = lastEdit;
		this.docProposalID = docProposalID;
		this.categorie = categorie;
	}

	/**
	 * The informations are in Json-format and will be transformed to Strings.
	 * 
	 * @param json
	 */
	public Document(String json) {
		JSONObject jsonObject = new JSONObject(json);
		JSONArray bindings = jsonObject.getJSONObject("results").getJSONArray("bindings");
		this.docID = bindings.getJSONObject(0).getJSONObject("FileID").getString("value");
		this.name = bindings.getJSONObject(0).getJSONObject("Document").getString("value");
		this.path = bindings.getJSONObject(0).getJSONObject("URL").getString("value");
		this.type = bindings.getJSONObject(0).getJSONObject("DocumentType").getString("value");
		this.categorie = bindings.getJSONObject(0).getJSONObject("Category").getString("value");
		this.lastEditor = bindings.getJSONObject(0).getJSONObject("Editor").getString("value");
		this.setColor(this.getColor(type));
	}

	/**
	 * 
	 * @param type
	 *            Choose color for each Application
	 * @return the color
	 */
	public String getColor(String type) {
		switch (type) {
		case "Word":
			color = "blue";
			break;
		case "Powerpoint":
			color = "red";
			break;
		default:
			color = "grey";
			break;
		}

		return color;
	}

	/**
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            This is the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return the type of document
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 *            this is the type of document chosen
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return the path / url
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 
	 * @param path
	 *            this is the new path / url
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * 
	 * @param size
	 *            this is the new size
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * 
	 * @return the lastEditor
	 */
	public String getLastEditor() {
		return lastEditor;
	}

	/**
	 * 
	 * @param lastEditor
	 *            this is the new editor
	 */

	public void setLastEditor(String lastEditor) {
		this.lastEditor = lastEditor;
	}

	/**
	 * 
	 * @param color
	 *            This is the color which was chosen
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * 
	 * @return the lastEdit
	 */
	public String getLastEdit() {
		return lastEdit;
	}

	/**
	 * 
	 * @param lastEdit
	 *            This is the last edited Document
	 */
	public void setLastEdit(String lastEdit) {
		this.lastEdit = lastEdit;
	}

}
