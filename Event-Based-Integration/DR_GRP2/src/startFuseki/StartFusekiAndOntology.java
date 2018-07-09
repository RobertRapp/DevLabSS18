package startFuseki;

import org.apache.jena.fuseki.embedded.FusekiServer;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.core.DatasetImpl;

public class StartFusekiAndOntology {

	public static void main(String[] args) {
		Model model = ModelFactory.createDefaultModel() ;
		System.out.println(model);
		model.read("../DR_GRP2/ontology/ontology.owl");
		System.out.println("modelreaded"+model.toString());
		Dataset ds = new DatasetImpl(model);
		FusekiServer server = FusekiServer.make(3030, "ds", ds.asDatasetGraph());
		server.start();
	}
}
