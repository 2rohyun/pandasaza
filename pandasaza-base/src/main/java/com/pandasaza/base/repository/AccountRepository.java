package com.pandasaza.base.repository;

import com.pandasaza.base.model.entity.Account;
import com.pandasaza.base.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
}
