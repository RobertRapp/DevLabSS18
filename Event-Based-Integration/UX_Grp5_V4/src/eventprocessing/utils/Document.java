package eventprocessing.utils;

import java.util.LinkedHashMap;

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
//			System.out.println(jsonObject.getJSONObject("results").getJSONArray("bindings").getJSONObject(0).getJSONObject("FileID").getString("value"));
//			System.out.println(jsonObject.getJSONObject("FileID").get("value").toString());
			this.docID = jsonObject.getJSONObject("results").getJSONArray("bindings").getJSONObject(0).getJSONObject("FileID").getString("value");
			this.name = jsonObject.getJSONObject("results").getJSONArray("bindings").getJSONObject(0).getJSONObject("FileName").getString("value");
			this.path = jsonObject.getJSONObject("results").getJSONArray("bindings").getJSONObject(0).getJSONObject("URL").getString("value");
			this.type = jsonObject.getJSONObject("results").getJSONArray("bindings").getJSONObject(0).getJSONObject("DocumentType").getString("value");
			this.categorie = jsonObject.getJSONObject("results").getJSONArray("bindings").getJSONObject(0).getJSONObject("Category").getString("value").split("#")[1];
			this.lastEditor = jsonObject.getJSONObject("results").getJSONArray("bindings").getJSONObject(0).getJSONObject("Editor").getString("value").split("#")[1];
			this.setColor(this.getColor(type));
		}
		public Document(LinkedHashMap map) {
			
//			JSONArray bindings = jsonObject.getJSONObject("results").getJSONArray("bindings");
//			bindings.getJSONObject(0).get("Name");
//			System.out.println(jsonObject.getJSONObject("results").getJSONArray("bindings").getJSONObject(0).getJSONObject("FileID").getString("value"));
//			System.out.println(jsonObject.getJSONObject("FileID").get("value").toString());
			this.docID = map.get("path").toString().split("/")[5];
			this.name = (String) map.get("name");
			this.path = (String) map.get("path");
			this.type = (String) map.get("type");
			this.categorie = (String) map.get("category");
			this.lastEditor = (String) map.get("lastEditor");
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
