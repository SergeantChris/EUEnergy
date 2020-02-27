package restlet_api.resources;

import org.restlet.resource.Get;

import com.mongodb.BasicDBObject;

import restlet_api.databaseLayer.*;
import restlet_api.utilities.GeneralUtilities;


public class ActualTotalLoadResource extends PowerResource{
	@Get
	public String getData() {
		String AreaName = getMandatoryAttribute("AreaName", "AreaName");
		String Resolution = getMandatoryAttribute("Resolution", "Resolution");
		String TimeFrame = getMandatoryAttribute("TimeFrame", "TimeFrame");
		String Date = getMandatoryAttribute("Date", "Date");
				
		BasicDBObject filter = new BasicDBObject();
		filter.append("AreaName", AreaName);
		filter.append("Resolution", Resolution);
		filter.append("TimeFrame", TimeFrame);
		filter.append("Date", Date);
				
		String res = "";
		
		String token = getRequest().getHeaders().getFirstValue("Token");
		String username = DatabaseManager.getUsernameFromToken(token);
		Integer quota = DatabaseManager.getQuota(username);
		
		if(DatabaseManager.isActiveToken(token)) {
			if(quota != 0) {
				switch(TimeFrame) {
				case "date":
					res = Queries.getDateResult(filter, "ActualTotalLoad");
					break;
				case "month":
					res = Queries.getMonthResult(filter, "ActualTotalLoad");
					break;
				case "year":
					res = Queries.getYearResult(filter, "ActualTotalLoad");
					break;
				}
				DatabaseManager.updateQuota(username, quota);
			}
			else
				res = GeneralUtilities.STATUS_OUT_OF_QUOTA;
		}
		else
			res = GeneralUtilities.STATUS_NOT_AUTHORIZED;
		
		return res;
	}
	
}
