package com.pandasaza.base.repository;

import com.pandasaza.base.model.entity.Chatting;
import com.pandasaza.base.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingRepository extends JpaRepository<Chatting,Long> {
}
