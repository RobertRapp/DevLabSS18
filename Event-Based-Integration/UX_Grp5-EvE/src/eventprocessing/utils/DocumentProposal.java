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
		public void addDocuments(ArrayList<Document> newDocuments) {
			
			for(Document d : documents) {
				System.out.println("Documentliste Elemtent:  "+d);
			}
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
		
		
		
		
}
