package com.pandasaza.base.repository;

import com.pandasaza.base.model.entity.Review;
import com.pandasaza.base.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
}
