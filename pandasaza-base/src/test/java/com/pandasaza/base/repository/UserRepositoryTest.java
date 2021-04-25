package com.pandasaza.base.repository;

import com.pandasaza.base.BaseApplicationTests;
import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.model.enumclass.UserStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryTest extends BaseApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        for(int i = 0;i<10;i++){
            User user = new User();
            user.setAccount("이도팔")
                    .setPassword("password")
                    .setLastLoginAt(LocalDateTime.now())
                    .setStatus(UserStatus.REGISTERED)
                    .setRegisteredAt(LocalDateTime.now())
                    .setEmail("anan@gmail.com")
                    .setAuthHistory("-")
                    .setAuthMethods("-")
                    .setNation("korea")
                    .setPhoneNumber("010-1111-2222")
                    .setUniversity("dongguk")
                    .setProfileIcon("gggg");

            User newUser = userRepository.save(user);
            Assertions.assertNotNull(newUser);
        }

    }

    @Test
    @Transactional
    public void read(){
        Optional<User> user = userRepository.findById(1L);

        user.ifPresent(u->{
            System.out.println("-----------------------------유저 기본 정보-----------------------------");
            System.out.println("계정 : " + u.getAccount());
            System.out.println("비밀번호 : " + u.getPassword());
            System.out.println("휴대폰 번호 : " + u.getPhoneNumber());
            System.out.println("이메일 : " + u.getEmail());
            System.out.println("국적 : " + u.getNation());
            System.out.println("상태 : " + u.getStatus());
            System.out.println("등록 날짜 : " + u.getRegisteredAt());
            System.out.println("학교 : " + u.getUniversity());

            u.getItemList().forEach(item -> {
                System.out.println("-----------------------------유저 상품 정보-----------------------------");
                System.out.println("카테고리 : " + item.getCategory().getType());
                System.out.println("상태 : " + item.getStatus());
                System.out.println("설명 : " + item.getContent());
                System.out.println("이름 : " + item.getName());
                System.out.println("제목 : " + item.getTitle());
                System.out.println("가격 : " + item.getPrice());
                System.out.println("등록 날짜 : " + item.getRegisteredAt());
    });

            System.out.println("-----------------------------유저 구매 정보-----------------------------");
            u.getOrderDetailList().forEach(order ->{
                System.out.println("구매 상태 : " + order.getStatus());
                System.out.println("구매 아이템 이름 : " + order.getItem().getName());
                System.out.println("구매 아이템 판매자 : " + order.getItem().getUser().getAccount());
            });

            System.out.println("-----------------------------유저 찜 정보-----------------------------");
            u.getDibList().forEach(dib->{
                System.out.println("찜한 아이템 이름 : " + dib.getItem().getName());
            });
        });

        Assertions.assertNotNull(user);
    }
}
