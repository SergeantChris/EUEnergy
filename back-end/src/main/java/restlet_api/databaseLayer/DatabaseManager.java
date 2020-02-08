package restlet_api.databaseLayer;

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
import java.util.HashMap;
import java.util.Map;

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
	private static Map<String, String> activeTokens;
	
	public static void Init() {
		activeTokens = new HashMap<String, String>();
	}
	
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
	
	public BasicDBObject getItem(String coll, BasicDBObject filter) {
		BasicDBObject res = new BasicDBObject();
		try {
			res = BasicDBObject.parse(db.getCollection(coll).find(filter).first().toJson());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public void addItem(String coll, BasicDBObject dbo) {
		try {
			Document doc = Document.parse(dbo.toJson());
			db.getCollection(coll).insertOne(doc);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delItem(String coll, BasicDBObject dbo) {
		try {
			db.getCollection(coll).deleteOne(dbo);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updItem(String coll, BasicDBObject item, BasicDBObject newItem) {
		try {
			Document repl = new Document();
			repl.append("$set", newItem);
			db.getCollection(coll).updateOne(item, repl);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isActiveToken(String token) {
		return activeTokens.containsKey(token);
	}
	
	public void deleteToken(String token) {
		activeTokens.remove(token);
	}
	
	public void insertToken(String token, String username) {
		activeTokens.put(token, username);
	}
	
	public FindIterable<Document> getQueryIterable(String coll, BasicDBObject dbo){		
		return db.getCollection(coll).find(dbo);
	}
	
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
