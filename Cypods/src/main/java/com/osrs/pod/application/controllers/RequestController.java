package com.osrs.pod.application.controllers;

import com.google.common.util.concurrent.RateLimiter;
import com.osrs.pod.application.services.Connect;
import com.osrs.pod.application.services.DataModeler;
import com.osrs.pod.application.services.SlidingWindowRateLimiter;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.*;

import static com.osrs.pod.application.ApplicationConstant.*;
@Component
@Getter
@Setter
public class RequestController extends Connect implements Runnable {

	JSONObject obj;
	protected static int searchResultSize = 0;
	/*********** Item key values *************/

	private String[][] itemListArray = new String[100][6];
	DataModeler dataModeler = new DataModeler();

	private long current_time = System.currentTimeMillis();
	private long last_call_time;

	// Create a RateLimiter that allows a limited number of calls per second
	private final RateLimiter callIntervalRateLimiter = RateLimiter.create(45.0 / 60.0);

	private final SlidingWindowRateLimiter maxCallsRateLimiter = new SlidingWindowRateLimiter();

	public RequestController() {

	}

	/**
	 * Method takes an API endpoint as an input, and returns a JSONObject
	 **/
	public JSONObject requestItemData(String url) throws NullPointerException {

		try {
			// Try to acquire a permit.
			if (!maxCallsRateLimiter.tryCall() || !callIntervalRateLimiter.tryAcquire()) {
				System.out.println("Rate limit exceeded. Please wait before making another API call.");
				return null;
			}
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
			System.out.println("Error in Method: requestItemData() " + " " + e.getMessage());
		}
		return obj;
	}


	/**
	 * Method parses the JSON response of the Runescape API (item price/info), and
	 * adds it to an array
	 **/
	protected String [] get_osrs_api_parseItemJson(String url) {
		//JSONObject obj = requestItemData(url);
		return dataModeler.dataModeler_osrs_api_parseItemJson(requestItemData(url));

	}

	protected String[][] returnItemListArray() {

		return itemListArray;

	}


	/**
	 * Method parses the JSON response of the Runescape API (item query search
	 * result), and adds it to an array
	 **/
	protected boolean osrsSearchItemsParseJsonList(String url , String params) {

		try {
			url = url +  URLEncoder.encode(params, "UTF-8");
			obj = requestItemData(url);
			JSONArray jsonArray = obj.getJSONArray("items");

			setSearchResultSize(jsonArray.length());

			clearItemSearchResultArray();

			for (int i = 0, size = jsonArray.length(); i < size; i++) {
				JSONObject arrayParserJSONObject = jsonArray.getJSONObject(i);

				String[] itemListNode = new String[6];
				itemListNode[GE_SEARCH_ICON_URL] = arrayParserJSONObject.getString("icon_large");
				itemListNode[GE_SEARCH_ID] = String.valueOf(arrayParserJSONObject.getInt("id"));
				itemListNode[GE_SEARCH_NAME] = arrayParserJSONObject.getString("name");
				itemListNode[GE_SEARCH_CURRENT_PRICE] = String.valueOf(arrayParserJSONObject.getJSONObject("current").get("price"));
				itemListNode[GE_SEARCH_TREND_TODAY] = arrayParserJSONObject.getJSONObject("today").getString("trend");
				itemListNode[GE_SEARCH_PRICE_TODAY] = String.valueOf(arrayParserJSONObject.getJSONObject("today").get("price"));

				itemListArray[i] = itemListNode;

			}
			if (DEBUG == true) {
				System.out.println("Debug item [10][2]: " +itemListArray[10][2] + "" + itemListArray[10][2]);
			}

		} catch (Exception e) {
			System.out.println("Error parsing get_osrs_api_parseItemJsonList: " + e.getMessage());
		}
		return true;
	}


	/**
	 * Method parses the JSON response of the runelite API, and adds it to an array
	 * @NotNull
	 */

	protected String[][] get_api_parseRuneLitePrice(String timePeriod, int itemID) {
		obj = requestItemData(generateRuneLitePriceDataUrl(timePeriod,itemID));
		JSONArray jsonArray = obj.getJSONArray("data");
		String[][] itemPriceArray = new String[jsonArray.length()][3];
		try {
			if (DEBUG == true) {
				System.out.println("getItemJsonPrice_RuneLine");
			}

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
				itemPriceArray[i] = itemPriceNode;
			}

		} catch (Exception e) {
			System.out.println("Error parsing get_api_parseRuneLitePrice: " + e.getMessage());
		}
		return itemPriceArray;

	}

	protected String [][] get_osrs_api_parseItemGraph(int itemId) throws NullPointerException{
		String[][] itemPriceArray = null;
		try {
			obj = requestItemData(generateOsrsPriceDataUrl(itemId));
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