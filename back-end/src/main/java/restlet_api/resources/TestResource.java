package restlet_api.resources;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class TestResource extends PowerResource{
	@Post
	public static synchronized String getPost() {
		return "STRING\n";
	}
	
	@Get
	public static synchronized String getGet() {
		return "STRING\n";
	}
}
