package restlet_api.resources;

import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.Resource;

public class LogoutResource extends PowerResource{
	@Post
	public String getPost() {
		String token = getRequest().getHeaders().getFirstValue("X-OBSERVATORY-AUTH");
		return "OK";
	}
}
