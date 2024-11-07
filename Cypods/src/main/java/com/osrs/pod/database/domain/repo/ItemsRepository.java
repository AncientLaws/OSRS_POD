package com.osrs.pod.database.domain.repo;


import com.osrs.pod.database.domain.entities.ItemsDb;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ItemsRepository extends CrudRepository<ItemsDb,Long> {

    ItemsDb findById(long id);

    List<ItemsDb> findAll();

    <S extends ItemsDb> S save(S entity);

    boolean existsById(int id);

    Iterable<ItemsDb> findAllById(Iterable<Long> ids);

    void deleteById(Long id);

    ItemsDb findByItemIdEquals(Integer item_id);
}
