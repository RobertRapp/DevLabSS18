package com.speechTokens.semanticAnalysis;

public class Testing {

	private static String jsonString = "{\r\n" + 
			"  \"head\": {\r\n" + 
			"    \"vars\": [ \"Instanz\" , \"Keyword\" ]\r\n" + 
			"  } ,\r\n" + 
			"  \"results\": {\r\n" + 
			"    \"bindings\": [\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostPlan\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"cost; expenses; expense; costs;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#BudgetPlan\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"budget; cost; plan;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Receipt\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"bill; receipt; cost; expense;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostStatement\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"cost; costs; expense; expenses; statement\" }\r\n" + 
			"      }\r\n" + 
			"    ]\r\n" + 
			"  }\r\n" + 
			"}\r\n" + 
			"";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(JsonHandler.handleJSONString(jsonString));
		
	}

}
