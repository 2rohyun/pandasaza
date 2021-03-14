package com.pandasaza.base.repository;

import com.pandasaza.base.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "select * from USER where user_id=:user_id", nativeQuery = true)
    User findByUserId(@Param("user_id") Long user_id);

    Optional<User> findByAccount(String account);

}
