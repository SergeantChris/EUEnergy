package restlet_api.resources;
import org.restlet.resource.Get;

import com.mongodb.BasicDBObject;

import restlet_api.databaseLayer.DatabaseManager;
import restlet_api.utilities.GeneralUtilities;

public class HealthCheckResource extends PowerResource{
	
	@Get
	public String getCheck() {
		String res = "";
		DatabaseManager man = DatabaseManager.getManager();
		if(man.getItem("Users", new BasicDBObject()).isEmpty()) {
			res =  GeneralUtilities.STATUS_BAD_REQUEST;
		}
		else
			res = GeneralUtilities.STATUS_OK;
		return res;
	}
}