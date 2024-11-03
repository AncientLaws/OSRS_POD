package com.cypods.dbupdater.Database.Dao;

import com.cypods.dbupdater.Database.Entities.ItemsDb;
import com.cypods.dbupdater.Database.Interfaces.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@ComponentScan(basePackages = {"com.cypods.geBuddy", "com.cypods.dbupdater"})
public class ItemsDao {

    private final JdbcTemplate jdbc;
    private final ItemsRepository repository;

    @Autowired
    public ItemsDao(JdbcTemplate jdbc, ItemsRepository repository) {
        this.jdbc = jdbc;
        this.repository = repository;
    }
//
//    @Autowired
//    JdbcTemplate jdbc;
//
//    @Autowired
//    ItemsRepository repository;

//        public ItemsDb findItemByItemId(Integer item_id){
//        return jdbc.queryForObject("select * from items where item_id = ?",
//                new Object[] {item_id},
//                new BeanPropertyRowMapper<ItemsDb>(ItemsDb.class));
//
//    }
//
//    @Query("SELECT i FROM items i WHERE i.item_id =:item_id")
//     ItemsDb findByItemIdContaining(@Param("item_id"),Integer item_id);

//    @Transactional
//    public ItemsDb findByItemId(Integer item_id){
//
//        if(repository.findByItemIdEquals(item_id).equals(null))
//            return Item
//        return repository.findByItemIdEquals(item_id);
//    }

    @Transactional
    public List<ItemsDb> findAll(){
        return repository.findAll();
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
    public boolean add(ItemsDb itemsDb){
        try{
//               if(repository.existsById(dashboardDb.getDASH_ID())!=true){
            return repository.save(itemsDb)!=null;
//               }
//               else{
//                   throw new IllegalArgumentException("Unable to add Dashboard. This is likely because the Dashboard ID exists");
//               }

        }
        catch (Exception e){
            throw new IllegalArgumentException("An error occurred while saving the ItemsDb to the database!");
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
