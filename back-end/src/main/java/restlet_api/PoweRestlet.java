package restlet_api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.engine.application.CorsFilter;
import org.restlet.routing.Router;

import com.mongodb.BasicDBObject;

import restlet_api.databaseLayer.DatabaseManager;
import restlet_api.resources.ActualTotalLoadResource;
import restlet_api.resources.ActualvsForecastResource;
import restlet_api.resources.AdminResource;
import restlet_api.resources.AggregatedGenerationPerTypeResource;
import restlet_api.resources.DayAheadTotalLoadForecastResource;
import restlet_api.resources.LoginResource;
import restlet_api.resources.LogoutResource;
import restlet_api.utilities.GeneralUtilities;

public class PoweRestlet extends Application{
	public static void main(String[] args) throws Exception {
		Init();
		runServer(8765);
	}  

	public static void runServer(int port) throws Exception {
		// Create a component.
		Component component = new Component();
		component.getServers().add(Protocol.HTTP, port);
		// Create an application (this class).
		Application application = new PoweRestlet();
		// Attach the application to the component with a defined contextRoot.
		String contextRoot = "/energy/api";
		component.getDefaultHost().attach(contextRoot, application);
		component.start();
	}
	
	public static void Init() {
		GeneralUtilities.Init();
		DatabaseManager.Init();
	}
	
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		
	  	router.attach("/ActualTotalLoad/{AreaName}/{Resolution}/{TimeFrame}/{Date}",
	  			ActualTotalLoadResource.class);
	  	router.attach("/AggregatedGenerationPerType/{AreaName}/{ProductionType}/{Resolution}/{TimeFrame}/{Date}",
	  			AggregatedGenerationPerTypeResource.class);
	  	router.attach("/DayAheadTotalLoadForecast/{AreaName}/{Resolution}/{TimeFrame}/{Date}",
	  			DayAheadTotalLoadForecastResource.class);
	  	router.attach("/ActualvsForecast/{AreaName}/{Resolution}/{TimeFrame}/{Date}",
	  			ActualvsForecastResource.class);
	  	router.attach("/Login",
	  			LoginResource.class);
	  	router.attach("/Logout",
	  			LogoutResource.class);
	  	router.attach("/Admin/{Action}",
	  			AdminResource.class);
      
		CorsFilter corsFilter = new CorsFilter(getContext(), router);
		corsFilter.setAllowedOrigins(new HashSet(Arrays.asList("*")));
		corsFilter.setAllowedCredentials(true);
		corsFilter.setAllowedHeaders(new HashSet(Arrays.asList("*")));
		corsFilter.setDefaultAllowedMethods(new HashSet(Arrays.asList(Method.GET, Method.PUT, Method.POST, Method.DELETE)));
		corsFilter.setAllowingAllRequestedHeaders(true);
		corsFilter.setSkippingResourceForCorsOptions(true);
		corsFilter.setMaxAge(10);
		return corsFilter;
  
	      
	  }
}
