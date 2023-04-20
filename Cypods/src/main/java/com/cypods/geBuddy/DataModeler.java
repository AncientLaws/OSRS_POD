package com.cypods.geBuddy;

//import org.jfree.data.json.impl.JSONObject;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
@Component
public class DataModeler {
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

    protected String [] dataModeler_osrs_api_parseItemJson(JSONObject obj) {
        String name = obj.getJSONObject("item").getString("name");
        String id = String.valueOf(obj.getJSONObject("item").getInt("id"));
        String icon = obj.getJSONObject("item").getString("icon");
        String icon_large = obj.getJSONObject("item").getString("icon_large");
        String type = obj.getJSONObject("item").getString("type");
        String typeIcon = obj.getJSONObject("item").getString("typeIcon");
        String description = obj.getJSONObject("item").getString("description");
        String members = obj.getJSONObject("item").getString("members");

        String currentPrice = String.valueOf(obj.getJSONObject("item").getJSONObject("current").get("price"));
        String currentTrend = obj.getJSONObject("item").getJSONObject("current").getString("trend");

        String todayPrice = String.valueOf(obj.getJSONObject("item").getJSONObject("today").get("price"));
        String todayTrend = obj.getJSONObject("item").getJSONObject("today").getString("trend");

        String day30_trend = obj.getJSONObject("item").getJSONObject("day30").getString("trend");
        String day30_change = String.valueOf(obj.getJSONObject("item").getJSONObject("day30").get("change"));
        String day90_trend = obj.getJSONObject("item").getJSONObject("day90").getString("trend");
        String day90_change = String.valueOf(obj.getJSONObject("item").getJSONObject("day90").get("change"));
        String day180_trend = obj.getJSONObject("item").getJSONObject("day180").getString("trend");
        String day180_change = String.valueOf(obj.getJSONObject("item").getJSONObject("day180").get("change"));

        String itemInfoArr2[] = { name // 0
                , id // 1
                , icon // 2
                , icon_large // 3
                , type // 4
                , typeIcon // 5
                , description // 6
                , members // 7

                , currentPrice // 8
                , currentTrend // 9

                , todayPrice // 10
                , todayTrend // 11

                , day30_trend // 12
                , day30_change // 13
                , day90_trend // 14
                , day90_change // 15
                , day180_trend // 16
                , day180_change }; // 17

        return itemInfoArr2;

    }
    public static XYDataset createRandomDataset() {
        XYSeries series = new XYSeries("S1");
        for (int x = 0; x < 10; x++) {
            series.add(x, x + Math.random() * 4.0);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        return dataset;
    }

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
