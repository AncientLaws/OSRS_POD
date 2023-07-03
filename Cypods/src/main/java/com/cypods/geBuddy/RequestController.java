package com.cypods.geBuddy;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Set;

import static com.cypods.geBuddy.ApplicationConstant.*;


@Component
public class RequestController extends Connect implements Runnable {
	


	private URL GET_1;
	private Object grab;
	private int[] grabno;
	JSONObject obj;
	JSONObject searchJSONObj;
	JSONObject itemPriceJSON;
	protected static int searchResultSize = 0;
	/*********** Item key values *************/
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
	/*********** Item key values *************/

	private String[] itemInfoArr1 = new String[18];
	private String[][] itemListArray = new String[100][6];
	public static boolean DEBUG = false;
	DataModeler dataModeler = new DataModeler();

	RequestController() {
	}

	/**
	 * Method takes an API endpoint as an input, and returns a JSONObject
	 **/

	private JSONObject getItemJson(String url) throws NullPointerException {
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
				// get_osrs_api_parseItemJson();
			}

		}

		catch (Exception e) {
			System.out.println("Error in Method: getItemJson() " + " " + e.getMessage());
		}

		return obj;
	}
	

	/**
	 * Method parses the JSON response of the Runescape API (item price/info), and
	 * adds it to an array
	 **/
	protected String [] get_osrs_api_parseItemJson(String url) {
		//JSONObject obj = getItemJson(url);
		return dataModeler.dataModeler_osrs_api_parseItemJson(getItemJson(url));

	}

	protected String[][] returnItemListArray() {

		return itemListArray;

	}


	/**
	 * Method parses the JSON response of the Runescape API (item query search
	 * result), and adds it to an array
	 **/

	protected void set_osrs_api_parseItemJsonList(String url , String params) {

		try {

			/**Fix 11/23/2022:- API update by Jagex forced the use of URL encoder
			 * @Before:- passed query parameters in the url could be =dragon claws
			 * @After:- Url must be encoded correctly by removing spaces =dragon+claws
			 * */
			url = url +  URLEncoder.encode(params, "UTF-8");
			obj = getItemJson(url);
			JSONArray jsonArray = obj.getJSONArray("items");

			setSearchResultSize(jsonArray.length());

			// System.out.println("itemListArray.length = "+ itemListArray.length + "
			// itemListArray[0].length = "+ itemListArray[0].length );

			clearItemSearchResultArray();

			for (int i = 0, size = jsonArray.length(); i < size; i++) {
				JSONObject arrayParserJSONObject = jsonArray.getJSONObject(i);

				String[] itemListNode = new String[6];
				itemListNode[GE_SEARCH_ICON_URL] = arrayParserJSONObject.getString("icon_large");
				itemListNode[GE_SEARCH_ID] = String.valueOf(arrayParserJSONObject.getInt("id"));
				itemListNode[GE_SEARCH_NAME] = arrayParserJSONObject.getString("name");
				itemListNode[GE_SEARCH_CURRENT_PRICE] = String.valueOf(arrayParserJSONObject.getJSONObject("current").get("price"));
				itemListNode[GE_SEARCH_TREND_TODAY] = arrayParserJSONObject.getJSONObject("today").getString("trend");
				itemListNode[GE_SEARCH_PRICE_TODAY] = String.valueOf(arrayParserJSONObject.getJSONObject("today").get("price")); // if 0
																												// will
																												// become
																												// an
																												// integer

				itemListArray[i] = itemListNode;

//				 System.out.println("\nIcon: "+itemListNode [0] +"\nID: "+ itemListNode
//				 [1]+"\nName: "+itemListNode [2]+"\n Price Today: "+itemListNode [3]+"\n Trend Today "+ itemListNode [4]
//				 +"\nPrice Change Today: "+itemListNode [5]);
//
//				 System.out.println(itemListArray[i][1]);

			}
			if (DEBUG == true) {
				System.out.println(itemListArray[10][2] + "" + itemListArray[10][2]);
			}
		} catch (Exception e) {
			System.out.println("Error parsing get_osrs_api_parseItemJsonList: " + e.getMessage());
		}

	}

	
	/**
	 * Method parses the JSON response of the runelite API, and adds it to an array
	 * @NotNull
	 */

	protected String[][] get_api_parseRuneLitePrice(String timePeriod, int itemID) {
		String runeLitePriceUrl = "https://prices.runescape.wiki/api/v1/osrs/timeseries?timestep=" + timePeriod + "&id=" + itemID;
		obj = getItemJson(runeLitePriceUrl);
		JSONArray jsonArray = obj.getJSONArray("data");
		String[][] itemPriceArray = new String[jsonArray.length()][3];
		try {
			if (DEBUG == true) {
				System.out.println("getItemJsonPrice_RuneLine");
			}
			/* Clearing old item data (if applicable) */
			// for(int i = 0, x = itemListArray.length - 1; i < x; i++) {
			// for(int j = 0, y = itemListArray[x].length - 1; j < y; j++)
			// itemListArray[i][j] = null;
			// }

			/* Looping through the prices */
			for (int i = 0, size = jsonArray.length(); i < size; i++) {
				JSONObject arrayParserJSONObject = jsonArray.getJSONObject(i);

				String[] itemPriceNode = new String[5];
				itemPriceNode[0] = String.valueOf(arrayParserJSONObject.get("timestamp"));
				itemPriceNode[1] = String.valueOf(arrayParserJSONObject.get("avgHighPrice"));
				itemPriceNode[2] = String.valueOf(arrayParserJSONObject.get("avgLowPrice"));
				itemPriceNode[3] = String.valueOf(arrayParserJSONObject.get("highPriceVolume"));
				itemPriceNode[4] = String.valueOf(arrayParserJSONObject.get("lowPriceVolume"));

				for(int j = 0; j<itemPriceNode.length-1; j++)
				{
					if (itemPriceNode[j].equals("null")) {
						itemPriceNode[j] = "0";
					}
					
				}


				/*
				 * System.out.println("timestamp: " +"\t " +itemPriceNode [0]
				 * +"\navgHighPrice: " + "\t " +itemPriceNode [1] +"\navgLowPrice: " + "\t "
				 * +itemPriceNode [2] +"\nhighPriceVolume: " + "\t " +itemPriceNode [3]
				 * +"\nlowPriceVolume: " + "\t " +itemPriceNode [4]);
				 */

				itemPriceArray[i] = itemPriceNode;

			}

		} catch (Exception e) {
			System.out.println("Error parsing get_api_parseRuneLitePrice: " + e.getMessage());
		}
		return itemPriceArray;

	}

	protected String [][] get_osrs_api_parseItemGraph(int itemID) throws NullPointerException{
		String[][] itemPriceArray = null;
		try {
			String itemGraphUrl = "https://services.runescape.com/m=itemdb_oldschool/api/graph/".concat(String.valueOf(itemID)).concat(".json");
			obj = getItemJson(itemGraphUrl);
			JSONObject dailyData = obj.getJSONObject("daily"); 
			Set<?> key_dailyData =  dailyData.keySet();
			itemPriceArray = new String[key_dailyData.size()][3];
			
			 Iterator<?> i = key_dailyData.iterator();
			 
			 for(int j = 0; i.hasNext(); j++)
			 {
							 
				 	String k = i.next().toString();
			        itemPriceArray[j][0] = k;
			        
			        itemPriceArray[j][1] = dailyData.get(k).toString();

			 }
		
		} catch (Exception e) {
			System.out.println("Error parsing get_osrs_api_parseItemGraph: " + e.getMessage());
		}
		
		return itemPriceArray;

	}

	@Override
	public void run() {

		
	}

	/**
	 * Clears item data from the itemList array.
	 * @Purpose If not done, previously searched items will show up if not overwritten
	 * */
	private void clearItemSearchResultArray(){
		for (int i = 0, x = itemListArray.length - 1; i <= x; i++) {
			for (int j = 0, y = itemListArray[x].length - 1; j <= y; j++)
				itemListArray[i][j] = null;
		}

	}

	protected static int get_getSearchResultSize()
	{
		return searchResultSize;
	}

	private void setSearchResultSize(int i){
		searchResultSize = i;
	}
	



}