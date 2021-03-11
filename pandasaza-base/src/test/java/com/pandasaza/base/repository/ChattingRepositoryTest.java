package com.pandasaza.base.repository;

import com.pandasaza.base.BaseApplicationTests;
import com.pandasaza.base.model.entity.Chatting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ChattingRepositoryTest extends BaseApplicationTests {

    @Autowired
    private ChattingRepository chattingRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void create(){

        Chatting chatting = new Chatting();

        chatting.setChatContent("얼마에요? 깎아주세요 ㅎ");
        chatting.setOrderDetail(orderDetailRepository.findByOrderId(1L));

        Chatting newChatting = chattingRepository.save(chatting);
        Assertions.assertNotNull(newChatting);
    }

    @Test
    public void read(){

        Optional<Chatting> chatting = chattingRepository.findById(1L);

        chatting.ifPresent(c->{
            System.out.println(c.getChatContent());
            System.out.println(c.getOrderDetail().getOrderId());
        });
    }

}
