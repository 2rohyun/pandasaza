package com.pandasaza.base.repository;

import com.pandasaza.base.model.entity.UnactiveUser;
import com.pandasaza.base.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UnactiveUserRepository extends JpaRepository<UnactiveUser,Long> {

}
