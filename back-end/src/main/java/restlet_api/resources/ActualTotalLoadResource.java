package restlet_api.resources;

import org.restlet.resource.Get;

import com.mongodb.BasicDBObject;

import restlet_api.databaseLayer.Queries;


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

		return res;
	}
	
}
