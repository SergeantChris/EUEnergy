package cli;

import gr.ntua.ece.softeng19b.client.RestAPI;
import picocli.CommandLine;

import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;

@Command(
    name="ActualvsForecast"
)
public class ActualvsForecast extends EnergyCliArgs implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        CommandLine cli = spec.commandLine();

        if (usageHelpRequested) {
            cli.usage(cli.getOut());
            return 0;
        }


        try {
            if (dateArgs.date != null ) {
                String records = new RestAPI().getActualvsForecast(areaName, timeres.name(), "date", dateArgs.date, format);
                // Do something with the records :)
                System.out.println("Fetched " + records + " records");
                return 0;
            }
            else if(dateArgs.month != null){
            	String records = new RestAPI().getActualvsForecast(areaName, timeres.name(),"month",dateArgs.month , format);
                // Do something with the records :)
                System.out.println("Fetched " + records + " records");
                return 0;
            }
            else if(dateArgs.year != null){
            	String records = new RestAPI().getActualvsForecast(areaName, timeres.name(), "year",dateArgs.year, format);
                // Do something with the records :)
                System.out.println("Fetched " + records + " records");
                return 0;
            }
            else {
            	System.out.println("");
            	cli.usage(cli.getOut());
                return 0;
            }
        } catch (RuntimeException e) {
            cli.getOut().println(e.getMessage());
            e.printStackTrace(cli.getOut());
            return -1;
        }

    }
}
