package com.pandasaza.base.repository;

import com.pandasaza.base.model.entity.Category;
import com.pandasaza.base.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query(value = "select * from CATEGORY where category_id=:category_id", nativeQuery = true)
    Category findByCategoryId(@Param("category_id") Long category_id);
}
