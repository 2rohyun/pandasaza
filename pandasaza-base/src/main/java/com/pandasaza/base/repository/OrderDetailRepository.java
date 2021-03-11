package com.pandasaza.base.repository;

import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.OrderDetail;
import com.pandasaza.base.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {

    @Query(value = "select * from order_detail where order_id=:order_id", nativeQuery = true)
    OrderDetail findByOrderId(@Param("order_id") Long order_id);
}
