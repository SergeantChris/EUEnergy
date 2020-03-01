package cli;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.concurrent.Callable;

import gr.ntua.ece.softeng19b.client.RestAPI;

import picocli.CommandLine;
import static picocli.CommandLine.*;



@Command(
	    name="Login"
	    
)
public class Login extends BasicCliArgs implements Callable<Integer>{
	@Option(
	        names = {"-u","--username"},
	        required = true,
	        description = "Write your username."
	    )
	protected String username;
	
	@Option(
	        names = {"-p","--password"},
	        required = true,
	        description = "Write your password."
	    )
	protected String password;
	
	@Override
    public Integer call() throws Exception {
		
		CommandLine cli = spec.commandLine();
		if (usageHelpRequested) {
            cli.usage(cli.getOut());
            return 0;
        }
		
		try {
			new RestAPI().login(username, password);
			return 0;
			
		}catch (RuntimeException e) {
            cli.getOut().println(e.getMessage());
            e.printStackTrace(cli.getOut());
            return -1;
        }
		
	}
}
