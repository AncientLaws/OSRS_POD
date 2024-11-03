package com.osrs.pod.database.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

@Component
public class DatabaseUpdaterController {
//    JSONObject obj;
    int responseCode;

    protected HttpURLConnection httpStringURL (String inputURL){
        URL url;
        HttpURLConnection http = null;
        try {
            System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
            url = new URL(inputURL);
            //url. URLEncoder.encode(url,"UTF-8");
            URLConnection con = url.openConnection();
            http = (HttpURLConnection)con;
            http.setRequestMethod("GET");
            http.setDoInput(true);
            http.setRequestProperty("Host", url.getHost());
            //System.out.println("Host is: " + url.getHost());
            http.setRequestProperty("Accept","application/json");
            http.setRequestProperty("User-Agent", "PostmanRuntime/7.29.2");
            responseCode = ((HttpURLConnection) con).getResponseCode();
        }
        catch(Exception e){ System.out.println(" " +e.getMessage());
        }
        return http;
    }


    /**
     * Method takes an API endpoint as an input, and returns a JSONObject
     **/
    public JSONObject getItemJson(String url) throws NullPointerException {
        String s = "";
        JSONObject obj = new JSONObject();
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


    public String [] dataModeler_osrs_api_parseItemJson(JSONObject obj) {

        String itemInfoArr[] = { obj.getJSONObject("item").getString("name") // 0
                , String.valueOf(obj.getJSONObject("item").getInt("id")) // 1
                , obj.getJSONObject("item").getString("icon") // 2
                , obj.getJSONObject("item").getString("icon_large") // 3
                , obj.getJSONObject("item").getString("type") // 4
                , obj.getJSONObject("item").getString("typeIcon") // 5
                , obj.getJSONObject("item").getString("description") // 6
                , obj.getJSONObject("item").getString("members") // 7

                , String.valueOf(obj.getJSONObject("item").getJSONObject("current").get("price")) // 8
                , obj.getJSONObject("item").getJSONObject("current").getString("trend") // 9

                , String.valueOf(obj.getJSONObject("item").getJSONObject("today").get("price")) // 10
                , obj.getJSONObject("item").getJSONObject("today").getString("trend") // 11

                , obj.getJSONObject("item").getJSONObject("day30").getString("trend") // 12
                , String.valueOf(obj.getJSONObject("item").getJSONObject("day30").get("change")) // 13
                , obj.getJSONObject("item").getJSONObject("day90").getString("trend") // 14
                , String.valueOf(obj.getJSONObject("item").getJSONObject("day90").get("change")) // 15
                , obj.getJSONObject("item").getJSONObject("day180").getString("trend") // 16
                , String.valueOf(obj.getJSONObject("item").getJSONObject("day180").get("change")) }; // 17

        return itemInfoArr;

    }
}

