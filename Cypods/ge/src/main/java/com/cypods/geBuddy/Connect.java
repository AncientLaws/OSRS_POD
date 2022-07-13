package com.cypods.geBuddy;

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
					http.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
					responseCode = ((HttpURLConnection) con).getResponseCode();
				}
			catch(Exception e){ System.out.println(" " +e.getMessage());
				}
		return http;
	}
	
}