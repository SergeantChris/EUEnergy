package restlet_api.resources;

import org.restlet.resource.Post;

import com.mongodb.BasicDBObject;

import restlet_api.databaseLayer.DatabaseManager;

public class AdminResource extends PowerResource{
	@Post
	public String getPost() {
		String action = getMandatoryAttribute("Action", "Action");
		String res = "";
		switch(action) {
		case "users":
			createUser();
			break;
		}
		
		return res;
	}
	
	public void createUser() {
		try {
		String username = getRequest().getHeaders().getFirstValue("User");
		String pass = getRequest().getHeaders().getFirstValue("Pass");
		int hashpass = pass.hashCode();
		BasicDBObject dbo = new BasicDBObject();
		
		dbo.append("Name", username);
		dbo.append("Pass", hashpass);
		
		DatabaseManager.getManager().addItem("Users", dbo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
