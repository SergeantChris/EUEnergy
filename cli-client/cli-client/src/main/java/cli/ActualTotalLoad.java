package cli;

import gr.ntua.ece.softeng19b.client.RestAPI;
import picocli.CommandLine;

import java.util.concurrent.Callable;

import org.json.JSONArray;
import org.json.JSONObject;

import static picocli.CommandLine.Command;

@Command(
    name="ActualTotalLoad"
)
public class ActualTotalLoad extends EnergyCliArgs implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        CommandLine cli = spec.commandLine();

        if (usageHelpRequested) {
            cli.usage(cli.getOut());
            return 0;
        }


        try {
            if (dateArgs.date != null ) {
                String records = new RestAPI().getActualTotalLoad(areaName, timeres.name(), "date", dateArgs.date, format);
                // Do something with the records :)
                System.out.println("Fetched \n" + records);
                return 0;
            }
            else if(dateArgs.month != null){
            	String records = new RestAPI().getActualTotalLoad(areaName, timeres.name(),"month",dateArgs.month , format);
                // Do something with the records :)
                System.out.println("Fetched \n" + records);
                return 0;
            }
            else if(dateArgs.year != null){
            	String records = new RestAPI().getActualTotalLoad(areaName, timeres.name(), "year",dateArgs.year, format);
                // Do something with the records :)
                System.out.println("Fetched \n" + records);
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
