package restlet_api.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import restlet_api.databaseLayer.DatabaseManager;

public class GeneralUtilities {
	
	public static Map<Integer,String> MapCodes;
	public static Map<Integer,String> ProductionTypeTexts;
	public static Map<String, Integer> ProductionTypeCodes;
	
	public static void Init() {
		MapCodes = new HashMap<Integer, String>();
		ProductionTypeTexts = new HashMap<Integer, String>();
		ProductionTypeCodes = new HashMap<String, Integer>();
		
		DatabaseManager man = DatabaseManager.getManager();
		BasicDBObject dbo = new BasicDBObject();
		
		FindIterable<Document> x;
		x = man.getQueryIterable("MapCode", dbo);
		MongoCursor<Document> cursor = x.iterator();
		try {
			while(cursor.hasNext()) {
				Document temp = cursor.next();
				Integer id = temp.getInteger("Id");
				String text = temp.getString("MapCodeText");
				MapCodes.put(id, text);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		x = man.getQueryIterable("ProductionType", dbo);
		cursor = x.iterator();
		try {
			while(cursor.hasNext()) {
				Document temp = cursor.next();
				Integer id = temp.getInteger("Id");
				String text = temp.getString("ProductionTypeText");
				ProductionTypeCodes.put(text, id);
				ProductionTypeTexts.put(id, text);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getProductionTypeId(String ProductionType) {

		return ProductionTypeCodes.get(ProductionType).intValue();
	}
	
	public static String getProductionTypeText(int productionTypeID) {
		return ProductionTypeTexts.get(new Integer(productionTypeID));
	}
	
	public static int getResolutionCodeId(String Resolution) {

		int ResolutionID = 0;
		switch(Resolution) {
		case "PT15M": ResolutionID = 1; break;
		case "PT30M": ResolutionID = 3; break;
		case "PT60M": ResolutionID = 2; break;
		}
		return ResolutionID;
	}
	
	public static String getResolutionText(int resolutionCodeID) {
		String resolutionText = "";
		switch(resolutionCodeID) {
		case 1: resolutionText = "PT15M"; break;
		case 3: resolutionText = "PT30M"; break;
		case 2: resolutionText = "PT60M"; break;
		}
		return resolutionText;
	}
	
	public static String getAreaTypeText(int AreaTypeID) {
		String result = "";
		switch(AreaTypeID) {
		case 1:
			result = "CTA";
			break;
		case 2:
			result = "BZN";
			break;
		case 3:
			result = "CTY";
			break;
		case 4:
			result = "MBA";
			break;
		case 5:
			result = "BZA";
			break;
		}
		return result;
	}
	
	public static String getMapCodeText(int mapCodeID) {
		return MapCodes.get(new Integer(mapCodeID));
	}
	
	public static String getRandomToken() {
		String token = "";
		String[] parts = new String[4];
		parts[0] = "";
		parts[1] = "";
		parts[2] = "";
		parts[3] = "";
		
		Random r = new Random();
		String alphabet = "QWERTYUIOPASDFGHJKLZXCVBNM0123456789";
		
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				parts[i] += alphabet.charAt(r.nextInt(alphabet.length()));
		
		token = parts[0] + '-' + parts[1] + '-' + parts[2] + '-' + parts[3];
		
		return token;
	}
	
	
}
