package restlet_api.databaseLayer;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import java.util.HashMap;
import java.util.Map;

import restlet_api.utilities.*;

public class Queries {
	
	
	
	public static JSONArray getDateResult(BasicDBObject filter, String dataset) {
					
		BasicDBObject dbo = new BasicDBObject();
		dbo.append("AreaName", filter.get("AreaName"));
		dbo.append("ResolutionCodeId", GeneralUtilities.getResolutionCodeId(filter.get("Resolution").toString()));
		String[] seperatedDate = filter.get("Date").toString().split("-");
		dbo.append("Day", Integer.parseInt(seperatedDate[2]));
		dbo.append("Month", Integer.parseInt(seperatedDate[1]));
		dbo.append("Year", Integer.parseInt(seperatedDate[0]));
		
		DatabaseManager man = DatabaseManager.getManager();
		
		BasicDBObject sorto = new BasicDBObject();

		sorto.append("DateTime", 1);
		if(dataset == "AggregatedGenerationPerType") {
			if(!filter.get("ProductionType").toString().equals("AllTypes")) 
				dbo.append("ProductionTypeId", GeneralUtilities.getProductionTypeId(filter.get("ProductionType").toString()));
			else 
				sorto.append("ProductionTypeId", 1);
		}
		
		JSONArray jsarr = new JSONArray();
		if(dataset != "ActualvsForecast") {
			FindIterable<Document> x = man.getQueryIterable(dataset, dbo).sort(sorto);
			MongoCursor<Document> cursor = x.iterator();
			try {
			while(cursor.hasNext()) {
				Document temp = cursor.next();
				JSONObject jso = JsonGenerator.mkJson(filter, dataset);
				jso.put("AreaTypeCode", GeneralUtilities.getAreaTypeText(Integer.parseInt(temp.get("AreaTypeCodeId").toString())));
				jso.put("MapCode", GeneralUtilities.getMapCodeText(Integer.parseInt(temp.get("MapCodeId").toString())));
				jso.put("DateTimeUTC", temp.get("DateTime"));
				jso.put("UpdateTimeUTC", temp.get("UpdateTime"));
				switch(dataset) {
				case "ActualTotalLoad":
					jso.put("ActualTotalLoadValue", temp.get("TotalLoadValue"));
					break;
				case "AggregatedGenerationPerType":
					if(filter.get("ProductionType").toString().equals("AllTypes")) {
						jso.put("ProductionType", GeneralUtilities.getProductionTypeText(Integer.parseInt(temp.get("ProductionTypeId").toString())));
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
		
		return jsarr;
	}
	
	
	// TODO: getMonth and getYear can be one method
	
	public static JSONArray getMonthResult(BasicDBObject filter, String dataset) {
		BasicDBObject dbo = new BasicDBObject();
		dbo.append("AreaName", filter.get("AreaName"));
		dbo.append("ResolutionCodeId", GeneralUtilities.getResolutionCodeId(filter.get("Resolution").toString()));
		String[] seperatedDate = filter.get("Date").toString().split("-");
		dbo.append("Month", Integer.parseInt(seperatedDate[1]));
		dbo.append("Year", Integer.parseInt(seperatedDate[0]));
		
		DatabaseManager man = DatabaseManager.getManager();
		
		BasicDBObject sorto = new BasicDBObject();

		sorto.append("Day", 1);
		if(dataset == "AggregatedGenerationPerType") {
			if(!filter.get("ProductionType").toString().equals("AllTypes")) 
				dbo.append("ProductionTypeId", GeneralUtilities.getProductionTypeId(filter.get("ProductionType").toString()));
		}
		
		JSONArray jsarr = new JSONArray();
		if(dataset != "ActualvsForecast") {
			AggregateIterable<Document> x = null;
			switch(dataset) {
			case "ActualTotalLoad":
				x = man.getGroupBySum(dataset, dbo, sorto, "Day", "TotalLoadValue");
				break;
			case "AggregatedGenerationPerType":
				Map<String, Object> multiIdMap = new HashMap<String, Object>();
				multiIdMap.put("groupField1", "$Day");
				multiIdMap.put("groupField2", "$ProductionTypeId");
				Document groupFields = new Document(multiIdMap);
				
				x = man.getGroupBySum(dataset, dbo, sorto, "Day", groupFields, "ActualGenerationOutput");
				break;
			case "DayAheadTotalLoadForecast":
				x = man.getGroupBySum(dataset, dbo, sorto, "Day", "TotalLoadValue");
				break;
			}
			MongoCursor<Document> cursor = x.iterator();
			try {
			while(cursor.hasNext()) {
				Document temp = cursor.next();
				JSONObject jso = JsonGenerator.mkJson(filter, dataset);
				jso.put("AreaTypeCode", GeneralUtilities.getAreaTypeText(Integer.parseInt(temp.get("AreaTypeCodeId").toString())));
				jso.put("MapCode", GeneralUtilities.getMapCodeText(Integer.parseInt(temp.get("MapCodeId").toString())));
				jso.put("Day", temp.get("Day"));
				switch(dataset) {
				case "ActualTotalLoad":
					jso.put("ActualTotalLoadByDayValue", temp.get("sum"));
					break;
				case "AggregatedGenerationPerType":
					if(filter.get("ProductionType").toString().equals("AllTypes")) {
						jso.put("ProductionType", GeneralUtilities.getProductionTypeText(Integer.parseInt(temp.get("ProductionTypeId").toString())));
					}
					jso.put("ActualGenerationOutputByDayValue", temp.get("sum"));
					break;
				case "DayAheadTotalLoadForecast":
					jso.put("DayAheadTotalLoadForecastByDayValue", temp.get("sum"));
					break;
				}
				jsarr.put(jso);
			}
			}catch(Exception e) {
			e.printStackTrace();
			}
		}
		else {
			AggregateIterable<Document> x1 = man.getGroupBySum("ActualTotalLoad", dbo, sorto, "Day", "TotalLoadValue");
			MongoCursor<Document> cursor = x1.iterator();
			AggregateIterable<Document> x2 = man.getGroupBySum("DayAheadTotalLoadForecast", dbo, sorto ,"Day", "TotalLoadValue");
			MongoCursor<Document> cursorDayAhead = x2.iterator();
			try {
			while(cursor.hasNext() && cursorDayAhead.hasNext()) {
				Document temp = cursor.next();
				Document tempDayAhead = cursorDayAhead.next();
				JSONObject jso = JsonGenerator.mkJson(filter, "ActualVSForecastedTotalLoad");
				jso.put("AreaTypeCode", GeneralUtilities.getAreaTypeText(Integer.parseInt(temp.get("AreaTypeCodeId").toString())));
				jso.put("MapCode", GeneralUtilities.getMapCodeText(Integer.parseInt(temp.get("MapCodeId").toString())));
				jso.put("Day", temp.get("Day"));
				jso.put("ActualTotalLoadByDayValue", temp.get("sum"));
				jso.put("DayAheadTotalLoadForecastByDayValue", tempDayAhead.get("sum"));
				jsarr.put(jso);
			}
			}catch(Exception e) {
			e.printStackTrace();
			}
		}
		
		return jsarr;
	}
	
	
	
	public static JSONArray getYearResult(BasicDBObject filter, String dataset) {
		BasicDBObject dbo = new BasicDBObject();
		dbo.append("AreaName", filter.get("AreaName"));
		dbo.append("ResolutionCodeId", GeneralUtilities.getResolutionCodeId(filter.get("Resolution").toString()));
		dbo.append("Year", Integer.parseInt(filter.get("Date").toString()));
		
		DatabaseManager man = DatabaseManager.getManager();
		
		BasicDBObject sorto = new BasicDBObject();

		sorto.append("Month", 1);
		if(dataset == "AggregatedGenerationPerType") {
			if(!filter.get("ProductionType").toString().equals("AllTypes")) 
				dbo.append("ProductionTypeId", GeneralUtilities.getProductionTypeId(filter.get("ProductionType").toString()));
		}
		
		JSONArray jsarr = new JSONArray();
		if(dataset != "ActualvsForecast") {
			AggregateIterable<Document> x = null;
			switch(dataset) {
			case "ActualTotalLoad":
				x = man.getGroupBySum(dataset, dbo, sorto, "Month", "TotalLoadValue");
				break;
			case "AggregatedGenerationPerType":
				Map<String, Object> multiIdMap = new HashMap<String, Object>();
				multiIdMap.put("groupField1", "$Month");
				multiIdMap.put("groupField2", "$ProductionTypeId");
				Document groupFields = new Document(multiIdMap);
				
				x = man.getGroupBySum(dataset, dbo, sorto, "Month", groupFields, "ActualGenerationOutput");
				break;
			case "DayAheadTotalLoadForecast":
				x = man.getGroupBySum(dataset, dbo, sorto, "Month", "TotalLoadValue");
				break;
			}
			MongoCursor<Document> cursor = x.iterator();
			try {
			while(cursor.hasNext()) {
				Document temp = cursor.next();
				JSONObject jso = JsonGenerator.mkJson(filter, dataset);
				jso.put("AreaTypeCode", GeneralUtilities.getAreaTypeText(Integer.parseInt(temp.get("AreaTypeCodeId").toString())));
				jso.put("MapCode", GeneralUtilities.getMapCodeText(Integer.parseInt(temp.get("MapCodeId").toString())));
				jso.put("Month", temp.get("Month"));
				switch(dataset) {
				case "ActualTotalLoad":
					jso.put("ActualTotalLoadByMonthValue", temp.get("sum"));
					break;
				case "AggregatedGenerationPerType":
					if(filter.get("ProductionType").toString().equals("AllTypes")) {
						jso.put("ProductionType", GeneralUtilities.getProductionTypeText(Integer.parseInt(temp.get("ProductionTypeId").toString())));
					}
					jso.put("ActualGenerationOutputByMonthValue", temp.get("sum"));
					break;
				case "DayAheadTotalLoadForecast":
					jso.put("DayAheadTotalLoadForecastByMonthValue", temp.get("sum"));
					break;
				}
				jsarr.put(jso);
			}
			}catch(Exception e) {
			e.printStackTrace();
			}
		}
		else {
			AggregateIterable<Document> x1 = man.getGroupBySum("ActualTotalLoad", dbo, sorto, "Month", "TotalLoadValue");
			MongoCursor<Document> cursor = x1.iterator();
			AggregateIterable<Document> x2 = man.getGroupBySum("DayAheadTotalLoadForecast", dbo, sorto ,"Month", "TotalLoadValue");
			MongoCursor<Document> cursorDayAhead = x2.iterator();
			try {
			while(cursor.hasNext() && cursorDayAhead.hasNext()) {
				Document temp = cursor.next();
				Document tempDayAhead = cursorDayAhead.next();
				JSONObject jso = JsonGenerator.mkJson(filter, "ActualVSForecastedTotalLoad");
				jso.put("AreaTypeCode", GeneralUtilities.getAreaTypeText(Integer.parseInt(temp.get("AreaTypeCodeId").toString())));
				jso.put("MapCode", GeneralUtilities.getMapCodeText(Integer.parseInt(temp.get("MapCodeId").toString())));
				jso.put("Month", temp.get("Month"));
				jso.put("ActualTotalLoadByMonthValue", temp.get("sum"));
				jso.put("DayAheadTotalLoadForecastByMonthValue", tempDayAhead.get("sum"));
				jsarr.put(jso);
			}
			}catch(Exception e) {
			e.printStackTrace();
			}
		}
		
		return jsarr;
	}
}