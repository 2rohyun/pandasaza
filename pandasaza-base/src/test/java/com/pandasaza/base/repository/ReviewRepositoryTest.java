package com.pandasaza.base.repository;

import com.pandasaza.base.BaseApplicationTests;
import com.pandasaza.base.model.entity.Chatting;
import com.pandasaza.base.model.entity.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class ReviewRepositoryTest extends BaseApplicationTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void create(){
        Review review = new Review();

        review.setContent("이 분 신뢰도 개지려요");
        review.setRegisteredAt(LocalDateTime.now());
        review.setTitle("완전 좋아요 ! ");
        review.setScore(5);
        review.setOrderDetail(orderDetailRepository.findByOrderId(1L));

        Review newReview = reviewRepository.save(review);
        Assertions.assertNotNull(newReview);
    }

    @Test
    public void read(){

        Optional<Review> review = reviewRepository.findById(1L);

        review.ifPresent(r->{
            System.out.println(r.getContent());
            System.out.println(r.getOrderDetail().getOrderId());
        });
    }
}
