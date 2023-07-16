package com.cypods.geBuddy;

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

    //ITEM META DATA MAPPER
    public static int ITEM_META_EXAMINE = 0;
    public static int ITEM_META_ID = 1;
    public static int ITEM_META_LOW_ALCH = 2;
    public static int ITEM_META_GE_LIMIT = 3;
    public static int ITEM_META_VALUE = 4;
    public static int ITEM_META_ALCH_VALUE = 5;
    public static int ITEM_META_NAME = 6;

    public static String RUNESCAPE_API_ITEM_LOOKUP_URL ="https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=";
    public static String RUNESCAPE_API_ITEM_SEARCH_URL = "https://services.runescape.com/m=itemdb_oldschool/api/catalogue/items.json?category=1&alpha=";
    public static String RSWIKI_API_ITEM_MAPPING_URL = "https://prices.runescape.wiki/api/v1/osrs/mapping";
    public static String RSWIKI_API_ITEM_LOOKUP_URL= "https://prices.runescape.wiki/api/v1/osrs/timeseries?timestep=";
    public static String RUNESCAPE_API_ITEM_GRAPH_URL="https://services.runescape.com/m=itemdb_oldschool/api/graph/";

}
