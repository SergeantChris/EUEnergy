package Restlet_server_0;


import java.io.UnsupportedEncodingException;

import org.bson.Document;
import org.restlet.resource.Get;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

import Restlet_server_0.DatabaseManager;
import Restlet_server_0.resources.PowerResource;

public class TestHandler extends PowerResource{
	@Get
	public String getSomething() throws UnsupportedEncodingException {
		
		return "";
	}
}
