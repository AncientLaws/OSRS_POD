package com.osrs.pod.database.service;

import com.osrs.pod.application.ApplicationConstant;
import com.osrs.pod.database.controller.DatabaseUpdaterController;
import com.osrs.pod.database.domain.entities.ItemsDb;
import com.osrs.pod.database.model.ItemPriceOsrsDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.ComponentScan;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@ComponentScan(basePackages = {"com.cypods.geBuddy", "com.cypods.dbupdater"})
@EntityScan(basePackages = "com.cypods.dbupdater.database.entities")
public class DatabaseUpdater implements CommandLineRunner {

    @Autowired
    public DatabaseUpdater() {
//        this.itemsDao = itemsDao;
//        getItemIconAndSaveIt(13652, "https://secure.runescape.com/m=itemdb_oldschool/1688984225416_obj_big.gif?id=13652");
    }

    public DatabaseUpdater(ItemsDao itemsDao, ItemsDb itemsDb, DatabaseUpdaterController databaseUpdaterController, ApplicationConstant applicationConstant, ItemPriceOsrsDTO itemPriceOsrsDTO, RestTemplate restTemplate, RestTemplateBuilder restTemplateBuilder, String itemPriceURI) {
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

    String itemPriceURI = "https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=7323";

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Running database updater");

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



        if(true){
        for(int i = 0; i<listOfItems.size();i++){
            String[] itemInfoArr = null;
            itemInfoArr = new String [18];
            if(listOfItems.get(i).getData() != null){
                continue;
            }
                JSONObject obj = databaseUpdaterController.getItemJson(applicationConstant.RUNESCAPE_API_ITEM_LOOKUP_URL.concat(listOfItems.iterator().next().getId().toString()));
                itemInfoArr = databaseUpdaterController.dataModeler_osrs_api_parseItemJson(obj);
                getItemIconAndSaveIt(listOfItems.iterator().next().getId(),itemInfoArr[3]); //3 icon sprite url

                System.out.println();

                int sleepTimer = getRandomNumberBetween(2000, 4000);
                Thread.sleep(sleepTimer);
                System.out.println("Slept for " + sleepTimer + " after saving the item " +listOfItems.iterator().next().getId() + " "+ itemInfoArr[0].toString());

        }
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