package restlet_api.resources;

import org.restlet.resource.Post;

import restlet_api.databaseLayer.DatabaseManager;
import restlet_api.utilities.GeneralUtilities;

public class LogoutResource extends PowerResource{
	@Post
	public String getPost() {
		String token = getRequest().getHeaders().getFirstValue("Token");
		DatabaseManager.deleteToken(token);
		return GeneralUtilities.STATUS_OK;
	}
}
