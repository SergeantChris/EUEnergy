package cli;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine;
import picocli.CommandLine.*;
import gr.ntua.ece.softeng19b.client.RestAPI;


@Command(name="UserStatus")
public class UserStatus extends BasicCliArgs implements Callable<Integer>{
	@Option(
	        names = {"-u","--username"},
	        required = true,
	        description = "Write the username."
	    )
	protected String username;
	
	
	
	
	@Override
	public Integer call() throws Exception {
		
		
		CommandLine cli = spec.commandLine();
		if (usageHelpRequested) {
            cli.usage(cli.getOut());
            return 0;
        }
		
		try {
			new RestAPI().getUser(username);
			return 0;
			
		}catch (RuntimeException e) {
            cli.getOut().println(e.getMessage());
            e.printStackTrace(cli.getOut());
            return -1;
        }
		
		
		
		
	}
	
}
