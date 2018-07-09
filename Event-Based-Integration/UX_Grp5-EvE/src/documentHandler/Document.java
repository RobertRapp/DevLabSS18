package documentHandler;

public class Document {
	
	int docID;
	String name;
	String type;
	String path;
	String size;
	String lastEditor;
	String color;
	String lastEdit;
	String context;
	String categorie;
	long docProposalID;
	
		public Document(int docID, String name, String type, String path, String size, String lastEditor, 
			String lastEdit, long docProposalID, String categorie) {
		super();
		this.docID = docID;
		this.name = name;
		this.type = type;
		this.path = path;
		this.size = size;
		this.lastEditor = lastEditor;
		this.lastEdit = lastEdit;
		this.docProposalID = docProposalID;
		this.categorie = categorie;
	}
		
		public String getColor(String type) {
			switch(type) {
			case "doc":
					color = "#4269a5";
					break;	
			case "präsentaion":
					color = "#dc6141";
					break;	
			case "tabelle":
				color = "green";
				break;
			case "type":
				color = "yellow";
				break;
			default:
					color = "#39825a";
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

		public int getDocID() {
			return docID;
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
