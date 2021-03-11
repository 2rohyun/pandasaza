package com.pandasaza.base.repository;

import com.pandasaza.base.model.entity.ThumbnailItem;
import com.pandasaza.base.model.entity.UnactiveUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbnailItemRepository extends JpaRepository<ThumbnailItem,Long> {

    @Query(value = "update thumbnail_item set cnt_show = IFNULL(cnt_show,0) + 1 where ti_id=:ti_id", nativeQuery = true)
    void updateHitByItemId(@Param("ti_id")Long ti_id);

}
