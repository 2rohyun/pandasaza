package com.pandasaza.base.repository;

import com.pandasaza.base.model.entity.Dib;
import com.pandasaza.base.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DibRepository extends JpaRepository<Dib, Long> {

    @Query(value = "select COUNT(*) from DIB where item_item_id=:item_item_id", nativeQuery = true)
    Integer getLikeCount(@Param("item_item_id") Long item_item_id);

    @Query(value = "select * from DIB where item_item_id=:item_item_id and user_user_id=:user_user_id", nativeQuery = true)
    Optional<Dib> findByItemIdAndUserId(@Param("item_item_id") Long item_item_id,
                                        @Param("user_user_id") Long user_user_id);
}
