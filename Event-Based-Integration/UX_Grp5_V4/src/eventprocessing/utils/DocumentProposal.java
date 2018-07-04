package eventprocessing.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class DocumentProposal {

	ArrayList<Document>  documents = new ArrayList<Document>();
	ArrayList<String> categories = new ArrayList<String>();
	

	int maxDocuments = 15;
		
	 	public ArrayList<Document> getDocuments() {
			return documents;
		}
	 	public ArrayList<String> getCategories() {
			return categories;
		}
		public void addDocuments(ArrayList<Document> newDocuments) {
			//Pr�fen ob Gesamtsumme der Dokumente die maximale Anzahl �bertrifft
			 if(documents.size() + newDocuments.size() >maxDocuments) {
				  int sumDeleteDocuments =  documents.size() + newDocuments.size() - maxDocuments;
				  	for (int i=0; i < sumDeleteDocuments; i++) {
					  documents.remove(i);
					  
				  	}					  
				}
					
			for (Document doc : newDocuments) {
				//Pr�fen ob ein Dokument einer neuen Kategorie zugeordnet ist. Wenn ja, dann Kategorienliste aktualisieren
				  if (!categories.contains(doc.categorie)) {
					   categories.add(doc.categorie);
				   }
				 documents.add(doc);			
			}
	  
		}
		public JSONObject toJson() {
			JSONObject docProposalJson = new JSONObject();
			JSONObject requestJson = new JSONObject();
			
//			JSONObject categoryInformation= new JSONObject();
			JSONObject document= new JSONObject();
			// Array f�r alle
			JSONArray childrenAllCategory = new JSONArray();
			
			for (String category : categories) {
				JSONArray docsOneCategory = new JSONArray();
				JSONObject childrenOneCategory = new JSONObject();
				for (Document doc : documents) {
					if (doc.categorie == category) {
						document= new JSONObject();
						document.put("Ersteller", doc.lastEditor);
						document.put("size", 50);
						document.put("name", doc.name);
						document.put("path", doc.path);
						document.put("fontcolor", "white");
						document.put("docID", doc.docID);
						document.put("category", doc.categorie);
						document.put("color", doc.getColor(doc.type));
						docsOneCategory.put(document);
						
					}
				}	
					//JSONObject categoryInformation= new JSONObject();
					childrenOneCategory.put("children", docsOneCategory);
					childrenOneCategory.put("fontcolor", "white");
					childrenOneCategory.put("color", "#B768F6");
					childrenOneCategory.put("name", category);
					//childrenOneCategory.put(categoryInformation);
					childrenAllCategory.put(childrenOneCategory);
					
			}
			docProposalJson.put("children", childrenAllCategory);
			docProposalJson.put("fontcolor", "black");
			docProposalJson.put("color", "white");
			docProposalJson.put("name", "docProposal");
			requestJson.put("docProposal", docProposalJson);
			requestJson.put("type", "newDocProposal");
			return requestJson;
			
		}
		
		
}
