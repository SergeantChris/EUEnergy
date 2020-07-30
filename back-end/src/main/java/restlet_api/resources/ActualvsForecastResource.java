package restlet_api.resources;

import org.json.JSONArray;
import org.restlet.resource.Get;

import com.mongodb.BasicDBObject;

import restlet_api.databaseLayer.DatabaseManager;
import restlet_api.databaseLayer.Queries;
import restlet_api.utilities.GeneralUtilities;


public class ActualvsForecastResource extends PowerResource{
	@Get
	public String getData() {
		String AreaName = getMandatoryAttribute("AreaName", "AreaName");
		String Resolution = getMandatoryAttribute("Resolution", "Resolution");
		String TimeFrame = getMandatoryAttribute("TimeFrame", "TimeFrame");
		String Date = getMandatoryAttribute("Date", "Date");
		
		String type = getRequest().getResourceRef().getQueryAsForm().getFirstValue("type");
		
		BasicDBObject filter = new BasicDBObject();
		filter.append("AreaName", AreaName);
		filter.append("Resolution", Resolution);
		filter.append("TimeFrame", TimeFrame);
		filter.append("Date", Date);
		
		String res = "";
		JSONArray jarr = new JSONArray();
		String token = getRequest().getHeaders().getFirstValue("Token");
		
		if(DatabaseManager.isActiveToken(token)) {
			String username = DatabaseManager.getUsernameFromToken(token);
			Integer quota = DatabaseManager.getQuota(username);
			if(quota != 0) {		
				switch(TimeFrame) {
				case "date":
					jarr = Queries.getDateResult(filter, "ActualvsForecast");
					break;
				case "month":
					jarr = Queries.getMonthResult(filter, "ActualvsForecast");
					break;
				case "year":
					jarr = Queries.getYearResult(filter, "ActualvsForecast");
					break;
				}
				DatabaseManager.updateQuota(username, quota);
			}
			else
				res = GeneralUtilities.STATUS_OUT_OF_QUOTA;
		}
		else
			res = GeneralUtilities.STATUS_NOT_AUTHORIZED;
		
		if((res!=GeneralUtilities.STATUS_NOT_AUTHORIZED)&&(res!=GeneralUtilities.STATUS_OUT_OF_QUOTA))
		if(type.equals("csv")) {
			res = GeneralUtilities.csvFromJson(jarr);
		}else {
			res = jarr.toString(1);
		}
		
		return res;
	}
}
