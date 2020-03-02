package restlet_api.tests;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import com.mongodb.BasicDBObject;

import restlet_api.databaseLayer.DatabaseManager;
import restlet_api.databaseLayer.Queries;
import restlet_api.utilities.GeneralUtilities;

public class Tests {
	@Test
	public void testAdd() {
		System.out.println("This test method should be run");
	    assertEquals(42, Integer.sum(19, 23));
	}
	
	@Test
	public void testTokenLifecycle() {
		DatabaseManager.testInit();
		if(!DatabaseManager.userHasToken("admin")) {
			String token = GeneralUtilities.getRandomToken();
			DatabaseManager.insertToken(token, "admin");
		}
		String token = DatabaseManager.getTokenFromUsername("admin");
		assertEquals(true, DatabaseManager.isActiveToken(token));
		DatabaseManager.deleteToken(token);
		assertEquals(false, DatabaseManager.isActiveToken(token));
	}
	
	@Test
	public void testUserManipulation() {
		BasicDBObject dbo = new BasicDBObject();
		dbo.append("User", "test");
		dbo.append("Pass", "1234");
		dbo.append("Email", "test@gmail.com");
		dbo.append("Quotas", 10);
		DatabaseManager.getManager().addItem("Users", dbo);
		
		BasicDBObject filter = new BasicDBObject();
		filter.append("User", "test");
		BasicDBObject doc =  DatabaseManager.getManager().getItem("Users", filter);
		assertEquals("test@gmail.com", doc.get("Email"));
		
		BasicDBObject dbo2 = new BasicDBObject();
		dbo2.append("Pass", "1234");
		dbo2.append("Email", "test@ntua.gr");
		dbo2.append("Quotas", 10);
		DatabaseManager.getManager().updItem("Users", filter, dbo2);
		doc =  DatabaseManager.getManager().getItem("Users", filter);
		assertEquals("test@ntua.gr", doc.get("Email"));
		DatabaseManager.getManager().delItem("Users", filter);
	}
	
	@Test
	public void testThreadCreation() {
		(new Thread(new Runnable() {
			public void run() {
				try {
					while(true) {
						TimeUnit.MINUTES.sleep(1);
						assertEquals("I'm here", "I'm here");					
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		})).start();
		assertEquals("And I'm there", "And I'm there");
		return;
	}
	
	@Test
	public void testYearDataEndpoints() {
		BasicDBObject filter = new BasicDBObject();
		filter.append("AreaName", "Serbia");
		filter.append("Resolution", "PT60");
		filter.append("TimeFrame", "year");
		filter.append("Date", "2018");
		BasicDBObject filter2 = new BasicDBObject();
		filter2 = filter.append("ProductionType", "AllTypes");
		boolean t1 = !Queries.getYearResult(filter, "ActualTotalLoad").equals(null);
		assertEquals(true, t1);
		boolean t2 = !Queries.getYearResult(filter2, "AggregatedGenerationPerType").equals(null);
		assertEquals(true, t2);
		boolean t3 = !Queries.getYearResult(filter, "DayAheadTotalLoadForecast").equals(null);
		assertEquals(true, t3);
		boolean t4 = !Queries.getYearResult(filter, "ActualvsForecast").equals(null);
		assertEquals(true, t4);
	}
}
