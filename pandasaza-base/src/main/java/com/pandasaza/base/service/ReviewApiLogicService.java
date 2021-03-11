package com.pandasaza.base.service;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.OrderDetail;
import com.pandasaza.base.model.entity.Review;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.OrderDetailApiRequest;
import com.pandasaza.base.model.network.request.ReviewApiRequest;
import com.pandasaza.base.model.network.response.OrderDetailApiResponse;
import com.pandasaza.base.model.network.response.ReviewApiResponse;
import com.pandasaza.base.repository.OrderDetailRepository;
import com.pandasaza.base.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewApiLogicService implements CrudInterface<ReviewApiRequest, ReviewApiResponse> {

    private final ReviewRepository reviewRepository;

    private final OrderDetailRepository orderDetailRepository;

    @Override
    public Header<ReviewApiResponse> create(Header<ReviewApiRequest> request) {
        ReviewApiRequest reviewApiRequest = request.getData();

        Review review = Review.builder()
                .registeredAt(LocalDateTime.now())
                .content(reviewApiRequest.getContent())
                .score(reviewApiRequest.getScore())
                .title(reviewApiRequest.getTitle())
                .orderDetail(orderDetailRepository.getOne(reviewApiRequest.getOrderDetailOrderDetailId()))
                .build();

        Review newReview = reviewRepository.save(review);

        return response(newReview);

    }

    @Override
    public Header<ReviewApiResponse> read(Long id) {
        Optional<Review> optional = reviewRepository.findById(id);

        return optional
                .map(review -> response(review))
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header<ReviewApiResponse> update(Header<ReviewApiRequest> request) {
        ReviewApiRequest reviewApiRequest = request.getData();

        Optional<Review> optional = reviewRepository.findById(reviewApiRequest.getReviewId());

        return optional.map(review->{

            review.setTitle(reviewApiRequest.getTitle())
                    .setRegisteredAt(reviewApiRequest.getRegisteredAt())
                    .setContent(reviewApiRequest.getContent())
                    .setScore(reviewApiRequest.getScore());

            return review;
        })
                .map(review->reviewRepository.save(review))
                .map(updateReview->response(updateReview))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<Review> optional = reviewRepository.findById(id);

        return optional.map(review->{
            reviewRepository.delete(review);
            return Header.OK();
        })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private Header<ReviewApiResponse> response(Review review){

        ReviewApiResponse reviewApiResponse = ReviewApiResponse.builder()
                .reviewId(review.getReviewId())
                .orderDetailOrderDetailId(review.getOrderDetail().getOrderId())
                .content(review.getContent())
                .score(review.getScore())
                .title(review.getTitle())
                .registeredAt(review.getRegisteredAt())
                .build();
        //Header + data return
        return Header.OK(reviewApiResponse);
    }
}
