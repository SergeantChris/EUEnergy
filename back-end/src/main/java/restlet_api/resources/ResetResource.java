package restlet_api.resources;

import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import com.mongodb.BasicDBObject;

import restlet_api.databaseLayer.DatabaseManager;
import restlet_api.utilities.GeneralUtilities;

public class ResetResource extends PowerResource{
	@Post
	public String getPost(Representation resp) {
		String res = "";
		String token = getRequest().getHeaders().getFirstValue("Token");
		if(DatabaseManager.isAdmin(token)) {
			DatabaseManager man = DatabaseManager.getManager();
			man.dropCollection("ActualTotalLoad");
			man.dropCollection("AggregatedGenerationPerType");
			man.dropCollection("DayAheadTotalLoadForecast");
			BasicDBObject dbo1 = new BasicDBObject();
			BasicDBObject dbo2 = new BasicDBObject();
			dbo1.put("$ne", "admin");
			dbo2.put("User", dbo1);
			man.delManyItems("Users", dbo2);
			res = GeneralUtilities.STATUS_OK;
		}else {
			res = GeneralUtilities.STATUS_NOT_AUTHORIZED;
		}
		return res;
	}
}
