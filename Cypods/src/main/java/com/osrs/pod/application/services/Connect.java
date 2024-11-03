package com.osrs.pod.application.services;

import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Connect {

	public int responseCode;
	
	protected HttpURLConnection httpStringURL (String inputURL){
		URL url;
		HttpURLConnection http = null;
			try {
					System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
					url = new URL(inputURL);
					URLConnection con = url.openConnection();
					http = (HttpURLConnection)con;
					http.setRequestMethod("GET");
					http.setDoInput(true);
					http.setRequestProperty("Host", url.getHost());
					http.setRequestProperty("Accept","application/json");
					http.setRequestProperty("User-Agent", "PostmanRuntime/7.29.2");
					responseCode = ((HttpURLConnection) con).getResponseCode();
				}
			catch(Exception e){ System.out.println(" " +e.getMessage());
				}
		return http;
	}
	
}