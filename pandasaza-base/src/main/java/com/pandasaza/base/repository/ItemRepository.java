package com.pandasaza.base.repository;

import com.pandasaza.base.model.entity.Category;
import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {

    @Query(value = "select * from ITEM where item_id=:item_id", nativeQuery = true)
    Item findByItemId(@Param("item_id") Long item_id);

    @Query(value = "update item set cnt_show = IFNULL(cnt_show,0) + 1 where item_id=:item_id", nativeQuery = true)
    void updateHitByItemId(@Param("item_id")Long item_id);

    @Query(value = "select * from ITEM where category_category_id=:category_category_id order by cnt_show desc limit 5", nativeQuery = true)
    List<Item> findByCategoryId(@Param("category_category_id") Long category_category_id);
}
