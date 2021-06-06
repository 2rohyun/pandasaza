package com.pandasaza.base.model.service;

import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.OrderDetail;
import com.pandasaza.base.model.entity.Review;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReviewService {

    public float getAvgScore(User user) {
        float avg = 0f;
        int cnt = 0;

        List<Review> reviewList = user.getReviewList();
        if (reviewList != null) {
            for (Review review : reviewList) {
                avg += review.getScore();
                cnt++;
            }
            avg = avg / cnt;
            return avg;
        } else {
            return 0f;
        }
    }

    public List<Integer> getScoreHistory(User user) {

        List<Integer> scoreHistory = new ArrayList<>(Collections.emptyList());

        List<Review> reviewList = user.getReviewList();
        if (reviewList != null){
            if (reviewList.size()>=3){
                for(Review review : reviewList.subList(0,2)){
                    scoreHistory.add(review.getScore());
                }
            }else{
                for(Review review : reviewList){
                    scoreHistory.add(review.getScore());
                }
            }

        }
        return scoreHistory;
    }

    public List<String> getReviewHistory(User user) {

        List<String> reviewHistory = new ArrayList<>(Collections.emptyList());
        List<Review> reviewList = user.getReviewList();
        if (reviewList != null){
            if (reviewList.size()>=3){
                for(Review review : reviewList.subList(0,2)){
                    reviewHistory.add(review.getTitle());
                }
            }else{
                for(Review review : reviewList){
                    reviewHistory.add(review.getTitle());
                }
            }

        }

        return reviewHistory;
    }


}



/*
    public float getAvgScore(User user) {

        float avg = 0f;
        int cnt = 0;

        if(user.getItemList() != null) {
            for (Item i : user.getItemList()) {
                for (OrderDetail j : i.getOrderDetailList()) {
                    for (Review k : j.getReviewList()) {
                        avg += k.getScore();
                        cnt++;
                    }
                }
            }
            avg = avg / cnt;
            return avg;
        }else{
            return 0f;
        }
    }

    public List<Integer> getScoreHistory(User user) {

        List<Integer> scoreHistory = new ArrayList<>(Collections.emptyList());

        if(user.getItemList() != null) {
            for (Item i : user.getItemList()) {
                if(i.getOrderDetailList().size() >= 3) {
                    for (OrderDetail j : i.getOrderDetailList().subList(0, 2)) {
                        for (Review k : j.getReviewList()) {
                            scoreHistory.add(k.getScore());
                        }
                    }
                } else{
                    for (OrderDetail j : i.getOrderDetailList()) {
                        for (Review k : j.getReviewList()) {
                            scoreHistory.add(k.getScore());
                        }
                    }
                }
            }
        }

        return scoreHistory;
    }

    public List<String> getReviewHistory(User user) {

        List<String> reviewHistory = new ArrayList<>(Collections.emptyList());

        if(user.getItemList() != null) {
            for (Item i : user.getItemList()) {
                if(i.getOrderDetailList().size() >= 3) {
                    for (OrderDetail j : i.getOrderDetailList().subList(0, 2)) {
                        for (Review k : j.getReviewList()) {
                            reviewHistory.add(k.getTitle());
                        }
                    }
                } else {
                    for (OrderDetail j : i.getOrderDetailList()) {
                        for (Review k : j.getReviewList()) {
                            reviewHistory.add(k.getTitle());
                        }
                    }
                }
            }
        }

        return reviewHistory;
    }

 */

