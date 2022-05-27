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
	JSONObject itemPriceJSON;
	
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
	private String[][] itemListArray = new String [100][6];
	public static boolean DEBUG = false;

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
		
		currentPrice		= String.valueOf(obj.getJSONObject("item").getJSONObject("current").get("price"));
		currentTrend		= obj.getJSONObject("item").getJSONObject("current").getString("trend");

		todayPrice			= String.valueOf(obj.getJSONObject("item").getJSONObject("today").get("price"));
		todayTrend			= obj.getJSONObject("item").getJSONObject("today").getString("trend");
		
		day30_trend 		= obj.getJSONObject("item").getJSONObject("day30").getString("trend");
		day30_change 		= String.valueOf(obj.getJSONObject("item").getJSONObject("day30").get("change"));
		day90_trend 		= obj.getJSONObject("item").getJSONObject("day90").getString("trend");
		day90_change 		= String.valueOf(obj.getJSONObject("item").getJSONObject("day90").get("change"));
		day180_trend 		= obj.getJSONObject("item").getJSONObject("day180").getString("trend");
		day180_change 		= String.valueOf(obj.getJSONObject("item").getJSONObject("day180").get("change"));
		
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
		
		//System.out.println("itemListArray.length = "+ itemListArray.length + " itemListArray[0].length = "+ itemListArray[0].length );
		
		for(int i = 0, x = itemListArray.length - 1; i < x; i++) {
			for(int j = 0, y = itemListArray[x].length - 1; j < y; j++)
			itemListArray[i][j] = null;
		}
		
		for(int i = 0, size = jsonArray.length(); i < size; i++) {
			JSONObject arrayParserJSONObject =  jsonArray.getJSONObject(i);
			
			String[] itemListNode = new String [6];
			itemListNode [0] = arrayParserJSONObject.getString("icon_large");
			itemListNode [1] = String.valueOf(arrayParserJSONObject.getInt("id"));
			itemListNode [2] = arrayParserJSONObject.getString("name");
			itemListNode [3] = String.valueOf(arrayParserJSONObject.getJSONObject("current").get("price"));
			itemListNode [4] = arrayParserJSONObject.getJSONObject("today").getString("trend");
			itemListNode [5] = String.valueOf(arrayParserJSONObject.getJSONObject("today").get("price")); //if 0 will become an integer
			
			itemListArray[i] = itemListNode;

			//System.out.println("\nIcon: "+itemListNode [0] +"\nID: "+ itemListNode [1]+"\nName: "+itemListNode [2]+"\n Price Today: "+itemListNode [3]+"\n Trend Today "+itemListNode [4]
			//		+"\nPrice Change Today: "+itemListNode [5]);
			
			//System.out.println(itemListArray[i][1]);

			}
		if(DEBUG == true) {System.out.println(itemListArray[10][2] +"" +itemListArray[10][2]);}
		}
		catch(Exception e)
		{
			System.out.println("Error parsing ItemJsonList: " + e.getMessage());
		}
		
	}
	
	
	protected String[][] getItemJsonPrice_RuneLine(String url) {
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
				itemPriceJSON = new JSONObject(jsonString);
				
				if(DEBUG == true) {System.out.println("getItemJsonPrice_RuneLine");}
				
			}
		}
		catch (Exception e) {
			System.out.println("Error in Method: getItemJsonList() "
		+ " " + e.getMessage());
		}
		return parseRuneLinePrice();
	}
	
	private String[][] parseRuneLinePrice() {
		JSONArray jsonArray =  itemPriceJSON.getJSONArray("data");
		String [][] itemPriceArray = new String [jsonArray.length()][3];
		try {
			if(DEBUG == true) {System.out.println("getItemJsonPrice_RuneLine");}
			/*Clearing old item data (if applicable)*/
			//for(int i = 0, x = itemListArray.length - 1; i < x; i++) {
			//	for(int j = 0, y = itemListArray[x].length - 1; j < y; j++)
		//		itemListArray[i][j] = null;
		//	}
			
			/*Looping through the prices*/
			for(int i = 0, size = jsonArray.length(); i < size; i++) {
				JSONObject arrayParserJSONObject =  jsonArray.getJSONObject(i);
				
				String[] itemPriceNode = new String [5];
				itemPriceNode [0] = String.valueOf(arrayParserJSONObject.get("timestamp"));
				itemPriceNode [1] = String.valueOf(arrayParserJSONObject.get("avgHighPrice"));
				itemPriceNode [2] = String.valueOf(arrayParserJSONObject.get("avgLowPrice"));
				itemPriceNode [3] = String.valueOf(arrayParserJSONObject.get("highPriceVolume"));	
				itemPriceNode [4] = String.valueOf(arrayParserJSONObject.get("lowPriceVolume"));
				
				
				if(itemPriceNode [0] == "null") {itemPriceNode [0]="0";}
				if(itemPriceNode [1] == "null") {itemPriceNode [1]="0";}
				if(itemPriceNode [2] == "null") {itemPriceNode [2]="0";}
				if(itemPriceNode [3] == "null") {itemPriceNode [3]="0";}
				if(itemPriceNode [4] == "null") {itemPriceNode [4]="0";}

				/*System.out.println("timestamp: " +"\t " 			+itemPriceNode [0] 
									+"\navgHighPrice: " + "\t " 	+itemPriceNode [1]
									+"\navgLowPrice: " + "\t " 		+itemPriceNode [2]
									+"\nhighPriceVolume: " + "\t "  +itemPriceNode [3]
									+"\nlowPriceVolume: " + "\t "   +itemPriceNode [4]);*/
				
				itemPriceArray[i] = itemPriceNode;

				}
			//System.out.println("avgHighPrice" +" " +itemPriceArray[10][1]);
			//return itemPriceArray;
			}
			catch(Exception e)
			{
				System.out.println("Error parsing ItemJsonList: " + e.getMessage());
			}
		return itemPriceArray;
		
	}
}


