package hdm.developmentlab.ebi.eve_implementation.sessionContextService.interestprofiles;


import java.util.logging.Logger;

import com.speechTokens.EvE.interestProfiles.TokenizeInterestProfile;
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


public class User extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8805054035365429362L;
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static Logger LOGGER = LoggerFactory.getLogger(TokenizeInterestProfile.class);
	/**
	 * Empfängt UserEvents und speichert diese Information jeweils im korrekten SessionContext ab.
	 * @param arg0
	 */

	@Override
	protected void doOnReceive(AbstractEvent event) {
		System.out.println("in IP von DR ");
		System.out.println(event);
		
		/*
		 * 
		 * DR FAKE. Bekommt ein SentenceEvent vom Topic ChunkGeneration
		 */
		
		//FeedbackEvent
		String jsonString = "{\n" + 
				"  \"head\": {\n" + 
				"    \"vars\": [ \"Instanzname\" , \"Classname\" , \"Oberklasse\" , \"Beziehung\" , \"Instanzname2\" , \"Attribut\" , \"Name\" , \"Keyword\" ]\n" + 
				"  } ,\n" + 
				"  \"results\": {\n" + 
				"    \"bindings\": [\n" + 
				"      {\n" + 
				"        \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostPlan\" } ,\n" + 
				"        \"Classname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#ProjectControlling\" } ,\n" + 
				"        \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Document\" } ,\n" + 
				"        \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsCreatedFor\" } ,\n" + 
				"        \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#HighNet\" } ,\n" + 
				"        \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } ,\n" + 
				"        \"Name\": { \"type\": \"literal\" , \"value\": \"costplan\" } ,\n" + 
				"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"cost; expenses; expense; costs;\" }\n" + 
				"      } ,\n" + 
				"      {\n" + 
				"        \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostPlan\" } ,\n" + 
				"        \"Classname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#ProjectControlling\" } ,\n" + 
				"        \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Document\" } ,\n" + 
				"        \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#HasAuthor\" } ,\n" + 
				"        \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Jennifer_Tran\" } ,\n" + 
				"        \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } ,\n" + 
				"        \"Name\": { \"type\": \"literal\" , \"value\": \"costplan\" } ,\n" + 
				"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"cost; expenses; expense; costs;\" }\n" + 
				"      } ,\n" + 
				"      {\n" + 
				"        \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostPlan\" } ,\n" + 
				"        \"Classname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#ProjectControlling\" } ,\n" + 
				"        \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Document\" } ,\n" + 
				"        \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsChangedBy\" } ,\n" + 
				"        \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Jennifer_Tran\" } ,\n" + 
				"        \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } ,\n" + 
				"        \"Name\": { \"type\": \"literal\" , \"value\": \"costplan\" } ,\n" + 
				"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"cost; expenses; expense; costs;\" }\n" + 
				"      } ,\n" + 
				"      {\n" + 
				"        \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostStatement\" } ,\n" + 
				"        \"Classname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#ProjectControlling\" } ,\n" + 
				"        \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Document\" } ,\n" + 
				"        \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsCreatedFor\" } ,\n" + 
				"        \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#DokumentenRepräsentation\" } ,\n" + 
				"        \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } ,\n" + 
				"        \"Name\": { \"type\": \"literal\" , \"value\": \"cost statement\" } ,\n" + 
				"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"cost; costs; expense; expenses; statement\" }\n" + 
				"      } ,\n" + 
				"      {\n" + 
				"        \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostStatement\" } ,\n" + 
				"        \"Classname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#ProjectControlling\" } ,\n" + 
				"        \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Document\" } ,\n" + 
				"        \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#HasAuthor\" } ,\n" + 
				"        \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Vanessa_Keller\" } ,\n" + 
				"        \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } ,\n" + 
				"        \"Name\": { \"type\": \"literal\" , \"value\": \"cost statement\" } ,\n" + 
				"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"cost; costs; expense; expenses; statement\" }\n" + 
				"      } ,\n" + 
				"      {\n" + 
				"        \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostStatement\" } ,\n" + 
				"        \"Classname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#ProjectControlling\" } ,\n" + 
				"        \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Document\" } ,\n" + 
				"        \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsChangedBy\" } ,\n" + 
				"        \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Vanessa_Keller\" } ,\n" + 
				"        \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } ,\n" + 
				"        \"Name\": { \"type\": \"literal\" , \"value\": \"cost statement\" } ,\n" + 
				"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"cost; costs; expense; expenses; statement\" }\n" + 
				"      } ,\n" + 
				"      {\n" + 
				"        \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#BudgetPlan\" } ,\n" + 
				"        \"Classname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#ProjectControlling\" } ,\n" + 
				"        \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Document\" } ,\n" + 
				"        \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#HasAuthor\" } ,\n" + 
				"        \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Jennifer_Tran\" } ,\n" + 
				"        \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } ,\n" + 
				"        \"Name\": { \"type\": \"literal\" , \"value\": \"budget plan\" } ,\n" + 
				"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"budget; cost; plan;\" }\n" + 
				"      } ,\n" + 
				"      {\n" + 
				"        \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#BudgetPlan\" } ,\n" + 
				"        \"Classname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#ProjectControlling\" } ,\n" + 
				"        \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Document\" } ,\n" + 
				"        \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsChangedBy\" } ,\n" + 
				"        \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Jennifer_Tran\" } ,\n" + 
				"        \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } ,\n" + 
				"        \"Name\": { \"type\": \"literal\" , \"value\": \"budget plan\" } ,\n" + 
				"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"budget; cost; plan;\" }\n" + 
				"      } ,\n" + 
				"      {\n" + 
				"        \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Receipt\" } ,\n" + 
				"        \"Classname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#ProjectControlling\" } ,\n" + 
				"        \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Document\" } ,\n" + 
				"        \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#HasAuthor\" } ,\n" + 
				"        \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Jennifer_Tran\" } ,\n" + 
				"        \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } ,\n" + 
				"        \"Name\": { \"type\": \"literal\" , \"value\": \"receipt\" } ,\n" + 
				"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"bill; receipt; cost; expense;\" }\n" + 
				"      } ,\n" + 
				"      {\n" + 
				"        \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Receipt\" } ,\n" + 
				"        \"Classname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#ProjectControlling\" } ,\n" + 
				"        \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Document\" } ,\n" + 
				"        \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsChangedBy\" } ,\n" + 
				"        \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Jennifer_Tran\" } ,\n" + 
				"        \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } ,\n" + 
				"        \"Name\": { \"type\": \"literal\" , \"value\": \"receipt\" } ,\n" + 
				"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"bill; receipt; cost; expense;\" }\n" + 
				"      }\n" + 
				"    ]\n" + 
				"  }\n" + 
				"}";
		System.out.println(20);
		String jsonString1 = "{\r\n" + 
				"  \"head\": {\r\n" + 
				"    \"vars\": [ \"Instanz\" , \"Keyword\" ]\r\n" + 
				"  } ,\r\n" + 
				"  \"results\": {\r\n" + 
				"    \"bindings\": [\r\n" + 
				"      {\r\n" + 
				"        \"Instanz\": { \"type\": \"Project\"} ,\r\n" + 
				"        \"Keyword\": { \"type\": \"Highnet\"}\r\n" + 
				"      } ,\r\n" + 
				"      {\r\n" + 
				"        \"Instanz\": { \"type\": \"Welt\"} ,\r\n" + 
				"        \"Keyword\": { \"type\": \"feuer\"}\r\n" + 
				"      } ,\r\n" + 
				"      {\r\n" + 
				"        \"Instanz\": { \"type\": \"Wie\" } ,\r\n" + 
				"        \"Keyword\": { \"type\": \"geht\" }\r\n" + 
				"      } ,\r\n" + 
				"      {\r\n" + 
				"        \"Instanz\": { \"type\": \"es\"} ,\r\n" + 
				"        \"Keyword\": { \"type\": \"dir\" }\r\n" + 
				"      }\r\n" + 
				"    ]\r\n" + 
				"  }\r\n" + 
				"}\r\n" + 
				"";
		System.out.println(21);
		Chunker chunker = new Chunker();
		chunker.addChunkContent("welt"); // muss klein geschrieben sein!
		chunker.addChunkContent("test");
		chunker.addChunkContent("project");
		//chunker.addSemanticToChunk("welt", jsonString);
		chunker.addSemanticToChunk("test", jsonString1);
		System.out.println(22);
		
		AbstractEvent feedbackEvent = eventFactory.createEvent("AtomicEvent");
		feedbackEvent.setType("FeedbackEvent");
		feedbackEvent.add(new Property<String>("UserID", "123"));
		feedbackEvent.add(new Property<String>("SessionID", "456"));
		feedbackEvent.add(new Property<String>("SentenceID", "789"));
		feedbackEvent.add(new Property<>("Chunks", chunker.returnList()));

		feedbackEvent.add(new Property<String>("Keywords", "document"));
		System.out.println(23);
		System.out.println(24);
		
		System.out.println("Hier Hier Hier");
		System.out.println(feedbackEvent);
		
		try {
			this.getAgent().send(feedbackEvent, "SemanticChunks");
		} catch (NoValidEventException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
