package com.osrs.pod.database.service;

import com.osrs.pod.application.ApplicationConstant;
import com.osrs.pod.database.controller.DatabaseUpdaterController;
import com.osrs.pod.database.domain.entities.ItemsDb;
import com.osrs.pod.database.model.Item;
import com.osrs.pod.database.model.ItemMaplet;
import com.osrs.pod.database.model.ItemPriceOsrsDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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


    public DatabaseUpdater() {
//        this.itemsDao = itemsDao;
//        getItemIconAndSaveIt(13652, "https://secure.runescape.com/m=itemdb_oldschool/1688984225416_obj_big.gif?id=13652");
    }

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

    String itemPriceURI = "https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=7323";

    List<ItemMaplet> itemMapletList = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {
//        databaseTestService.testConnection();

//        long i = 13652L;
//        getItemIconAndSaveIt(i,"https://secure.runescape.com/m=itemdb_oldschool/1688984225416_obj_big.gif?id=13652");

        //Retrive an array of all the items in the itemsDb
        List<ItemsDb> listOfItems = itemsDao.findAll();
        //Loop through all the items in the itemdb, and use the id to create url

        this.restTemplate = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        this.restTemplate.setMessageConverters(messageConverters);

        //Last stop
//
//       testing purposes
//        Object itemPrice = restTemplate.getForObject(itemPriceURI, Object.class);
//
//        System.out.println(itemPrice);


        /**Find items that dont exist in the database, and add it*/
        itemMapletList = restTemplate.exchange(
                ApplicationConstant.itemMappingUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ItemMaplet>>() {}
        ).getBody();

        Set<Long> allDatabaseItems = listOfItems.stream().map(ItemsDb::getId).collect(Collectors.toSet());

        List<ItemMaplet> newItems = itemMapletList.stream().filter(itemMaplet -> !allDatabaseItems.contains(itemMaplet.getId().longValue())).collect(Collectors.toList());

        System.out.println("Found a new items: " + newItems.size());

        List<ItemsDb> itemsDbList = new ArrayList<>();

        newItems.forEach(curr ->{
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
            itemsDbList.add(temp);

        });
        itemsDao.add(itemsDbList.get(1));
        System.out.println("Total of ".concat(String.valueOf(itemsDbList.size())).concat(" potential items to save with test item added ".concat(String.valueOf(itemsDbList.get(1).getId()))));
//        itemsDao.saveAll(itemsDbList);


        /**Find any item that doesn't have an image icon and add it*/
        for(int i = 0; i<listOfItems.size();i++){
//            String[] itemInfoArr = null;
//            itemInfoArr = new String [18];
            if(listOfItems.get(i).getData() != null){
                continue;
            }
                //Call rs url
                Item item = restTemplate.getForObject(ApplicationConstant.RUNESCAPE_API_ITEM_LOOKUP_URL.concat(listOfItems.get(i).getId().toString()), Item.class);
//                JSONObject obj = databaseUpdaterController.getItemJson(applicationConstant.RUNESCAPE_API_ITEM_LOOKUP_URL.concat(listOfItems.iterator().next().getId().toString()));
                //Map respose to array
//                itemInfoArr = databaseUpdaterController.dataModeler_osrs_api_parseItemJson(obj);
                //Get large icon spring url and save it
                getItemIconAndSaveIt(listOfItems.get(i).getId(),item.getIcon_large()); //3 icon sprite url

                int sleepTimer = getRandomNumberBetween(2000, 4000);
                Thread.sleep(sleepTimer);
                System.out.println("Slept for " + sleepTimer + " after saving the item " +listOfItems.get(i).getId() + " "+ item.getName());


        //use the method getItemIconAndSaveIt to save the icon to the db
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


        System.out.println("Logo Image length: " + logoImage.length);

        itemDb.setData(logoImage);

        itemsDao.update(itemDb);
    }

}


//https://stackoverflow.com/questions/32138739/bytearraybuffer-missing-in-sdk23