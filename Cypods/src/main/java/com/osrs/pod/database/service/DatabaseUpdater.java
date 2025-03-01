package com.osrs.pod.database.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.osrs.pod.application.ApplicationConstant;
import com.osrs.pod.application.controllers.RequestController;
import com.osrs.pod.database.controller.DatabaseUpdaterController;
import com.osrs.pod.database.domain.entities.ItemsDb;
import com.osrs.pod.database.model.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

@Component
@ComponentScan(basePackages = {"com.osrs.pod.database"})
@EntityScan(basePackages = "com.osrs.pod.database.domain.entities")
public class DatabaseUpdater implements CommandLineRunner {

    public DatabaseUpdater(DatabaseTestService databaseTestService , ItemsDao itemsDao, ItemsDb itemsDb, DatabaseUpdaterController databaseUpdaterController, ApplicationConstant applicationConstant, ItemPriceOsrsDTO itemPriceOsrsDTO, RestTemplate restTemplate, RestTemplateBuilder restTemplateBuilder, String itemPriceURI) {
        this.databaseTestService = databaseTestService;
        this.itemsDao = itemsDao;
        this.itemsDb = itemsDb;
        this.databaseUpdaterController = databaseUpdaterController;
        this.applicationConstant = applicationConstant;
        this.itemPriceOsrsDTO = itemPriceOsrsDTO;
        this.restTemplate = restTemplate;
        this.restTemplateBuilder = restTemplateBuilder;
        this.itemPriceURI = itemPriceURI;
    }

    public DatabaseUpdater() {
    }

    @Autowired
    ItemsDao itemsDao;

    @Autowired
    ItemsDb itemsDb;

    @Autowired
    DatabaseUpdaterController databaseUpdaterController;

    @Autowired
    ApplicationConstant applicationConstant;

    @Autowired
    ItemPriceOsrsDTO itemPriceOsrsDTO;

    RestTemplate restTemplate;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    DatabaseTestService databaseTestService;

    RequestController requestController = new RequestController();

    String itemPriceURI = "https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=7323";

    public static List<ItemMaplet> itemMapletList = new ArrayList<>();

    public static LatestPriceNodes latestPriceNodes;

    @Override
    public void run(String... args) throws Exception {
//        databaseTestService.testConnection();
        Thread updateDatabaseItemsThread= new Thread(()->{
            try {
                updateDatabaseItems();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
       updateDatabaseItemsThread.start();
    }

    //Update lastest prices every 30 minutes
    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void updateLatestPriceNodes() throws JsonProcessingException {
        // This line fetches the latest price nodes.
        latestPriceNodes = requestController.getDataModeler().getLastestPrices(
                requestController.requestItemData(ApplicationConstant.WIKI_LATEST_PRICES));
        System.out.println("Updated latestPriceNodes at " + java.time.LocalDateTime.now());
    }

    /**Look for new items and add it to the database*/
    public void updateDatabaseItems() throws JsonProcessingException {
        //Retrive an array of all the items in the itemsDb
        List<ItemsDb> listOfItems = itemsDao.findAll();
        //Loop through all the items in the itemdb, and use the id to create url

        this.restTemplate = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        this.restTemplate.setMessageConverters(messageConverters);


        /**Find items that dont exist in the database, and add it*/
        itemMapletList = restTemplate.exchange(
                ApplicationConstant.itemMappingUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ItemMaplet>>() {}
        ).getBody();

        Set<Long> allDatabaseItems = listOfItems.stream().map(ItemsDb::getId).collect(Collectors.toSet());
        ArrayList<Integer> blackListedItemsList = new ArrayList<>();
        blackListedItemsList.addAll(List.of(ApplicationConstant.BLACK_LISTED_ITEMS));

        //Look for new items that don't exist in the database
        List<ItemMaplet> newItems = itemMapletList.stream().filter(itemMaplet ->
              !allDatabaseItems.contains(itemMaplet.getId().longValue())).collect(Collectors.toList());
                         newItems = newItems.stream().filter(curr ->
                                          !blackListedItemsList.contains(curr.getId())).collect(Collectors.toList());
//                                         !newItems.containsAll(blackListedItemsList)).collect(Collectors.toList());

        System.out.println("Found a new items: " + newItems.size());

        //Save all new items metadata in the db without item image
        newItems.forEach(curr ->{
            if(!blackListedItemsList.contains(curr.getId())){
                ItemsDb temp = new ItemsDb();
                temp.setId(curr.getId().longValue());
                temp.setItemId(curr.getId());
                temp.setItem_examine(curr.getExamine());
                temp.setItem_name(curr.getName());
                temp.setItem_limit(curr.getLimit());
                temp.setItem_high_alch(curr.getHighalch());
                temp.setItem_low_alch(curr.getLowalch());
                temp.setItem_value(curr.getValue());
                temp.setData(null);
                itemsDao.add(temp);
            }
        });

        listOfItems = itemsDao.findAll();

        /**Find any item that doesn't have an image icon and add it*/
        for(int i = 0; i < listOfItems.size() ; i++){
            if(listOfItems.get(i).getData() != null || blackListedItemsList.contains(listOfItems.get(i).getItemId())){
                continue;
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            headers.set("User-Agent","PostmanRuntime/7.36.1");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            //Call rs url
            StringBuffer sb = new StringBuffer(ApplicationConstant.RUNESCAPE_API_ITEM_LOOKUP_URL.concat(listOfItems.get(i).getId().toString()));
            try{
                ResponseItem item = restTemplate.exchange(sb.toString(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ResponseItem>() {}).getBody();

                //Get large icon spring url and save it
                getItemIconAndSaveIt(listOfItems.get(i).getId(),item.getItem().getIcon_large()); //3 icon sprite url
                int sleepTimer = getRandomNumberBetween(2000, 4000);
                Thread.sleep(sleepTimer);
//                System.out.println("Slept for " + sleepTimer + " after saving the item " +listOfItems.get(i).getId() + " "+ item.getItem().getName());
            }
            catch (Exception e){
                System.out.println("An error occurred during retrieving or saving an item: " + listOfItems.get(i).getId());
            }
        }
    }

    public void getItemIconAndSaveIt(long item_id, String url){
        byte[] logoImage = getLogoImage(url);
        insertBlob(item_id,logoImage);
    }

    private int getRandomNumberBetween(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }


    private byte[] getLogoImage(String url){
        try {
            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();

            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            byte[] data = new byte[1000];
//            ByteArrayBuffer baf = new ByteArrayBuffer(500);
            int current = 0;
            while ((current = bis.read(data,0,data.length)) != -1) {
                buffer.write(data,0,current);
            }

            return buffer.toByteArray();
        } catch (Exception e) {
            System.out.println("ImageManager" + "Error: " + e.toString());
        }
        return null;
    }

    public void insertBlob(long item_id, byte[] logoImage){

//        StringBuilder logoUrl = new StringBuilder("");

//        itemsDao = new ItemsDao();


        ItemsDb itemDb = itemsDao.findById(item_id);

//        System.out.println("Retrieved ItemDb ItemID: " + itemDb.getId());


//        System.out.println("Logo Image length: " + logoImage.length);

        itemDb.setData(logoImage);

        itemsDao.update(itemDb);
    }

    public static List<ItemMaplet> getItemMapletList() {
        return itemMapletList;
    }

    public static void setItemMapletList(List<ItemMaplet> itemMapletList) {
        DatabaseUpdater.itemMapletList = itemMapletList;
    }
}


//https://stackoverflow.com/questions/32138739/bytearraybuffer-missing-in-sdk23