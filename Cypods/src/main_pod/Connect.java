package main_pod;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Connect {

	int responseCode;
	
	protected HttpURLConnection httpStringURL (String inputURL){
		URL url;
		HttpURLConnection http = null;
			try {
					url = new URL(inputURL);
					URLConnection con = url.openConnection();
					http = (HttpURLConnection)con;
					http.setRequestMethod("GET");
					http.setDoInput(true);
					http.setRequestProperty("Accept","application/json");
					responseCode = ((HttpURLConnection) con).getResponseCode();
				}
			catch(Exception e){ System.out.println(" " +e.getMessage());
				}
		return http;
	}
	
}