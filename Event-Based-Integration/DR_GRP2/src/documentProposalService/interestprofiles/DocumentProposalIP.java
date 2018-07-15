package documentProposalService.interestprofiles;

import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.json.JSONObject;

import com.google.api.client.json.Json;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.Document;
import eventprocessing.utils.TimeUtils;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;
import eventprocessing.utils.model.OWLResultUtils;

public class DocumentProposalIP extends AbstractInterestProfile {
	
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static final long serialVersionUID = -6108185466150892913L;
	private static Logger LOGGER = LoggerFactory.getLogger(DocumentProposalIP.class);


	@Override
	protected void doOnReceive(AbstractEvent event) { System.out.println(this.getClass().getSimpleName() + " : Event angekommen "+event.getType()+" - " + TimeUtils.getCurrentTime());
		System.out.println(this.getClass().getSimpleName() + " : Event angekommen "+event.getType()+" - " + TimeUtils.getCurrentTime());
		// TODO Auto-generated method stub
		System.out.println("in IP von Dr mit dem Event: " + event);
		String result = getModul(event);

		//_________________________________
		JSONObject jsonObject = new JSONObject(result);
		ArrayList<Document> docList = new ArrayList<Document>();
		AbstractEvent outputEvent = eventFactory.createEvent("AtomicEvent");
		outputEvent.setType("DocProposalEvent");	
		for (int i = 0; i < jsonObject.getJSONObject("results").getJSONArray("bindings").length() ; i++) {
			JSONObject bindingobject = jsonObject.getJSONObject("results").getJSONArray("bindings").getJSONObject(i);
				Document d = new Document(bindingobject.toString());
				outputEvent.add(new Property<Document>("Document",d));
				
			}
			System.out.println("OUTPUT ANSATZ "+outputEvent);
		
		//________________________________
		
		// Pushen eines Events
			
				try {
					//Neue FeedbackEvent
					System.out.println("DR schickt dieses Event an die GUI: " + outputEvent);
					this.getAgent().send(outputEvent, "DocProposal");
					
				} catch (NoValidEventException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoValidTargetTopicException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}

	// DocProposalEvent UNSER CODE
	
	static String sQueryAnfang = "	\n" + 
			"\n" + 
			"PREFIX asdf: <http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#>\n" + 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" + 
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" + 
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" + 
			"PREFIX as: <http://www.w3.org/ns/activitystreams#>\n" + 
			"PREFIX ordf: <http://purl.org/NET/ordf/>\n" + 
			"\n" + 
			"SELECT DISTINCT ?Document ?Author ?Editor ?Project ?FileName ?DocumentType ?URL ?LastChangeDate ?Category ?FileID\n" + 
			"  \n" + 
			"WHERE {\n" + 
			"?Document asdf:HasAuthor ?Author.\n" + 
			"?Document asdf:IsChangedBy ?Editor.\n";
			
	static String sQueryEnde =	"	\n" + 
			"?Document asdf:FileName ?FileName ; \n" + 
			"			asdf:DocumentType ?DocumentType ;\n"+
			"          asdf:URL ?URL;\n" + 
			"          asdf:LastChangeDate ?LastChangeDate;\n" + 
			"          asdf:FileID ?FileID;\n" + 
			"          rdf:type ?Category .\n" + 
			"?Category rdf:type owl:Class .\n" + 
			" }\n" + 
			"ORDER BY DESC(?LastChangeDate)";
	
	public static String addPerson(String keyword) {
		String sPerson = "{?Author asdf:Keyword ?KeywordAuthor . "
				+ "FILTER regex( str(?KeywordAuthor), \""
				+ keyword
				+ "\").} UNION {?Editor asdf:Keyword ?KeywordEditor . "
				+ "FILTER regex( str(?KeywordEditor), \""
				+ keyword
				+ "\")}";
		return sPerson;
	}
	
	public static String addProject(String keyword) {
		String sProject = "?Document asdf:IsCreatedFor ?Project.\n" + 
				"?Project asdf:Keyword ?KeywordProject.\n" + 
				" FILTER regex( str(?KeywordProject), \""
				+ keyword
				+ "\") .";
		return sProject;
	}
	
	
	public static String addKeyword(String keyword) {
		String sKeyword = "?Document asdf:Keyword ?Keyword.\n" + 
				" FILTER regex( str(?Keyword), \""
				+ keyword
				+ "\") .";
		return sKeyword;
	}

	public static String getModul(AbstractEvent event) {
		 String sFinishQuery = sQueryAnfang + "";
		// AbstractEvent event2 = eventFactory.createEvent("AtomicEvent");
		 Property<ArrayList<AbstractEvent>> keywords = (Property<ArrayList<AbstractEvent>>) EventUtils.findPropertyByKey(event, "keyword");
		 
		 
		 
		 if(EventUtils.findPropertyByKey(event, "project") != null) {
			 //event2.add(new Property<String>((String) event.getValueByKey("project")));
			 sFinishQuery = sFinishQuery + addProject((String) event.getValueByKey("project"));
		 }
		 
		 if(EventUtils.findPropertyByKey(event, "person") != null) {
			 //event2.add(new Property<String>((String) event.getValueByKey("project")));
			 sFinishQuery = sFinishQuery + addProject((String) event.getValueByKey("person"));
		 }
		 
		 if(EventUtils.findPropertyByKey(event, "keywords") != null) {
			 for(int i = 0; i < keywords.getValue().size(); i++) {
				//event2.add(new Property<String>(keywords.getValue().get(i).toString())); 
				 sFinishQuery = sFinishQuery + addKeyword(keywords.getValue().get(i).toString());
			 }
		 }
		 sFinishQuery = sFinishQuery + sQueryEnde; 
		 System.out.println(sFinishQuery);
		 return getProposal(sFinishQuery);
	}
	
	public static String getProposal(String sQuery) {
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://localhost:3030/ds" , sQuery);
		
		ResultSet resultSet = queryExecution.execSelect();		
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsJSON(outputStream, resultSet);
		
		String json = new String(outputStream.toByteArray());
		System.out.println(json);
		queryExecution.close();
		return json;
	}
	

}
