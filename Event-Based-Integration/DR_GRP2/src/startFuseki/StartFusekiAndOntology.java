package startFuseki;

import java.io.InputStream;
import java.nio.file.Paths;

import org.apache.jena.fuseki.embedded.FusekiServer;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.core.DatasetImpl;
import org.apache.jena.util.FileManager;

public class StartFusekiAndOntology {

	public static void main(String[] args) {
		Model model = ModelFactory.createDefaultModel() ;	
		String rootStorePath = Paths.get("ontology.owl").toString();
		System.out.println(rootStorePath);
		model.read(rootStorePath);
		System.out.println("modelreaded"+model.toString());
		Dataset ds = new DatasetImpl(model);
		FusekiServer server = FusekiServer.make(3030, "ds", ds.asDatasetGraph());
		server.start();
	}
}
