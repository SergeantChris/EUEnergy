package restlet_api.resources;

import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import com.mongodb.BasicDBObject;

import restlet_api.databaseLayer.DatabaseManager;
import restlet_api.utilities.GeneralUtilities;

public class LoginResource extends PowerResource{
	@Post
	public String getPost(Representation repr) {
		String username = getRequest().getHeaders().getFirstValue("User");
		String pass = getRequest().getHeaders().getFirstValue("Pass");
		
		int hashpass = pass.hashCode();
		
		String res = "";
		
		BasicDBObject uData = new BasicDBObject();
		uData.append("User", username);
		uData.append("Pass", hashpass);
		
		BasicDBObject lookup = new BasicDBObject();
		
		lookup = DatabaseManager.getManager().getItem("Users", uData);
		
		if(lookup.isEmpty()) {
			res = GeneralUtilities.STATUS_BAD_REQUEST;
		}else {
			if(!DatabaseManager.userHasToken(username)) {
				String token = GeneralUtilities.getRandomToken();
				DatabaseManager.insertToken(token, username);
				res = new JSONObject().put("Token", token).toString(1);
			}else {
				res = new JSONObject().put("Token", DatabaseManager.getTokenFromUsername(username)).toString(1);
			}
		}
		return res;
	}
}
