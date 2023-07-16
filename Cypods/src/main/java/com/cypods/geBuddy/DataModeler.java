package com.cypods.geBuddy;

//import org.jfree.data.json.impl.JSONObject;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static com.cypods.geBuddy.ApplicationConstant.*;

@Component
public class DataModeler {

    @Autowired
    ApplicationConstant applicationConstant;

    @Autowired
    ItemsDao itemsDao;
    /**
     * @Purpose
     * Method to convert epoch time to date with adding 1000 multiplier
     * Exists because runelite's price timeseries API returns epoch time divided by a 1000
     *
     * @return Date
     */
    protected Date epochToDateTime_x1000(String epoch) {

        long longEpoch = Long.parseLong(epoch) * 1000;
        LocalDateTime localDateTime = Instant.ofEpochMilli(longEpoch).atZone(ZoneId.systemDefault()).toLocalDateTime();
        Instant i = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        java.util.Date date1 = Date.from(i);
        return date1;
    }

    /**
     * @Purpose
     * Method to convert epoch time to date without adding 1000 multiplier
     * Exists because osrs graph API returns correct epoch time
     *
     * @return Date
     */
    protected Date epochToDateTime(String epoch) {

        long longEpoch = Long.parseLong(epoch);
        LocalDateTime localDateTime = Instant.ofEpochMilli(longEpoch).atZone(ZoneId.systemDefault()).toLocalDateTime();
        Instant i = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        java.util.Date date1 = Date.from(i);
        return date1;
    }

    /**
     * @Purpose
     * Parses Osrs API specific item search JSON response and assigns key to String values.
     * String values are used for labels
     * */
    protected String [] dataModeler_osrs_api_parseItemJson(JSONObject obj) {

        String itemInfoArr2[] = { obj.getJSONObject("item").getString("name") // 0
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

        return itemInfoArr2;

    }

    /*
    * @Purpose
    * Given the Json object result from the metadata api response, parse the json result and save it into an array
    * */
    public String[][] dataModeler_wiki_item_metaData(JSONObject jsonObject){
        String[][] metaDataParsingResult = new String[4000][10];
        JSONObject obj = jsonObject;
        try{
            for(int i = 0; i < obj.length() - 1; i++) {
                if(obj.has("examine")){
                    metaDataParsingResult[i][0] = obj.getString("examine");
                }
                if(obj.has("id")){
                    metaDataParsingResult[i][1] = String.valueOf(obj.get("id"));
                }
                if(obj.has("lowalch")){
                    metaDataParsingResult[i][2] = String.valueOf(obj.get("lowalch"));
                }
                if(obj.has("limit")){
                    metaDataParsingResult[i][3] = String.valueOf(obj.get("limit"));
                }
                if(obj.has("value")){
                    metaDataParsingResult[i][4] = String.valueOf(obj.get("value"));
                }
                if(obj.has("highalch")){
                    metaDataParsingResult[i][5] = String.valueOf(obj.get("highalch"));
                }
                if(obj.has("name")){
                    metaDataParsingResult[i][6] = obj.getString("name");
                }

            }
        }
        catch (JSONException JE){
            System.out.println("JSON error was thrown in DataModeler.dataModeler_wiki_item_metaData due to: "+ JE.getMessage() + JE.getStackTrace());

        }
        return metaDataParsingResult;
    }

    /*
    * @Purpose
    * Search for a specific item's metadata given the passed 2d array
    * */
    protected String[] searchForItemMetaData( int itemId){
        String[] metaDataSearchResult = new String[10];
        ItemsDb itemsDb= itemsDao.findById(itemId);
        System.out.println("Found Item " + itemsDb.getItem_name() + "from the database via the DataModeler.searchForItemMetaData method");
                metaDataSearchResult[ITEM_META_EXAMINE]     = itemsDb.getItem_examine();
                metaDataSearchResult[ITEM_META_ID]          = itemsDb.getItemId().toString();
                metaDataSearchResult[ITEM_META_LOW_ALCH]    = itemsDb.getItem_low_alch().toString();
                metaDataSearchResult[ITEM_META_GE_LIMIT]    = itemsDb.getItem_limit().toString();
                metaDataSearchResult[ITEM_META_VALUE]       = itemsDb.getItem_value().toString();
                metaDataSearchResult[ITEM_META_ALCH_VALUE]  = itemsDb.getItem_high_alch().toString();
                metaDataSearchResult[ITEM_META_NAME]        = itemsDb.getItem_name();
        return metaDataSearchResult;
    }

    /**
     * @Purpose
     * Create random XYDataset to be used when initalizing price chart
     * */
    public static XYDataset createRandomDataset() {
        XYSeries series = new XYSeries("S1");
        for (int x = 0; x < 10; x++) {
            series.add(x, x + Math.random() * 4.0);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        return dataset;
    }

    /**
     * @Purpose
     * Create random DefaultCategoryDataset to be used when initalizing volume chart
     * */
    protected  DefaultCategoryDataset createRandomCategoryDataset(){
        DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
        String dateString = "1622";
        int data = 1622;
        for (int x = 0; x < 200; x++) {
            dateString = String.valueOf((int)Math.round((data + Math.random()*x)));
            defaultCategoryDataset.addValue(x + Math.random() * 4, "null", dateString );
        }
        return defaultCategoryDataset;
    }

    protected DefaultCategoryDataset createBarchartCategoryDataset(){
        DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();

        return defaultCategoryDataset;
    }

    /**
     * @Purpose
     * Get the average value given two inputs
     * */
    protected double avgValue(double highValue, double lowValue) {
        double average = (highValue + lowValue) / 2;
        return average;
    }

    /**
     * @Purpose
     * Shortens the numbers in the axis of graphs to make it easily readable
     * @Credit
     * https://stackoverflow.com/questions/43280204/y-axis-is-not-displaying-correct-figure-for-millions-and-billions-in-jfreechart?noredirect=1&lq=1#
     * */
    protected void setNumberFormatOverrideAxis(long MILLION, long BILLION, long TRILLION, long THOUSAND, NumberAxis priceChartAxis) {
        priceChartAxis.setNumberFormatOverride(new NumberFormat() {

            @Override
            public Number parse(String source, ParsePosition parsePosition) {
                return null;
            }

            @Override
            public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {

                String temp =  number < THOUSAND ? String.valueOf((number * 100.0) / 100.0) :
                        number < MILLION ?  ((double)((number / THOUSAND)* 100.0) / 100.0) + " K" :
                                number < BILLION ?  ((double)((number / MILLION)* 100.0) / 100.0) + " M" :
                                        number < TRILLION ? Math.round(((double)(number / BILLION)* 100.0) / 100.0) + " B" :
                                                ((double)((number / TRILLION)* 100.0) / 100.0) + " T";
                return new StringBuffer(temp);
            }

            @Override
            public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {

                String temp =  number < THOUSAND ? String.valueOf((number * 100.0) / 100.0) :
                        number < MILLION ?  (((number / THOUSAND) * 100.0) / 100.0) + " K" :
                                number < BILLION ?  (((number / MILLION)* 100.0) / 100.0) + " M" :
                                        number < TRILLION ? (((number / BILLION)* 100.0) / 100.0) + " B" :
                                                (((number / TRILLION)* 100.0) / 100.0) + " T";
                return new StringBuffer(temp);
            }
        });
    }


}
