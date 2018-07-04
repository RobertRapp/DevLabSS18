package eventprocessing.utils;

import org.json.JSONArray;
import org.json.JSONObject;

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
		public Document(String json) {
			JSONObject jsonObject = new JSONObject(json);
//			JSONArray bindings = jsonObject.getJSONObject("results").getJSONArray("bindings");
//			bindings.getJSONObject(0).get("Name");
			this.docID = jsonObject.getString("FileID");
			this.name = jsonObject.getString("Documentname");
			this.path = jsonObject.getString("URL");
			this.type = jsonObject.getString("DocumentType");
			this.categorie = jsonObject.getString("Category");
			this.lastEditor = jsonObject.getString("Editor");
			this.setColor(this.getColor(type));
		}
		
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
			case "application":
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
