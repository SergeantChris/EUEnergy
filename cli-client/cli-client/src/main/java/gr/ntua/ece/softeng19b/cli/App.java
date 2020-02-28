package gr.ntua.ece.softeng19b.cli;

import picocli.CommandLine;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.http.HttpClient;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.concurrent.Callable;

import static picocli.CommandLine.*;


@Command(
    name="energy_TEAM_38",
    mixinStandardHelpOptions = true,
    version = "energy_TEAM 1.0",
    subcommands = {
        HealthCheck.class,
        ActualTotalLoad.class,
        AggregatedGenerationPerType.class
    }
)
public class App implements Callable<Integer> {

    static final String BASE_URL = "https://localhost:8765/energy/api";

    public static void main(String[] args) throws IOException {
        CommandLine commandLine = new CommandLine(new App());
        commandLine.setCaseInsensitiveEnumValuesAllowed(true);
        commandLine.setStopAtUnmatched(true);
        //
        
        //If there's no sub-command, show the usage
        
        String command = "";
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        
        Scanner in = new Scanner(System.in);
        
        System.out.println("Starting Pico CLI");
        
        while(true) {
	        command = in.nextLine();
	        System.out.println(command);
	        //int exitCode = commandLine.execute(args);
	        /*
	        if (commandLine.getParseResult().subcommand() == null) {
	        	commandLine.usage(System.out);
	        }
	      	*/
	        if(command.equals("exit")) {
	        	in.close();
	        	System.exit(0);
	        }
        }
    }

    //Helper method to create a new http client that can tolerate self-signed or improper ssl certificates
    static HttpClient newHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new SecureRandom());
        return HttpClient.newBuilder().sslContext(sslContext).build();
    }

    private static TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }
        }
    };

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
