package restlet_api;


import java.io.UnsupportedEncodingException;

import org.bson.Document;
import org.restlet.resource.Get;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

import restlet_api.DatabaseManager;
import restlet_api.resources.PowerResource;

public class TestHandler extends PowerResource{
	@Get
	public String getSomething() throws UnsupportedEncodingException {
		
		return "";
	}
}
