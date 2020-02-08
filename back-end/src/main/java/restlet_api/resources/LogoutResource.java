package restlet_api.resources;

import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.Resource;

import restlet_api.databaseLayer.DatabaseManager;

public class LogoutResource extends PowerResource{
	@Post
	public String getPost() {
		String token = getRequest().getHeaders().getFirstValue("Token");
		
		DatabaseManager.deleteToken(token);
		return "OK\n";
	}
}
