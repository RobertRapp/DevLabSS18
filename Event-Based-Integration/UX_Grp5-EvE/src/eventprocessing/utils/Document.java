package eventprocessing.utils;

import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Repräsentiert die Attribute eines Dokumentes.
 * 
 */
public class Document {
	
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
	
		public Document(String docID,String name, String type, String path, String size, String lastEditor, 
			String lastEdit, String categorie) {
		super();
		this.name = name;
		this.type = type;
		this.path = path;
		this.size = size;
		this.lastEditor = lastEditor;
		this.lastEdit = lastEdit;
		this.docID = docID;
		this.categorie = categorie;
	}
		public Document(String jsonStr) {
			JSONObject json = new JSONObject(jsonStr);
			this.docID = json.getJSONObject("FileID").getString("value");
			this.name = json.getJSONObject("FileName").getString("value");
			this.path = json.getJSONObject("URL").getString("value");
			this.type = json.getJSONObject("DocumentType").getString("value");
			this.categorie = json.getJSONObject("Category").getString("value").split("#")[1];
			this.lastEditor = json.getJSONObject("Editor").getString("value").split("#")[1];
			this.setColor(this.getColor(type));
		}
		public Document(LinkedHashMap map) {
			
			this.docID = map.get("path").toString().split("/")[5];
			this.name = (String) map.get("name");
			this.path = (String) map.get("path");
			this.type = (String) map.get("type");
			this.categorie = (String) map.get("category");
			this.lastEditor = (String) map.get("lastEditor");
			this.setColor(this.getColor(type));
		}
		
		// Anhand des Dokumententyps werden die Farben von Microsoft dem Dokument zugeordnet.
		public String getColor(String type) {
			switch(type) {
			case "presentation":
				color = "#dc6141";
				break;
			case "text":
					color = "#4269a5";
					break;	
			case "table":
				color = "#39825a";
					break;	
			case "Application":
				color = "#E5B34F";
				break;	
			default:
					color = "grey";
					break;
			}
			
			return color;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getSize() {
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}

		public String getLastEditor() {
			return lastEditor;
		}

		public void setLastEditor(String lastEditor) {
			this.lastEditor = lastEditor;
		}


		public void setColor(String color) {
			this.color = color;
		}

		public String getLastEdit() {
			return lastEdit;
		}

		public void setLastEdit(String lastEdit) {
			this.lastEdit = lastEdit;
		}

}
