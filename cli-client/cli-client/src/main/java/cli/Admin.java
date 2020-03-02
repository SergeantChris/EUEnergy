package cli;

import gr.ntua.ece.softeng19b.client.RestAPI;
import picocli.CommandLine;
import picocli.CommandLine.*;

import java.io.File;
import java.util.concurrent.Callable;


@Command(
	    name="Admin",
		subcommands = {
		        NewUser.class,
		        UpdateUser.class,
		        UserStatus.class
		    }
	    )
public class Admin extends BasicCliArgs implements Callable<Integer> {
	

	@ArgGroup(exclusive = false, multiplicity = "0..1")
	File file;

    static class File {
    	@Option(
    	        names = {"-f","--file"},
    	        required = false,
    	        description = "Write file path"
    	        )
    	 String path;

    	@Parameters(arity="1", paramLabel = "DataSet", description = "Data set")
    	String dataset;
    }
	
   
	
	

    @Override
    public Integer call() throws Exception {
    	CommandLine cli = spec.commandLine();

        if (usageHelpRequested) {
            cli.usage(cli.getOut());
            return 0;
        }

        if(file==null) {
        	cli.usage(cli.getOut());
            return 0;
	       
        }
	    else {
	    	 try {
		            new RestAPI().importFile(file.dataset, new java.io.File(file.path).toPath());
		            return 0;
		        } catch (RuntimeException e) {
		            cli.getOut().println(e.getMessage());
		            e.printStackTrace(cli.getOut());
		            return -1;
		        }
	    }
    
    }

}
