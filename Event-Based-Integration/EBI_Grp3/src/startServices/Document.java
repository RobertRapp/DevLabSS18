package startServices;

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
	long docProposalID;
	
		public Document(String name, String type, String path, String size, String lastEditor, 
			String lastEdit, long docProposalID, String categorie) {
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
		public Document(String json) {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray bindings = jsonObject.getJSONObject("results").getJSONArray("bindings");	
			this.docID = bindings.getJSONObject(0).getJSONObject("FileID").getString("value");
			this.name = bindings.getJSONObject(0).getJSONObject("Document").getString("value");
			this.path = bindings.getJSONObject(0).getJSONObject("URL").getString("value");
			this.type = bindings.getJSONObject(0).getJSONObject("DocumentType").getString("value");
			this.categorie = bindings.getJSONObject(0).getJSONObject("Category").getString("value");
			this.lastEditor =  bindings.getJSONObject(0).getJSONObject("Editor").getString("value");			
			this.setColor(this.getColor(type));
		}
		
		public String getColor(String type) {
			switch(type) {
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
