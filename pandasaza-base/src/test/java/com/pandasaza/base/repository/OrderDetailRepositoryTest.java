package com.pandasaza.base.repository;

import com.pandasaza.base.BaseApplicationTests;
import com.pandasaza.base.model.entity.OrderDetail;
import com.pandasaza.base.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

public class OrderDetailRepositoryTest extends BaseApplicationTests {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create(){
        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setStatus("WAITING")
                .setUser(userRepository.findByUserId(1L)) //구매자 정보
                .setItem(itemRepository.findByItemId(1L)); //판매 상품 ( 판매자 ) 정보

        OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);

        Assertions.assertNotNull(newOrderDetail);
    }

    @Test
    @Transactional
    public void read(){
        OrderDetail orderDetail = orderDetailRepository.findByOrderId(1L);

        System.out.println("주문 상태 : " + orderDetail.getStatus());
        //System.out.println("판매자 : " + orderDetail.getItem().getUser().getAccount());
        //System.out.println("구매자 : " + orderDetail.getUser().getAccount());
        orderDetail.getChattingList().forEach(chatting ->{
            System.out.println("채팅방 ID : " + chatting.getChattingId());
        });
//        orderDetail.getReviewList().forEach(review -> {
//            System.out.println("리뷰 ID : " + review.getReviewId());
//        });
    }

}
