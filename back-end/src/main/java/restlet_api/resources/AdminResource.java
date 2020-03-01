package restlet_api.resources;

import org.json.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoWriteException;

import restlet_api.databaseLayer.DatabaseManager;
import restlet_api.utilities.GeneralUtilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.restlet.data.MediaType;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.representation.Representation;

public class AdminResource extends PowerResource{
	@Post
	public String getPost(Representation entity) throws Exception {
		String action = getMandatoryAttribute("Action", "Action");
		String res = "";
		String token = getRequest().getHeaders().getFirstValue("Token");
		if(DatabaseManager.isActiveToken(token)) {
			if(DatabaseManager.getUsernameFromToken(token).equals("admin")) {
				switch(action) {
				case "users":
					res = createUser();
					break;
				case "ActualTotalLoad":
				case "AggregatedGenerationPerType":
				case "DayAheadTotalLoadForecast":
					res = createCollection(action, entity);
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
				int quotas = DatabaseManager.getQuota(username);
				doc.remove("_id");
				doc.append("Token", token);
				doc.append("Quotas", quotas);
				doc.append("Next_quota_refresh_seconds", DatabaseManager.getTimeTillRefresh());
				doc.append("status", "200 OK");
				res = doc.toJson();
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
	
	public String createCollection(String coll, Representation entity) throws Exception {
		String result = null;
	    if (entity != null) {
	        if (MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(), true)) {
	            // 1/ Create a factory for disk-based file items
	            DiskFileItemFactory factory = new DiskFileItemFactory();
	            factory.setSizeThreshold(1000000240);

	            // 2/ Create a new file upload handler based on the Restlet
	            // FileUpload extension that will parse Restlet requests and
	            // generates FileItems.
	            RestletFileUpload upload = new RestletFileUpload(factory);

	            // 3/ Request is parsed by the handler which generates a
	            // list of FileItems
	            FileItemIterator fileIterator = upload.getItemIterator(entity);

	            // Process only the uploaded item called "fileToUpload"
	            // and return back
	            boolean found = false;
	            while (fileIterator.hasNext() && !found) {
	                FileItemStream fi = fileIterator.next();
	                if (fi.getFieldName().equals("fileToUpload")) {
	                    found = true;
	                    File theFile = new File("theFile.csv");
	                    FileWriter writer  = new FileWriter("theFile.csv");
	                    // consume the stream immediately, otherwise the stream
	                    // will be closed.
	                    
	                    StringBuilder sb = new StringBuilder();
	                    BufferedReader br = new BufferedReader(
	                            new InputStreamReader(fi.openStream()));
	                    String line = null;
	                    while ((line = br.readLine()) != null) {
	                        sb.append(line);
	                        sb.append('\n');
	                    }
	                    sb.append("\n");
	                    System.out.println(sb.toString());
	                    writer.write(sb.toString());
	                    writer.close();
	                    
	                    DatabaseManager.getManager().dropCollection(coll);
	                    GeneralUtilities.executeSystemCommand("./mongoInsertCSV theFile.csv "+coll);
	                    
	                    theFile.delete();
	                    
	                    result = GeneralUtilities.STATUS_OK;
	                }
	            }
	        } else {
	        	result = GeneralUtilities.STATUS_BAD_REQUEST;
	        }
	    }
	    return result;
	}
}
