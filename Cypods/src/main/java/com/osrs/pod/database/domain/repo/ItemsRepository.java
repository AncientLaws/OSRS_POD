package com.osrs.pod.database.domain.repo;


import com.osrs.pod.database.domain.entities.ItemsDb;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ItemsRepository extends CrudRepository<ItemsDb,Long> {

    ItemsDb findById(long id);

    List<ItemsDb> findAll();

    <S extends ItemsDb> S save(S entity);

    boolean existsById(int id);

    Iterable<ItemsDb> findAllById(Iterable<Long> ids);

    void deleteById(Long id);

    @Query("select i from ItemsDb i where lower(i.item_name) like lower(concat('%', :item, '%')) order by i.item_name asc")
    List<ItemsDb> findByItemName(@Param("item") String item);


    @Modifying
    @Query(value = "INSERT INTO items (id, item_id, item_name, item_examine, item_limit, item_high_alch, item_low_alch, data, item_value) " +
            "VALUES (:id, :itemId, :itemName, :itemExamine, :itemLimit, :itemHighAlch, :itemLowAlch, :data, :itemValue)", nativeQuery = true)
    void insertItem(@Param("id") Long id,
                    @Param("itemId") Integer itemId,
                    @Param("itemName") String itemName,
                    @Param("itemExamine") String itemExamine,
                    @Param("itemLimit") Integer itemLimit,
                    @Param("itemHighAlch") Integer itemHighAlch,
                    @Param("itemLowAlch") Integer itemLowAlch,
                    @Param("data") byte[] data,
                    @Param("itemValue") Integer itemValue);
}
