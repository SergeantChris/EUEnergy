package restlet_api.resources;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

import restlet_api.databaseLayer.DatabaseManager;

public class TestResource extends PowerResource{
	@Post
	public static synchronized String getPost() {
		return "Time since last update\n" + DatabaseManager.lastQuotaUpdate;
	}
	
	@Get
	public static synchronized String getGet() {
		return "Time since last update\n" + DatabaseManager.lastQuotaUpdate;
	}
}
