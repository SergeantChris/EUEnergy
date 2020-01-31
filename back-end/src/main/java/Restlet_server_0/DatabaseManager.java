package Restlet_server_0;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.DBObject;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;

public class DatabaseManager {
	private static DatabaseManager dbManager = null;
	private MongoClient mongoClient;
	private MongoDatabase db;

	private DatabaseManager() {
		try{
			//db at localhost:default port
			mongoClient = MongoClients.create();
			db = mongoClient.getDatabase("Entsoe");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static DatabaseManager getManager() {
		if(dbManager == null)
			dbManager = new DatabaseManager();
		return dbManager;
	}
	
	public FindIterable<Document> getQueryIterable(String coll, BasicDBObject dbo){		
		return db.getCollection(coll).find(dbo);
	}
	/**
	 * 
	 * Returns the sum of the float value represented in a field of a document iterable collection
	 * 
	 * @param it The iterable pointing to the collection
	 * @param field The name of the field that contains the float value
	 * @return Returns the sum of the field values in a String format
	 */
	
	public String getFloatFieldSum(FindIterable<Document> it, String field) {
		MongoCursor<Document> cursor = it.iterator();
		Double sum = new Double(0);
		try {
			while(cursor.hasNext()) {
				Document temp = cursor.next();
				sum = sum + Double.parseDouble(temp.get(field).toString());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sum.toString();
	}
	
	AggregateIterable<Document> getGroupBySum(String coll, BasicDBObject dbo, String field, String sumfield) {
		return db.getCollection(coll).aggregate(
					Arrays.asList(
							Aggregates.match(dbo),
							Aggregates.group(field, Accumulators.sum("sum", sumfield))
					)
				); 
	}
	
}
