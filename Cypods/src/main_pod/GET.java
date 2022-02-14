package main_pod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class GET extends Connect {
	private URL GET_1;
	private Object grab;
	private int[] grabno;
	JSONObject obj;
	
	/***********Item key values*************/
	private String name;
	private int id;
	private String icon;
	private String icon_large;
	private String type;
	private String typeIcon;
	private String description;
	private String members;
	private String day30;
	private String day30_trend;
	private String day30_change;
	private String day90;
	private String day90_trend;
	private String day90_change;
	private String day180;
	private String day180_trend;
	private String day180_change;
	/***********Item key values*************/

	GET() {
	}
	
	public void getItemJson(String url) {
		String s = "";
		try {
			HttpURLConnection http = httpStringURL(url); 
			if (HttpURLConnection.HTTP_OK == responseCode) { // Success: Status = 200
				BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
				StringBuffer response = new StringBuffer();
				String READ_INPUT_LINE_FROM_SITE;
				while ((READ_INPUT_LINE_FROM_SITE = in.readLine()) != null) {
						response.append(READ_INPUT_LINE_FROM_SITE);
				}
				in.close();
				String jsonString = response.toString();
				obj = new JSONObject(jsonString);
				parseItemJson();
			}
		
		}

		catch (Exception e) {
			System.out.println("Error in Method: getItemJson() "
		+ " " + e.getMessage());
		}
		
	}
	
	private void parseItemJson() {
		name 				= obj.getJSONObject("item").getString("name");
		id   				= obj.getJSONObject("item").getInt("id");
		icon 				= obj.getJSONObject("item").getString("icon");
		icon_large  		= obj.getJSONObject("item").getString("icon_large");
		type  				= obj.getJSONObject("item").getString("type");
		typeIcon  			= obj.getJSONObject("item").getString("typeIcon");
		description 		= obj.getJSONObject("item").getString("description");
		members 			= obj.getJSONObject("item").getString("members");
		
		/*
		day30 				= obj.getJSONObject("item").getString("");
		day30_trend 		= obj.getJSONObject("item").getString("");
		day30_change 		= obj.getJSONObject("item").getString("");
		day90 				= obj.getJSONObject("item").getString("");
		day90_trend 		= obj.getJSONObject("item").getString("");
		day90_change 		= obj.getJSONObject("item").getString("");
		day180 				= obj.getJSONObject("item").getString("");
		day180_trend 		= obj.getJSONObject("item").getString("");
		day180_change 		= obj.getJSONObject("item").getString("");*/

	}

	public String getIconLargeSprite (){
		return icon_large;
	}
	
	public String getItemName()	{
		return name;
	}
	
	public int getItemId() {
		return id;
	}
	public String getIconSprite() {
		return icon;
	}
	public String getItemType()	{
		return type;
	}
	public String getTypeIcon() {
		return typeIcon;
	}
	public String getItemDescription() {
		return description;
	}
	public String getItemMemberStatus() {
		return members;
	}

		
	/** Overloaded GETTER Methods to get API Response */
	private void GETTER(String url) {
		try {
			HttpURLConnection http = httpStringURL(url); // Setting http variable to equal predefined values returned by
															// httpStringURL method
			if (HttpURLConnection.HTTP_OK == responseCode) { // Success: Status = 200
				BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
				StringBuffer response = new StringBuffer();
				String READ_INPUT_LINE_FROM_SITE;
				// Reading input from BufferedReader into StringBuffer
				while ((READ_INPUT_LINE_FROM_SITE = in.readLine()) != null) {
					response.append(READ_INPUT_LINE_FROM_SITE);
				}
				in.close();
				System.out.println(response);

			}
		}

		catch (Exception e) {
			System.out.println(" " + e.getMessage());
		}

	}



	private void GETTER(String url, int[] i) {
		try {
			HttpURLConnection http = httpStringURL(url); // Setting http variable to equal predefined values returned by
															// httpStringURL method
			if (HttpURLConnection.HTTP_OK == responseCode) { // Success: Status = 200
				BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
				StringBuffer response = new StringBuffer();
				String READ_INPUT_LINE_FROM_SITE;
				// Reading input from BufferedReader into StringBuffer
				while ((READ_INPUT_LINE_FROM_SITE = in.readLine()) != null) {
					response.append(READ_INPUT_LINE_FROM_SITE);
				}
				in.close();
				// Assign output to JSONObject

				String jsonString = response.toString();
				JSONObject obj = new JSONObject(jsonString);
				Object JSON_KEYS = null;

				for (int k = 0; k < i.length; k++) {
					int KEY_INDEX;
					String KEY_STRING;
					String KEY_STRING_VALUE;
					if (i[k] < obj.length()) {
						KEY_INDEX = i[k];
						JSON_KEYS = obj.keySet();
						String JSON_KEYS_STRING = JSON_KEYS.toString();
						JSON_KEYS_STRING = JSON_KEYS_STRING.substring(1, JSON_KEYS_STRING.length() - 1);
						// System.out.println(JSON_KEYS_STRING);
						String JKS_Arr[];
						JKS_Arr = JSON_KEYS_STRING.split(",");
						KEY_STRING = JKS_Arr[KEY_INDEX].trim();
						KEY_STRING_VALUE = obj.get(KEY_STRING).toString();

						System.out.println(KEY_STRING_VALUE);

					} else {
						System.out.println("Index out of bounds");
					}
				}
			}
		} catch (Exception e) {
			System.out.println(" " + e.getMessage());
		}
	}

}
