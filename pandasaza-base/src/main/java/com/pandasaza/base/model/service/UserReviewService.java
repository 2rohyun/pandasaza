package com.pandasaza.base.model.service;

import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.OrderDetail;
import com.pandasaza.base.model.entity.Review;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReviewService {

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

        List<Integer> scoreHistory = null;

        if(user.getItemList() != null) {
            for (Item i : user.getItemList()) {
                for (OrderDetail j : i.getOrderDetailList().subList(0,2)) {
                    for (Review k : j.getReviewList()) {
                        scoreHistory.add(k.getScore());
                    }
                }
            }
        }
        return scoreHistory;
    }

    public List<String> getReviewHistory(User user) {

        List<String> reviewHistory = null;

        if(user.getItemList() != null) {
            for (Item i : user.getItemList()) {
                for (OrderDetail j : i.getOrderDetailList()) {
                    for (Review k : j.getReviewList().subList(0,2)) {
                        reviewHistory.add(k.getTitle());
                    }
                }
            }
        }
        return reviewHistory;
    }
}
