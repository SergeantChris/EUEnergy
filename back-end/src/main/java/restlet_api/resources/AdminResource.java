package restlet_api.resources;

import org.restlet.resource.Post;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoWriteException;

import restlet_api.databaseLayer.DatabaseManager;
import restlet_api.utilities.GeneralUtilities;

public class AdminResource extends PowerResource{
	@Post
	public String getPost() {
		String action = getMandatoryAttribute("Action", "Action");
		String res = "";
		String token = getRequest().getHeaders().getFirstValue("Token");
		if(DatabaseManager.isActiveToken(token)) {
			if(DatabaseManager.getUsernameFromToken(token).equals("admin")) {
				switch(action) {
				case "users":
					res = createUser();
					break;
				}
			}
			else
				res = GeneralUtilities.STATUS_NOT_AUTHORIZED;
		}
		else
			res = GeneralUtilities.STATUS_NOT_AUTHORIZED;		
		return res;
	}
	
	public String createUser() {
		String res = "";
		try {
			String username = getRequest().getHeaders().getFirstValue("User");
			String pass = getRequest().getHeaders().getFirstValue("Pass");
			int hashpass = pass.hashCode();
			BasicDBObject dbo = new BasicDBObject();
			
			dbo.append("User", username);
			dbo.append("Pass", hashpass);
			dbo.append("Quotas", 12);
			System.out.println("Dbo is " + dbo.toString());
			try {
				DatabaseManager.getManager().addItem("Users", dbo);
				res = GeneralUtilities.STATUS_OK;
				System.out.println("Res is OK");
			}catch(MongoWriteException e) {
				res =  GeneralUtilities.STATUS_BAD_REQUEST;
				System.out.println("Res is BAD");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
}
