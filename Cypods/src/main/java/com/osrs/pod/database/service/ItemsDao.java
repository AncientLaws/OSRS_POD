package com.osrs.pod.database.service;

import com.osrs.pod.database.domain.entities.ItemsDb;
import com.osrs.pod.database.domain.repo.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@ComponentScan(basePackages = {"com.osrs.pod.database.domain.entities", "com.osrs.pod.database"})
@Transactional
public class ItemsDao {

    private final JdbcTemplate jdbc;

    private final ItemsRepository repository;

    public ItemsDao(JdbcTemplate jdbc, ItemsRepository repository) {
        this.jdbc = jdbc;
        this.repository = repository;
        System.out.println("Calling db count after init: " + repository.findAll().size());

    }

    @Transactional
    public List<ItemsDb> findAll(){
        return repository.findAll();
    }
    @Transactional
    public List<ItemsDb> findItemBySearch(String item){
        return repository.findByItemName(item);
    }

    @Transactional
    public ItemsDb findById(long id){
        if(repository.existsById(id)){
            return repository.findById(id);
        }
        else{
            throw new IllegalArgumentException("ItemDb Id" + id + " does not exist");
        }
    }

    @Query("SELECT item FROM ItemsDb item where item.id = :id")
    ItemsDb retrieveItem(@Param("id") long id) {
        return null;
    }

    @Transactional
    public synchronized boolean add(ItemsDb itemsDb){
        try{
//                return repository.save(itemsDb)!=null;
            repository.insertItem(itemsDb.getId().longValue(),itemsDb.getItemId().intValue(),itemsDb.getItem_name(),itemsDb.getItem_examine(),itemsDb.getItem_limit(),
                    itemsDb.getItem_high_alch(), itemsDb.getItem_low_alch(), itemsDb.getData(),itemsDb.getItem_value());
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("An error occurred while saving the ItemsDb to the database!");
        }
    }

    @Transactional
    public synchronized  boolean saveAll(List<ItemsDb> itemsDbList){
        try{
            repository.saveAll(itemsDbList);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("An error occurred while saving the ItemsDb List to the database!");
        }
    }

    @Transactional
    public boolean update(ItemsDb itemsDb){
        System.out.println("To String! "  + itemsDb.toString());
        if(repository.existsById(itemsDb.getItemId().longValue())){

            return repository.save(itemsDb) != null;
        }
        else{
            throw new IllegalArgumentException("ItemsDb doesn't exist, therefore it cannot be updated");
        }
    }
    @Transactional
    public void delete(ItemsDb itemsDb) {
        if(repository.existsById(itemsDb.getItemId().longValue()))
        {
            repository.deleteById(itemsDb.getItemId().longValue());
        }
        else{
            throw new IllegalArgumentException("ItemsDb Id doesn't exist");
        }
    }
}
