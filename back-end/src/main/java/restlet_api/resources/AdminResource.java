package restlet_api.resources;

import org.json.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

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
				// case "Atl|Agpt|Datlf"
				}
			}
			else
				res = GeneralUtilities.STATUS_NOT_AUTHORIZED;
		}
		else
			res = GeneralUtilities.STATUS_NOT_AUTHORIZED;		
		return res;
	}
	
	@Put
	public String getPut() {
		String action = getMandatoryAttribute("Action", "Action");
		String res = "";
		String token = getRequest().getHeaders().getFirstValue("Token");
		if(DatabaseManager.isActiveToken(token)) {
			if(DatabaseManager.getUsernameFromToken(token).equals("admin")) {
				res = updateUser(action);
			}
			else
				res = GeneralUtilities.STATUS_NOT_AUTHORIZED;
		}
		else
			res = GeneralUtilities.STATUS_NOT_AUTHORIZED;
		return res;
	}
	
	@Get
	public String getGet() {
		String action = getMandatoryAttribute("Action", "Action");
		String res = "";
		String token = getRequest().getHeaders().getFirstValue("Token");
		if(DatabaseManager.isActiveToken(token)) {
			if(DatabaseManager.getUsernameFromToken(token).equals("admin")) {
				res = showUser(action);
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
			String email = getRequest().getHeaders().getFirstValue("Email");
			String quotas = getRequest().getHeaders().getFirstValue("Quotas");
			int hashpass = pass.hashCode();
			
			BasicDBObject dbo = new BasicDBObject();
			dbo.append("User", username);
			dbo.append("Pass", hashpass);
			dbo.append("Email", email);
			dbo.append("Quotas", Integer.parseInt(quotas));
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
			res = GeneralUtilities.STATUS_BAD_REQUEST;
		}
		return res;
	}
	
	public String updateUser(String username) {
		String res = "";
		try {
			String pass = getRequest().getHeaders().getFirstValue("Pass");
			String email = getRequest().getHeaders().getFirstValue("Email");
			String quotas = getRequest().getHeaders().getFirstValue("Quotas");
			int hashpass = pass.hashCode();

			BasicDBObject filter = new BasicDBObject();
			BasicDBObject dbo = new BasicDBObject();
			filter.append("User", username);
			dbo.append("Pass", hashpass);
			dbo.append("Email", email);
			dbo.append("Quotas", Integer.parseInt(quotas));
			System.out.println("Dbo is " + dbo.toString());
			try {
				DatabaseManager.getManager().updItem("Users", filter, dbo);
				res = GeneralUtilities.STATUS_OK;
				System.out.println("Res is OK");
			}catch(MongoWriteException e) {
				res =  GeneralUtilities.STATUS_BAD_REQUEST;
				System.out.println("Res is BAD");
			}
		}catch(Exception e){
			e.printStackTrace();
			res = GeneralUtilities.STATUS_BAD_REQUEST;
		}
		return res;
	}
	
	public String showUser(String username) {
		String res = "";
		try {
			BasicDBObject filter = new BasicDBObject();
			filter.append("User", username);
			try {
				BasicDBObject doc =  DatabaseManager.getManager().getItem("Users", filter);
				String token = DatabaseManager.getTokenFromUsername(username);
				doc.append("Token", token);
				res = GeneralUtilities.STATUS_OK + doc.toJson();
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
