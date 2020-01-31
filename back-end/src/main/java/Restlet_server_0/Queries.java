package Restlet_server_0;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import Restlet_server_0.utilities.GeneralUtilities;

public class Queries {
	
	
	
	public static String getDateResult(BasicDBObject filter, String dataset) {
					
		BasicDBObject dbo = new BasicDBObject();
		dbo.append("AreaName", filter.get("AreaName"));
		dbo.append("ResolutionCodeId", GeneralUtilities.getResolutionCodeId(filter.get("Resolution").toString()));
		String[] seperatedDate = filter.get("Date").toString().split("-");
		dbo.append("Day", Integer.parseInt(seperatedDate[2]));
		dbo.append("Month", Integer.parseInt(seperatedDate[1]));
		dbo.append("Year", Integer.parseInt(seperatedDate[0]));
		
		DatabaseManager man = DatabaseManager.getManager();
		
		BasicDBObject sorto = new BasicDBObject();
		
		if(dataset == "AggregatedGenerationPerType") {
			if(!filter.get("ProductionType").toString().equals("AllTypes")) 
				dbo.append("ProductionTypeId", GeneralUtilities.getProductionTypeId(filter.get("ProductionType").toString()));
			else 
				sorto.append("ProductionTypeId", 1);
		}
		sorto.append("DateTime", 1);
		
		JSONArray jsarr = new JSONArray();
		if(dataset != "ActualvsForecast") {
			FindIterable<Document> x = man.getQueryIterable(dataset, dbo).sort(sorto);
			MongoCursor<Document> cursor = x.iterator();
			try {
			while(cursor.hasNext()) {
				Document temp = cursor.next();
				JSONObject jso = JsonGenerator.mkJson(filter, dataset);
				jso.put("AreaTypeCode", temp.get("AreaTypeCodeId"));
				jso.put("MapCode", temp.get("MapCodeId"));
				jso.put("DateTimeUTC", temp.get("DateTime"));
				jso.put("UpdateTimeUTC", temp.get("UpdateTime"));
				//debug = jso.toString();
				switch(dataset) {
				case "ActualTotalLoad":
					jso.put("ActualTotalLoadValue", temp.get("TotalLoadValue"));
					break;
				case "AggregatedGenerationPerType":
					if(filter.get("ProductionType").toString().equals("AllTypes")) {
						jso.put("ProductionType", temp.get("ProductionTypeId")); //conversion to name
					}
					jso.put("ActualGenerationOutputValue", temp.get("ActualGenerationOutput"));
					break;
				case "DayAheadTotalLoadForecast":
					jso.put("DayAheadTotalLoadForecastValue", temp.get("TotalLoadValue"));
					break;
				}
				jsarr.put(jso);
			}
			}catch(Exception e) {
			e.printStackTrace();
			}
		}
		else {
			FindIterable<Document> x1 = man.getQueryIterable("ActualTotalLoad", dbo).sort(sorto);
			MongoCursor<Document> cursor = x1.iterator();
			FindIterable<Document> x2 = man.getQueryIterable("DayAheadTotalLoadForecast", dbo).sort(sorto);
			MongoCursor<Document> cursorDayAhead = x2.iterator();
			try {
			while(cursor.hasNext() && cursorDayAhead.hasNext()) {
				Document temp = cursor.next();
				Document tempDayAhead = cursorDayAhead.next();
				JSONObject jso = JsonGenerator.mkJson(filter, "ActualVSForecastedTotalLoad");
				jso.put("AreaTypeCode", temp.get("AreaTypeCodeId"));
				jso.put("MapCode", temp.get("MapCodeId"));
				jso.put("DateTimeUTC", temp.get("DateTime"));
				jso.put("ActualTotalLoadValue", temp.get("TotalLoadValue"));
				jso.put("DayAheadTotalLoadForecastValue", tempDayAhead.get("TotalLoadValue"));
				jsarr.put(jso);
			}
			}catch(Exception e) {
			e.printStackTrace();
			}
		}
		
		return dbo.toJson() + '\n' + jsarr.toString(1);
	}
	
	public static String getMonthResult(BasicDBObject filter, String dataset) {
		
		BasicDBObject dbo = new BasicDBObject();
		dbo.append("AreaName", filter.get("AreaName"));
		dbo.append("ResolutionCodeId", GeneralUtilities.getResolutionCodeId(filter.get("Resolution").toString()));
		String[] seperatedDate = filter.get("Date").toString().split("-");
		dbo.append("Month", Integer.parseInt(seperatedDate[1]));
		dbo.append("Year", Integer.parseInt(seperatedDate[0]));
		
		DatabaseManager man = DatabaseManager.getManager();
		
		BasicDBObject sorto = new BasicDBObject();
		
		if(dataset == "AggregatedGenerationPerType") {
			if(!filter.get("ProductionType").toString().equals("AllTypes")) 
				dbo.append("ProductionTypeId", GeneralUtilities.getProductionTypeId(filter.get("ProductionType").toString()));
			else 
				sorto.append("ProductionTypeId", 1);
		}
		sorto.append("DateTime", 1);
		
		JSONArray jsarr = new JSONArray();
		
		return dbo.toJson() + '\n' + jsarr.toString(1);
	}
	public static String getYearResult(FindIterable<Document> x) {
		//MongoCursor<Document> cursor = x.iterator();
		return "";
	}
}
