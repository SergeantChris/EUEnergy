package restlet_api.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.data.Header;
import org.restlet.engine.adapter.HttpRequest;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.util.Series;

public class LoginResource extends PowerResource{
	@Post
	public String getPost() {
		String username = getRequest().getHeaders().getFirstValue("User");
		String pass = getRequest().getHeaders().getFirstValue("Pass");
		
		return "Post + " + username + " " + pass + " \n";
	}
}
