package com.osrs.pod.application;

import org.springframework.stereotype.Component;

@Component
public class ApplicationConstant {

        //OSRS GE ITEM RESULT INDEX
        public static int ITEM_NAME = 0;
        public static int ITEM_ID = 1;
        public static int ITEM_SPRITE_URL = 3;
        public static int ITEM_DESC = 6;
        public static int ITEM_IS_MEMBER = 7;
        public static int ITEM_CURRENT_PRICE = 8;
        public static int ITEM_CURRENT_TREND = 9;
        public static int ITEM_TODAY_PRICE = 10;
        public static int ITEM_TODAY_TREND = 11;
        public static int ITEM_TREND_30 = 12;
        public static int ITEM_CHANGE_30 = 13;
        public static int ITEM_TREND_90 = 14;
        public static int ITEM_CHANGE_90 = 15;
        public static int ITEM_TREND_180 = 16;
        public static int ITEM_CHANGE_180 = 17;

        //OSRS ITEM SEAERCH JSON RESPONSE
        public static int GE_SEARCH_ICON_URL = 0;
        public static int GE_SEARCH_ID = 1;
        public static int GE_SEARCH_NAME = 2;
        public static int GE_SEARCH_CURRENT_PRICE = 3;
        public static int GE_SEARCH_TREND_TODAY = 4;
        public static int GE_SEARCH_PRICE_TODAY = 5;

        public static String runeLitePriceData = "https://prices.runescape.wiki/api/v1/osrs/timeseries?timestep=";

        public static String itemGraphUrl = "https://services.runescape.com/m=itemdb_oldschool/api/graph/";

        public static String osrsItemSearch = "https://services.runescape.com/m=itemdb_oldschool/api/catalogue/items.json?category=1&alpha=";

        public static String osrsGetItemDetails = "https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=";

        public static String itemMappingUrl = "https://prices.runescape.wiki/api/v1/osrs/mapping";

        public static boolean DEBUG = false;

        public static boolean BORDERS = false;

        public static boolean transparentBackground = false;

        public static String RUNESCAPE_API_ITEM_LOOKUP_URL ="https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=";

        public static Integer[] BLACK_LISTED_ITEMS = new Integer[]{
                29098,  //Not meat
                28860,  //Polar camo legs (equipped)
                28857,  //Polar camo top (equipped)
                28854,  //Desert camo legs (equipped)
                28851,  //Desert camo top (equipped)
                28848,  //Jungle camo legs (equipped)
                28845,  //Jungle camo top (equipped)
                28842,  //Wood camo legs (equipped)
                28839,  //Wood camo top (equipped)
                28585  //Warped sceptre
        };

        public static String generateRuneLitePriceDataUrl(String timePeriod, Integer itemId){
                if(timePeriod != null && itemId!= null){
                        return runeLitePriceData.concat(timePeriod).concat("&id=").concat(itemId.toString());
                }
                return "";
        }

        public static String generateOsrsPriceDataUrl(Integer itemId){
                if(itemId != null){
                        return itemGraphUrl.concat(itemId.toString()).concat(".json");
                }
                return "";
        }

    }
