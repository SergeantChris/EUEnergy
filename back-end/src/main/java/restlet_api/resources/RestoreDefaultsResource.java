package restlet_api.resources;

import org.restlet.resource.Post;

import restlet_api.databaseLayer.DatabaseManager;
import restlet_api.utilities.GeneralUtilities;

public class RestoreDefaultsResource extends PowerResource{
	@Post
	public String getPost() {
		String res = "";
		String token = getRequest().getHeaders().getFirstValue("Token");
		if(DatabaseManager.isAdmin(token)) {
			
			GeneralUtilities.executeSystemCommand(
					"./mongoInsertCSV data_defaults/ActualTotalLoad-10days.csv ActualTotalLoad");
			
			res = GeneralUtilities.STATUS_OK;
		}else {
			res = GeneralUtilities.STATUS_NOT_AUTHORIZED;
		}
		return res;
	}
}
