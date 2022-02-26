package main_pod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class GET extends Connect {
	private URL GET_1;
	private Object grab;
	private int[] grabno;
	JSONObject obj;
	JSONObject searchJSONObj;
	
	/***********Item key values*************/
	private String name;
	private String id;
	private String icon;
	private String icon_large;
	private String type;
	private String typeIcon;
	private String description;
	private String members;
	private String currentTrend;
	private String currentPrice;
	private String todayTrend;
	private String todayPrice;
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
	
	private String[] itemInfoArr1 = new String [18];
	private String[] []itemListArray = new String [6][25];
	//private Object[] itemListNode = new String [6];
	//JSONObject arrayParserJSONObject;

	GET() {
	}
	
	protected void getItemJson(String url) {
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
		id   				= String.valueOf(obj.getJSONObject("item").getInt("id"));
		icon 				= obj.getJSONObject("item").getString("icon");
		icon_large  		= obj.getJSONObject("item").getString("icon_large");
		type  				= obj.getJSONObject("item").getString("type");
		typeIcon  			= obj.getJSONObject("item").getString("typeIcon");
		description 		= obj.getJSONObject("item").getString("description");
		members 			= obj.getJSONObject("item").getString("members");
		
		currentPrice		= obj.getJSONObject("item").getJSONObject("current").getString("price");
		currentTrend		= obj.getJSONObject("item").getJSONObject("current").getString("trend");

		todayPrice			= obj.getJSONObject("item").getJSONObject("today").getString("price");
		todayTrend			= obj.getJSONObject("item").getJSONObject("today").getString("trend");
		
		day30_trend 		= obj.getJSONObject("item").getJSONObject("day30").getString("trend");
		day30_change 		= obj.getJSONObject("item").getJSONObject("day30").getString("change");
		day90_trend 		= obj.getJSONObject("item").getJSONObject("day90").getString("trend");
		day90_change 		= obj.getJSONObject("item").getJSONObject("day90").getString("change");
		day180_trend 		= obj.getJSONObject("item").getJSONObject("day180").getString("trend");
		day180_change 		= obj.getJSONObject("item").getJSONObject("day180").getString("change");
		
		String itemInfoArr2[] = {
				 name  				//0		
				,id    				//1	
				,icon 				//2	
				,icon_large  		//3	
				,type  				//4	
				,typeIcon  			//5	
				,description 		//6	
				,members 			//7	

				,currentPrice		//8	
				,currentTrend		//9	

				,todayPrice			//10	
				,todayTrend			//11	

				,day30_trend 		//12	
				,day30_change 		//13	
				,day90_trend 		//14	
				,day90_change 		//15
				,day180_trend 		//16	
				,day180_change};	//17	
		
		itemInfoArr1=itemInfoArr2;
		
	}
	protected String[] getItemInfo() {
		return itemInfoArr1;
	}
	
	protected String[][] returnItemListArray () {
		return itemListArray;
		
	}

	
	protected void getItemJsonList(String url) {
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
				searchJSONObj = new JSONObject(jsonString);
				parseItemJsonList();
			}
		}
		catch (Exception e) {
			System.out.println("Error in Method: getItemJsonList() "
		+ " " + e.getMessage());
		}
		
	}
	
	private void parseItemJsonList() {

		try {
		
		JSONArray jsonArray =  searchJSONObj.getJSONArray("items");
		
		for(int i = 0, size = jsonArray.length(); i < size; i++) {
			JSONObject arrayParserJSONObject =  jsonArray.getJSONObject(i);
			
			String[] itemListNode = new String [6];
			itemListNode [0] = arrayParserJSONObject.getString("icon");
			itemListNode [1] = String.valueOf(arrayParserJSONObject.getInt("id"));
			itemListNode [2] = arrayParserJSONObject.getString("name");
			itemListNode [3] = arrayParserJSONObject.getJSONObject("current").getString("price");
			itemListNode [4] = arrayParserJSONObject.getJSONObject("today").getString("trend");
			itemListNode [5] = String.valueOf(arrayParserJSONObject.getJSONObject("today").get("price")); //if 0 will become an integer
			
			itemListArray[i] = itemListNode;

			//System.out.println("\nIcon: "+itemListNode [0] +"\nID: "+ itemListNode [1]+"\nName: "+itemListNode [2]+"\n Price Today: "+itemListNode [3]+"\n Trend Today "+itemListNode [4]
			//		+"\nPrice Change Today: "+itemListNode [5]);
			
			//System.out.println(itemListArray[i][1]);

			}
		}
		catch(Exception e)
		{
			System.out.println("Error parsing ItemJsonList: " + e.getMessage());
		}
		
	}
}


