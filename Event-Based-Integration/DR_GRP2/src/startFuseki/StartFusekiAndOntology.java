package startFuseki;

import java.io.ByteArrayOutputStream;

import org.apache.jena.fuseki.embedded.FusekiServer;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.core.DatasetImpl;

public class StartFusekiAndOntology {

	public static void main(String[] args) {
		Model model = ModelFactory.createDefaultModel() ;
		System.out.println(model);
		model.read("C:\\Users\\rrapp\\Desktop\\Programme\\apache-jena-fuseki-3.8.0\\20180702_Ontoogie.owl");
		System.out.println("modelreaded"+model.toString());
		Dataset ds = new DatasetImpl(model);
		FusekiServer server = FusekiServer.make(3030, "ds", ds.asDatasetGraph());
		server.start();
		
		String sQuery = "PREFIX asdf: <http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#>\r\n" + 
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>\r\n" + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
				"PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n" + 
				"\r\n" + 
				"SELECT DISTINCT ?Instanzname ?Beziehung ?Instanzname2 ?Oberklasse ?Attribut ?Name ?Keyword\r\n" + 
				"WHERE {\r\n" + 
				"?Instanzname rdf:type ?Classname .\r\n" + 
				"?Classname rdf:type owl:Class .\r\n" + 
				"?Classname rdfs:subClassOf ?Oberklasse .\r\n" + 
				"  \r\n" + 
				"?Instanzname ?Beziehung ?Instanzname2 .\r\n" + 
				"?Beziehung rdf:type owl:ObjectProperty .\r\n" + 
				"  \r\n" + 
				"?Instanzname ?Attribut ?Name.\r\n" + 
				"?Attribut rdf:type owl:DatatypeProperty .\r\n" + 
				"  \r\n" + 
				"FILTER regex( str(?Attribut), \"Name\" ) .\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"  \r\n" + 
				"?Instanzname asdf:Keyword ?Keyword .\r\n" + 
				"FILTER regex( str(?Keyword), \"costs\") \r\n" + 
				"\r\n" + 
				"}";
		
		QueryExecution queryExecution = QueryExecutionFactory.				
				sparqlService("http://localhost:3030/ds" , sQuery);
		
		ResultSet resultSet = queryExecution.execSelect();		
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsJSON(outputStream, resultSet);
		
		String json = new String(outputStream.toByteArray());
		System.out.println(json);
		queryExecution.close();
	}
}
