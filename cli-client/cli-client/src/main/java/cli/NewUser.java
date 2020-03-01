package cli;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine;
import picocli.CommandLine.*;
import gr.ntua.ece.softeng19b.client.RestAPI;


@Command(name="NewUser", description = "Add new user")
public class NewUser extends BasicCliArgs implements Callable<Integer>{
	@Option(
	        names = {"-u","--username"},
	        required = true,
	        description = "Write the username."
	    )
	protected String username;
	
	@Option(
	        names = {"-p","--password"},
	        required = true,
	        description = "Write the password."
	    )
	protected String password;
	@Option(
	        names = {"-e","--emai"},
	        required = true,
	        description = "Write the email."
	    )
	protected String email;
	
	@Option(
	        names = {"-q","--quota"},
	        required = true,
	        description = "Write the quota."
	    )
	protected int quota;
	
	
	
	@Override
	public Integer call() throws Exception {
		
		
		CommandLine cli = spec.commandLine();
		if (usageHelpRequested) {
            cli.usage(cli.getOut());
            return 0;
        }
		
		try {
			new RestAPI().addUser(username, password,email,quota);
			return 0;
			
		}catch (RuntimeException e) {
            cli.getOut().println(e.getMessage());
            e.printStackTrace(cli.getOut());
            return -1;
        }
		
		
		
		
	}
	
}
