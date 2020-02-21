package restlet_api.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.json.JSONObject;
import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.data.Header;
import org.restlet.engine.adapter.HttpRequest;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.util.Series;

import com.mongodb.BasicDBObject;

import restlet_api.databaseLayer.DatabaseManager;
import restlet_api.utilities.GeneralUtilities;

public class LoginResource extends PowerResource{
	@Post
	public String getPost() {
		String username = getRequest().getHeaders().getFirstValue("User");
		String pass = getRequest().getHeaders().getFirstValue("Pass");
		int hashpass = pass.hashCode();
		
		String res = "";
		
		BasicDBObject uData = new BasicDBObject();
		uData.append("Name", username);
		uData.append("Pass", hashpass);
		
		BasicDBObject lookup = new BasicDBObject();
		
		lookup = DatabaseManager.getManager().getItem("Users", uData);
		
		if(lookup.isEmpty()) {
			res = GeneralUtilities.STATUS_BAD_REQUEST;
		}else {
			if(!DatabaseManager.userHasToken(username)) {
				String token = GeneralUtilities.getRandomToken();
				DatabaseManager.insertToken(token, username);
				res = new JSONObject().put("Token", token).toString();
			}else {
				res = new JSONObject().put("Token", DatabaseManager.getTokenFromUsername(username)).toString();
			}
		}
		return res;
	}
}
