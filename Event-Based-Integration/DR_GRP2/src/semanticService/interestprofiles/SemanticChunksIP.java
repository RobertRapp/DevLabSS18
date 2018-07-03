package semanticService.interestprofiles;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

import com.speechTokens.tokenizer.Chunker;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;

public class SemanticChunksIP extends AbstractInterestProfile{

	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static final long serialVersionUID = -6108185466150892913L;
	private static Logger LOGGER = LoggerFactory.getLogger(SemanticChunksIP.class);

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {		
		System.out.println("In IP von DR!!!!");
		System.out.println("Event erhalten: " + event);
		System.out.println("Hallo neu");
		//Chunker Objekt von Token abfangen
		ArrayList<Object> chunkslist = (ArrayList<Object>)	EventUtils.findPropertyByKey(event, "Chunks").getValue();
		Chunker chunkerObject = new Chunker();
		chunkerObject.parseArrayList(chunkslist);
		System.out.println(chunkerObject);
		// Alle chunks auslesen, die übergeben sind
		ArrayList<String> list = chunkerObject.readChunks();
		System.out.println("Länge Luste : " + list.size());
		// Chunk in der Ontologie durchsuchen
		for (int i = 0; i < list.size(); i++) {
			System.out.println("For schleife1 in i " + i);
			String jsonFile = getSemantic(list.get(i)); 
			chunkerObject.addSemanticToChunk(list.get(i), jsonFile);
		}
		System.out.println("ChunkerObjekt auslesen: " + chunkerObject);
		for (int i = 0; i < chunkerObject.size(); i++) {
			System.out.println("For schleife2 in i " + i);
			chunkerObject.getSemanticAt(i);	
			System.out.println(chunkerObject.getSemanticAt(i));
		}
		
		
		// Pushen eines Events
		AbstractEvent feedbackEvent = eventFactory.createEvent("AtomicEvent");
		feedbackEvent.setType("FeedbackEvent");
		// Eigenschaften von Token wieder hinzufügen
		feedbackEvent.add(event.getPropertyByKey("UserID"));
		System.out.println("userid: " + event.getPropertyByKey("UserID").getValue());
		feedbackEvent.add(event.getPropertyByKey("SessionID"));
		System.out.println("SessionID: " + event.getPropertyByKey("SessionID").getValue());
		feedbackEvent.add(event.getPropertyByKey("SentenceID"));
		//Unsere Semantic übergeben
		feedbackEvent.add(new Property<ArrayList<Object>>("Chunks", chunkerObject.returnList()));
		//System.out.println("Chunkerlist: " + chunkerObject.returnList());
		
		
		try {
			//Neue FeedbackEvent
			System.out.println("Das wird gesendet vom SemantikAgent: ");
			this.getAgent().send(feedbackEvent, "SemanticChunks");
			
		} catch (NoValidEventException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getSemantic(String chunk) {
		
		String keyword = chunk.toLowerCase();
		System.out.println("keyword: " + keyword);
		String sQuery = 
				"\n" + 
				"PREFIX asdf: <http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#>\n" + 
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" + 
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" + 
				"\n" + 
				"SELECT DISTINCT ?Instanzname ?Beziehung ?Instanzname2 ?Oberklasse ?Attribut ?Name ?Keyword\n" + 
				"WHERE {\n" + 
				"?Instanzname rdf:type ?Classname .\n" + 
				"?Classname rdf:type owl:Class .\n" + 
				"?Classname rdfs:subClassOf ?Oberklasse .\n" + 
				"  \n" + 
				"?Instanzname ?Beziehung ?Instanzname2 .\n" + 
				"?Beziehung rdf:type owl:ObjectProperty .\n" + 
				"  \n" + 
				"?Instanzname ?Attribut ?Name.\n" + 
				"?Attribut rdf:type owl:DatatypeProperty .\n" + 
				"  \n" + 
				"FILTER regex( str(?Attribut), \"Name\" ) .\n" + 
				"\n" + 
				"\n" + 
				"  \n" + 
				"?Instanzname asdf:Keyword ?Keyword \n" + 
				"FILTER regex( str(?Keyword), \""+
				keyword + 
				"\") \n" + 
				"\n" + 
				"}";
		
		System.out.println("squery: " + sQuery);
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/" , sQuery);
		System.out.println(queryExecution);
		ResultSet resultSet = queryExecution.execSelect();		
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsJSON(outputStream, resultSet);
		
		String json = new String(outputStream.toByteArray());
		System.out.println("____________");
		System.out.println(json);

		queryExecution.close();
		
		return(json);
	
	}

	
}
