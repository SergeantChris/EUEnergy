package restlet_api.databaseLayer;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bson.Document;

import com.mongodb.BasicDBObject;

public class DatabaseManager {
	private static DatabaseManager dbManager = null;
	public static long lastQuotaUpdate;
	private MongoClient mongoClient;
	private MongoDatabase db;
	private static Map<String, String> tokenUsers;
	private static Map<String, String> userTokens;
	
	public static void Init() {
		tokenUsers = new HashMap<String, String>();
		userTokens = new HashMap<String, String>();
		lastQuotaUpdate = System.currentTimeMillis()/1000;
		(new Thread(new Runnable() {
			public void run() {
				periodUpd();
			}
		})).start();
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
		Document doc = Document.parse(dbo.toJson());
		db.getCollection(coll).insertOne(doc);
	}
	
	public void delItem(String coll, BasicDBObject dbo) {
		try {
			db.getCollection(coll).deleteOne(dbo);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delManyItems(String coll, BasicDBObject dbo) {
		try {
			db.getCollection(coll).deleteMany(dbo);
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
	
	private void updMany(String coll, BasicDBObject item, BasicDBObject newItem) {
		try {
			Document repl = new Document();
			repl.append("$set", newItem);
			db.getCollection(coll).updateMany(item, repl);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void periodUpd() {
		try {
			while(true) {
				TimeUnit.MINUTES.sleep(4);
				new DatabaseManager().updMany(
						"Users", 
						new BasicDBObject(), 
						new BasicDBObject().append("Quotas", 12)
						);
				System.out.println("Quota updated");
				lastQuotaUpdate = System.currentTimeMillis()/1000;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getTimeTillRefresh() {
		long timePassed = System.currentTimeMillis()/1000 - lastQuotaUpdate;
		return (int) (4*60-timePassed);
	}
	
	public static int getQuota(String username) {
		BasicDBObject dbo = new BasicDBObject().append("User", username);
		BasicDBObject retObj = new DatabaseManager().getItem("Users", dbo);
		return Integer.parseInt(retObj.get("Quotas").toString());
	}
	
	public static void updateQuota(String username, Integer quota) {
		BasicDBObject dbo = new BasicDBObject().append("User", username);
		BasicDBObject replObj = new BasicDBObject().append("Quotas", quota-1);
		new DatabaseManager().updItem("Users", dbo, replObj);
	}
	
	public static boolean isActiveToken(String token) {
		return tokenUsers.containsKey(token);
	}
	
	public static void deleteToken(String token) {
		String user = tokenUsers.get(token);
		tokenUsers.remove(token);
		userTokens.remove(user);
		System.out.println(token);
	}
	
	public static void insertToken(String token, String username) {
		if(!tokenUsers.containsKey(token)) {
			tokenUsers.put(token, username);
			userTokens.put(username, token);
		}
	}
	
	public static boolean userHasToken(String user) {
		return userTokens.containsKey(user);
	}
	
	public static String getTokenFromUsername(String username) {
		return userTokens.get(username);
	}
	
	public static String getUsernameFromToken(String token) {
		return tokenUsers.get(token);
	}
	
	public static boolean isAdmin(String token) {
		boolean res = false;
		if(DatabaseManager.isActiveToken(token))
			if(DatabaseManager.getUsernameFromToken(token).equals("admin"))
					res = true;
			
		return res;
	}
	
	public FindIterable<Document> getQueryIterable(String coll, BasicDBObject dbo){		
		return db.getCollection(coll).find(dbo);
	}
		
	AggregateIterable<Document> getGroupBySum(String coll, BasicDBObject dbo, BasicDBObject sort, String field, String sumfield) {
		return db.getCollection(coll).aggregate(
					Arrays.asList(
							Aggregates.match(dbo),
							Aggregates.group(
									"$"+field,
									Accumulators.first(field, "$"+field),
									Accumulators.first("AreaTypeCodeId", "$AreaTypeCodeId"),
									Accumulators.first("MapCodeId", "$MapCodeId"),
									Accumulators.sum("sum", "$"+sumfield)
							),
							Aggregates.sort(sort)
					)
				); 
	}
	
	AggregateIterable<Document> getGroupBySum(String coll, BasicDBObject dbo, BasicDBObject sort, String field, Document groupFields, String sumfield) {
		return db.getCollection(coll).aggregate(
					Arrays.asList(
							Aggregates.match(dbo),
							Aggregates.group(
									groupFields,
									Accumulators.first(field, "$"+field),
									Accumulators.first("ProductionTypeId", "$ProductionTypeId"),
									Accumulators.first("AreaTypeCodeId", "$AreaTypeCodeId"),
									Accumulators.first("MapCodeId", "$MapCodeId"),
									Accumulators.sum("sum", "$"+sumfield)
							),
							Aggregates.sort(sort)
					)
				); 
	}
	
	public void dropCollection(String coll) {
		MongoCollection<Document> myColl = db.getCollection(coll);
		myColl.drop();
	}
	
}
