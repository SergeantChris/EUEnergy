package restlet_api.utilities;

import org.json.*;
import com.mongodb.*;

public class JsonGenerator {
	
	public static JSONObject mkJson(BasicDBObject obj, String dataset) {
		JSONObject basicJson = new JSONObject().put("Source", "entso-e");
		basicJson.put("Dataset", dataset);
		String AreaName = obj.get("AreaName").toString();
		String Resolution = obj.get("Resolution").toString();
		String TimeFrame = obj.get("TimeFrame").toString();
		String Date = obj.get("Date").toString();
		String[] seperated = Date.split("-");
		if(dataset == "AggregatedGenerationPerType") {
			String ProductionType = obj.get("ProductionType").toString();
			basicJson = basicJson.put("ProductionType", ProductionType);
		}
		basicJson.put("AreaName", AreaName);
		basicJson.put("ResolutionCode", Resolution);
		switch(TimeFrame) {
		case "date":
			basicJson.put("Day", seperated[2]);
		case "month":
			basicJson.put("Month", seperated[1]);
		case "year":
			if(TimeFrame=="year")
			basicJson.put("Year", Date);
			else
			basicJson.put("Year", seperated[0]);
		}
		return basicJson;
	}
}