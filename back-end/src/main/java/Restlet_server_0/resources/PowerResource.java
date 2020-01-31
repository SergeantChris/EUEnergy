package Restlet_server_0.resources;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class PowerResource extends ServerResource{
	
	private static final String EMPTY_STRING = "";
	
	protected String getAttributeDecoded(String attr) {
        String value = getAttribute(attr);
        try {
			return value == null ?  null : sanitize(URLDecoder.decode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "";
    }

    protected String getMandatoryAttribute(String attr, String message) {
        String value = getAttributeDecoded(attr);
        if (value.length() == 0) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, message);
        }
        return value;
    }

    static String sanitize(String s) {
        return s == null ? EMPTY_STRING : s.trim();
    }
}
