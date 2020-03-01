package cli;

import gr.ntua.ece.softeng19b.client.RestAPI;
import picocli.CommandLine;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;


@Command(
	    name="Admin",
		subcommands = {
		        NewUser.class
		        
		    }
	    )
public class Admin extends BasicCliArgs implements Callable<Integer> {
	


	

    @Override
    public Integer call() throws Exception {
    	CommandLine cli = spec.commandLine();

        if (usageHelpRequested) {
            cli.usage(cli.getOut());
            return 0;
        }

        try {
            new RestAPI().healthCheck();
            return 0;
        } catch (RuntimeException e) {
            cli.getOut().println(e.getMessage());
            e.printStackTrace(cli.getOut());
            return -1;
        }
    
    }

}
