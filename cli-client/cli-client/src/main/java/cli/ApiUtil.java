package cli;

import org.json.JSONObject;

public class ApiUtil {
	
	public static String[] splitArgs(String args) {
		return args.split(" ");
	}
	
	public static String createURL(String[] args) {
		String URL = "";
		if(args[1].contentEquals("energy_group_38")) {
			
		}
		
		
		return URL;
	}
	
	
	public static JSONObject requestURL(String url){
		return new JSONObject();
	}
	

}
