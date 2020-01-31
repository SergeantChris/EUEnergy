package Restlet_server_0.resources;

import org.restlet.resource.Get;

import com.mongodb.BasicDBObject;
import Restlet_server_0.Queries;


public class DayAheadTotalLoadForecastResource extends PowerResource{
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
		
		switch(TimeFrame) {
		case "date":
			res = Queries.getDateResult(filter, "DayAheadTotalLoadForecast");
			break;
		case "month":
			break;
		case "year":
			break;
		}

		return res;
	}
}
