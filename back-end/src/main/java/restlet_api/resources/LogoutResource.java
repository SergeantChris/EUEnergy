package restlet_api.resources;

import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import restlet_api.databaseLayer.DatabaseManager;
import restlet_api.utilities.GeneralUtilities;

public class LogoutResource extends PowerResource{
	@Post
	public String getPost(Representation resp) {
		String token = getRequest().getHeaders().getFirstValue("Token");
		
		if(!DatabaseManager.isActiveToken(token))
			return GeneralUtilities.STATUS_BAD_REQUEST;
		DatabaseManager.deleteToken(token);
		return GeneralUtilities.STATUS_OK;
	}
}
