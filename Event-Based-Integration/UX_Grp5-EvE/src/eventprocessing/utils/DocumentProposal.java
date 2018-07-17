package eventprocessing.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Beinhaltet eine ArrayList mit allen Dokumentenvorschlägen des Typs Document. Bei der Methode AddDocument
 * wird geprüft, ob die Summe aus aktuellen und neuen Dokumentenvorschlägen das gesetzte Maximum übersteigt.
 * Falls dies der Fall ist, werden die ältesten Dokumente gelöscht und durch die neuen ersetzt.
 * 
 * Zusätzlich wird bei jedem neuen Dokument geprüft, ob dieses eine neue Kategorie aufweist.
 * Alle Kategorien werden in einer ArrayList des Typs String gespeichert.
 * 
 */
public class DocumentProposal {

	private static ArrayList<Document>  documents = new ArrayList<Document>();
	private static ArrayList<String> categories = new ArrayList<String>();
	

	int maxDocuments = 15;
		
	 	public ArrayList<Document> getDocuments() {
			return documents;
		}
	 	public ArrayList<String> getCategories() {
			return categories;
		}
	 	
	 	public void clearProposal() {
	 		//
	 		documents.clear();
	 		categories.clear();
	 		
	 	}
		public void addDocuments(ArrayList<Document> newDocuments) {
			ArrayList<Document> removeEmptyDocs = new ArrayList<Document>();
			for(Document d : newDocuments) {
				if(d.getName().isEmpty()) {
					removeEmptyDocs.add(d);
				}
			}
			newDocuments.removeAll(removeEmptyDocs);
			
			
			
			//ArrayList<Document> documentList=documents;
			//documentList.addAll(newDocuments);
//			for(Document d : documentList) {
//				
//				
//				
//			}
			
			
			
			//Pr�fen ob Gesamtsumme der Dokumente die maximale Anzahl �bertrifft
			documents.addAll(newDocuments);
			if(documents.size() > 15) {
				ArrayList<Document> removableDocs = new ArrayList<Document>();
				for(int i = 0; (documents.size() - i) == 15; i ++) {
					removableDocs.add(documents.get(i));
				}
						documents.removeAll(removableDocs);
			}
					
//			for (Document doc : documentList) {
//				//Pr�fen ob ein Dokument einer neuen Kategorie zugeordnet ist. Wenn ja, dann Kategorienliste aktualisieren
//				  if (!categories.contains(doc.categorie)) {
//					   categories.add(doc.categorie);
//				   }
//				  documentList.add(doc);			
//			}
			
				ArrayList<String> newCategoryList = new ArrayList<>();
				for(Document document : documents) {
					if(!newCategoryList.contains(document.getCategorie())) newCategoryList.add(document.getCategorie());		
				}
				categories = newCategoryList;
			}
		
			
		
//			public void addDocuments(ArrayList<Document> newDocuments) {
//
//				documents.addAll(newDocuments);
//				if(documents.size() > 15) {
//					ArrayList<Document> removableDocs = new ArrayList<Document>();
//					for(int i = 0; (documents.size() - i) == 15; i ++) {
//						removableDocs.add(documents.get(i));
//					}
//							documents.removeAll(removableDocs);
//				}
//				for(Document document : documents) {
//									
//				if (!categories.contains(document.getCategorie())) {
//					   categories.add(document.getCategorie());
//				   }
//				}
//			}
			
			
		
		
//	 	public void addDocuments(ArrayList<Document> newDocuments) {
//	 		
//	 		
//			documents.addAll(newDocuments);
//			
//			
//			if(documents.size() > 15) {
//				ArrayList<Document> removableDocs = new ArrayList<Document>();
//				for(int i = 0; (documents.size() - i) == 15; i ++) {
//					removableDocs.add(documents.get(i));
//				}
//					
//						documents.removeAll(removableDocs);
//			}
//			
//			for (Document doc : newDocuments) {
//				//Pr�fen ob ein Dokument einer neuen Kategorie zugeordnet ist. Wenn ja, dann Kategorienliste aktualisieren
//				  if (!categories.contains(doc.categorie)) {
//					   categories.add(doc.categorie);
//				   }
//				  documents.add(doc);			
//			}
//		}
		
		
		
}
