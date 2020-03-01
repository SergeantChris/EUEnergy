package cli;

import gr.ntua.ece.softeng19b.client.RestAPI;
import picocli.CommandLine;

import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;

@Command(
    name="Reset",
    description="Reset database, authorised"
)
public class Reset extends BasicCliArgs implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {

        CommandLine cli = spec.commandLine();

        if (usageHelpRequested) {
            cli.usage(cli.getOut());
            return 0;
        }

        try {
            new RestAPI().resetDatabase();
            return 0;
        } catch (RuntimeException e) {
            cli.getOut().println(e.getMessage());
            e.printStackTrace(cli.getOut());
            return -1;
        }        
    }
}
