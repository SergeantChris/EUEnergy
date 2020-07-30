package restlet_api;
import javax.net.ssl.SSLContext;

import org.restlet.data.Parameter;
import org.restlet.engine.ssl.DefaultSslContextFactory;
import org.restlet.util.Series;
public class PowerSSL extends DefaultSslContextFactory{
	public PowerSSL() {
		super();
		super.setKeyManagerAlgorithm("");
		this.setKeyManagerAlgorithm("");
	}
}
