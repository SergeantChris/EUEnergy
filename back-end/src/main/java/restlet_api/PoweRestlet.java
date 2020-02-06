package restlet_api;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import restlet_api.resources.ActualTotalLoadResource;
import restlet_api.resources.ActualvsForecastResource;
import restlet_api.resources.AggregatedGenerationPerTypeResource;
import restlet_api.resources.DayAheadTotalLoadForecastResource;
import restlet_api.utilities.GeneralUtilities;

public class PoweRestlet extends Application{
	public static void main(String[] args) throws Exception {
		GeneralUtilities.Init();
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
	
	public Restlet createInboundRoot() {
	      // Create a router restlet.
	      Router router = new Router(getContext());
	      // Attach the resources to the router.
	      
	      router.attach("/ActualTotalLoad/{AreaName}/{Resolution}/{TimeFrame}/{Date}",
	    		  ActualTotalLoadResource.class);
	      router.attach("/AggregatedGenerationPerType/{AreaName}/{ProductionType}/{Resolution}/{TimeFrame}/{Date}",
	    		  AggregatedGenerationPerTypeResource.class);
	      router.attach("/DayAheadTotalLoadForecast/{AreaName}/{Resolution}/{TimeFrame}/{Date}",
	    		  DayAheadTotalLoadForecastResource.class);
	      router.attach("/ActualvsForecast/{AreaName}/{Resolution}/{TimeFrame}/{Date}",
	    		  ActualvsForecastResource.class);
	      // Return the root router
	      return router;
	  }
}
