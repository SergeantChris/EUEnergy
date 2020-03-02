package tests;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import gr.ntua.ece.softeng19b.client.Format;
import gr.ntua.ece.softeng19b.client.RestAPI;

public class Tests {	
	@Test
	public void testTokenLifecycle() {
		new RestAPI().login("admin", "321nimda");
		assertEquals(true, new RestAPI().isLoggedIn());
		new RestAPI().logout();
		assertEquals(false, new RestAPI().isLoggedIn());
	}
	
	@Test
	public void testUserManipulation() {
		new RestAPI().addUser("test", "1234", "test@gmail.com", 10);
		assertEquals("test@gmail.com", new RestAPI().getUser("test").getEmail());
		new RestAPI().updateUser("test", "1234", "test@ntua.gr", 10);
		assertEquals("test@ntua.gr", new RestAPI().getUser("test").getEmail());
		MongoClient mongoClient;
		MongoDatabase db;
		mongoClient = MongoClients.create();
		db = mongoClient.getDatabase("Entsoe");
		BasicDBObject filter = new BasicDBObject();
		filter.append("User", "test");
		db.getCollection("Users").deleteOne(filter);
	}
	
	@Test
	public void testYearDataEndpoints() {
		boolean t1 = !new RestAPI().getActualTotalLoad("Serbia", "PT60", "year", "2018", Format.JSON).isEmpty();
		assertEquals(true, t1);
		boolean t2 = !new RestAPI().getAggregatedGenerationPerType("Serbia", "AllTypes", "PT60", "year", "2018", Format.JSON).isEmpty();
		assertEquals(true, t2);
		boolean t3 = !new RestAPI().getDayAheadTotalLoadForecast("Serbia", "PT60", "year", "2018", Format.JSON).isEmpty();
		assertEquals(true, t3);
		boolean t4 = !new RestAPI().getActualvsForecast("Serbia", "PT60", "year", "2018", Format.JSON).isEmpty();
		assertEquals(true, t4);
	}
}