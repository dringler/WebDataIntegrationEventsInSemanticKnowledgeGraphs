import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.sparql.engine.http.QueryExceptionHTTP;
/**
* Query DBpedia for the Data Analysis of the Event Class.
*
* @author  Daniel Ringler
* @version 1.0
* @since   2016-11-01 
*/
public class QueryDBpedia {
	public static void main(String[] args) {
		//Model m = ModelFactory.createDefaultModel();
		String service = "http://dbpedia.org/sparql";
		
		//CHECK DBpedia connection
		String queryTest = "ASK {}";
		QueryExecution qeTest = QueryExecutionFactory.sparqlService(service, queryTest);
		
		try {
			if(qeTest.execAsk()) {
				System.out.println(service + " is up");
				
				// call method to count event classes
				//countInstancesOfEventClasses(service);
				
				//get all properties of the event instances in desc importance
				//eventProperties(service);
				
				// TIME-RELATED PROPERTIES
				String dboTimeArray[] = getDboTimeArray();
				getPropertyDatatypes(service, dboTimeArray, true);
				
				String dbpTimeArray[] = getDbpTimeArray();
				getPropertyDatatypes(service, dbpTimeArray, false);
			}
		// check connection catch block
		} catch (QueryExceptionHTTP e) {
			System.out.println(service + " is down");
		} finally {
			qeTest.close();
			System.out.println("DONE");
		}
	}
	
	private static void getPropertyDatatypes(String service, String[] propertyArray, boolean isDbo) {
		for (String queryStringVar : propertyArray) {
			String queryString = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" + 
								 "PREFIX dbp: <http://dbpedia.org/property/>\n"+
								 	"select ?datatype (COUNT(DISTINCT ?x) AS ?iCount) WHERE {\n"+
								 		"select (datatype(?y) AS ?datatype) ?x where {\n"+
								 			"?x  a dbo:Event .\n";
			if (isDbo) {
				queryString = queryString + "?x dbo:"+ queryStringVar +" ?y .}\n";
			} else {
				queryString = queryString + "?x dbp:"+ queryStringVar +" ?y .}\n";
										
			}
			queryString = queryString + 
						"GROUP BY ?y (datatype(?y)) ?x }\n" +
					"GROUP BY ?datatype\n"+
					"ORDER BY DESC(COUNT(DISTINCT ?x))";
			String type = "db";
			if (isDbo) {
				type = type + "o";
			} else {
				type = type + "p";
			}
			System.out.println("Datatype for " + type + ":" + queryStringVar);
			queryDBpedia(service, queryString, true);
		}
		
	}

	private static String[] getDboTimeArray() {
		String dboTimeArray[] = {"time", "date", "startDate", "endDate", "foundingYear"}; //, "birthDate", "deathDate"};
		return dboTimeArray;
	}
	private static String[] getDbpTimeArray() {
		String dbpTimeArray[] = {"date", "electionDate", "next", "time", "first", "last", //"dateOfBirth",
				"yearEnd", "yearStart", //"dateOfDeath",
				"birthDate"};
		
		return dbpTimeArray;
	}

	/**
	   * Get all properties of instances of the event class.
	   * Sort by instance count for importance of properties.
	   * @param service URL of the SPARQL endpoint
	   * @return Nothing
	   */
	public static void eventProperties(String service) {
		String queryString = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" + 
				"SELECT ?property (COUNT(DISTINCT ?x) AS ?xC)\n" +
				"WHERE {\n" +
					"?x a dbo:Event .\n" +
					"?x ?property ?object ."+
				"}\n"+
				"GROUP BY ?property\n"+
				"ORDER BY DESC(COUNT(DISTINCT ?x))";
		queryDBpedia(service, queryString, true);	
	}
	
	
	 /**
	   * Count the instances of the event class and all subclasses.
	   * @param service URL of the SPARQL endpoint
	   * @return Nothing
	   */
	public static void countInstancesOfEventClasses(String service) {
		// COUNT EVENT CLASSES
		//add all dbo:classes to the array
		String queryStringArray[] = {"Event",
									"Competition",			
									"Contest",
									"LifeCycleEvent",			
									"PersonalEvent",	
									"Birth",
									"Death",
									"Divorce",	
									"Marriage",
									"NaturalEvent",		
									"Earthquake",
									"SolarEclipse",		
									"StormSurge",
									"SocietalEvent",			
									"AcademicConference",		
									"Attack",
									"Convention",
									"Election",	
									"FilmFestival",		
									"Meeting",
									"MilitaryConflict",		
									"MusicFestival",
									"Rebellion",
									"SpaceMission",	
									"SportsEvent",
									"CyclingCompetition",	
									"FootballMatch",
									"GrandPrix",
									"InternationalFootballLeagueEvent",	
									"MixedMartialArtsEvent",
									"NationalFootballLeagueEvent",	
									"Olympics",
									"OlympicEvent",
									"Race",
									"CyclingRace",
									"HorseRace",
									"MotorRace",
									"Tournament",
									"GolfTournament",
									"SoccerTournament",
									"TennisTournament",
									"WomensTennisAssociationTournament",
									"WrestlingEvent"};
		//for each class in array
		for (String queryStringVar : queryStringArray) {
			//create queryString
			String queryString = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" + 
					"SELECT\n" +
						"(COUNT(DISTINCT ?" + queryStringVar.toLowerCase() + ") AS ?"+queryStringVar.toLowerCase()+"C)\n"+ 
					"WHERE {\n" +
							"?"+ queryStringVar.toLowerCase() + " a dbo:"+queryStringVar +".\n" +
					"}";
			//System.out.println(queryString);
			//execute query
			queryDBpedia(service, queryString, false);	
		}
	}
	 /**
	   * Query DBpedia and print results.
	   * @param service 	URL of the SPARQL endpoint
	   * @param queryString 	Query to fire against the endpoint
	   * @param printBlock  use ResultSetFormatterm, else print line-wise
	   * @return Nothing
	   */
	public static void queryDBpedia(String service, String queryString, boolean printBlock) {
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		ResultSet results = qe.execSelect();
		//print output
		if (printBlock) {
		System.out.println(ResultSetFormatter.asText(results));
		} else {
			while (results.hasNext()) {
				String output = results.next().toString();
				System.out.println(output);
			}
		}
		
	}
}