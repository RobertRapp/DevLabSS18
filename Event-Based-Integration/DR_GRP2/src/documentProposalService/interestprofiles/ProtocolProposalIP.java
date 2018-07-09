package documentProposalService.interestprofiles;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.TimeUtils;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;

public class ProtocolProposalIP extends AbstractInterestProfile{
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static final long serialVersionUID = -6108185466150892913L;
	private static Logger LOGGER = LoggerFactory.getLogger(DocumentProposalIP.class);


	
	protected void doOnReceive(AbstractEvent event) { System.out.println(this.getClass().getSimpleName() + " : Event angekommen "+event.getType()+" - " + TimeUtils.getCurrentTime());
		// TODO Auto-generated method stub

		getQuery(event);
	
		
		// Pushen eines Events
				AbstractEvent proProposalEvent = eventFactory.createEvent("AtomicEvent");
				proProposalEvent.setType("DocProposalEvent");
				proProposalEvent.add(new Property<String>("Documentname",EventUtils.findPropertyByKey(event, "Document").getValue().toString()));
				proProposalEvent.add(new Property<String>("Author",EventUtils.findPropertyByKey(event, "Author").getValue().toString()));
				proProposalEvent.add(new Property<String>("Editor",EventUtils.findPropertyByKey(event, "Editor").getValue().toString()));
				proProposalEvent.add(new Property<String>("Project",EventUtils.findPropertyByKey(event, "Project").getValue().toString()));
				proProposalEvent.add(new Property<String>("Filename",EventUtils.findPropertyByKey(event, "Filename").getValue().toString()));
				proProposalEvent.add(new Property<String>("URL",EventUtils.findPropertyByKey(event, "URL").getValue().toString()));
				proProposalEvent.add(new Property<String>("LastChangeDate",EventUtils.findPropertyByKey(event, "LastChangeDate").getValue().toString()));
				proProposalEvent.add(new Property<String>("Category",EventUtils.findPropertyByKey(event, "Category").getValue().toString()));
				proProposalEvent.add(new Property<String>("FileID",EventUtils.findPropertyByKey(event, "FileID").getValue().toString()));

				
				try {
					//Neue FeedbackEvent
					this.getAgent().send(proProposalEvent, "ProProposal");
					
				} catch (NoValidEventException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoValidTargetTopicException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}

	// DocProposalEvent UNSER CODE
	
	

	public static void getQuery(AbstractEvent event) {
		
		 String participant1 = null;
		 String participant2 = null;

		 if(EventUtils.findPropertyByKey(event, "participant1") != null) {
			participant1 = (String) event.getValueByKey("participant1");
		 }
		 
		 if(EventUtils.findPropertyByKey(event, "participant2") != null) {
			participant2 = (String) event.getValueByKey("participant2");
		 }
		 
		 String sQuery = "PREFIX asdf: <http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#>\n" + 
		 		"PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" + 
		 		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
		 		"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" + 
		 		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" + 
		 		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" + 
		 		"\n" + 
		 		"\n" + 
		 		"SELECT DISTINCT ?Participant1 ?Participant2 ?Conversation ?Document ?FileName ?DocumentType ?URL ?Category ?Project ?LastChangeDate\n" + 
		 		"WHERE {\n" + 
		 		" 	?Conversation asdf:ConversationHasMember ?Participant1 .\n" + 
		 		"  	?Conversation asdf:ConversationHasMember ?Participant2.\n" + 
		 		"\n" + 
		 		"	?Participant1 asdf:Keyword ?Keyword1 . "
		 		+ "FILTER regex( str(?Keyword1), \""
		 		+ participant1
		 		+ "\").\n" + 
		 		"	?Participant2 asdf:Keyword ?Keyword2 . "
		 		+ "FILTER regex( str(?Keyword2), \""
		 		+ participant2
		 		+ "\").\n" + 
		 		"\n" + 
		 		"?Conversation asdf:HasTopicOf ?Project.\n" + 
		 		"?Conversation asdf:IsDescribedByProtocol ?Document.\n" + 
		 		"?Document asdf:LastChangeDate ?LastChangeDate;\n" + 
		 		"          asdf:FileName ?FileName; \n" + 
		 		"			asdf:DocumentType ?DocumentType; \n"	+
		 		"          asdf:URL ?URL;\n" + 
		 		"          asdf:FileID ?FileID;\n" + 
		 		"          rdf:type ?Category .\n" + 
		 		"?Category rdf:type owl:Class .\n" + 
		 		"}\n" + 
		 		"ORDER BY DESC(?LastChangeDate)";
		 
		getProposal(sQuery);
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
