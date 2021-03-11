package com.pandasaza.base.repository;

import com.pandasaza.base.model.entity.Admin;
import com.pandasaza.base.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
}
