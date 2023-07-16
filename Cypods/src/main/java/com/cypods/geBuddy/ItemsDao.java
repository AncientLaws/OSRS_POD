package com.cypods.geBuddy;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@ComponentScan({"com.cypods.geBuddy.Configuration"})
public class ItemsDao{

//    private final JdbcTemplate jdbc;
      private final ItemsRepository repository;

    public ItemsDao(ItemsRepository repository) {
        this.repository = repository;
    }


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

//    @Query("SELECT item FROM ItemsDb item where item.id = :id")
//    ItemsDb retrieveItem(@Param("id") long id) {
//        return null;
//    }

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

////    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("Number of items in database: " + repository.count());
//    }
}
