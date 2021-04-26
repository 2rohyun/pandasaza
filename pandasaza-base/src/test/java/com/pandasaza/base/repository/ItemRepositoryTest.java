package com.pandasaza.base.repository;

import com.pandasaza.base.BaseApplicationTests;
import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ItemRepositoryTest extends BaseApplicationTests {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create() {

        Item item = new Item();
        item.setName("아이패드")
                .setTitle("아이패드 팝니다!@@")
                .setContent("-")
                .setPrice(BigDecimal.valueOf(1400000))
                .setStatus("거래중")
                .setRegisteredAt(LocalDateTime.now())
                .setUser(userRepository.findByUserId(2L))
                .setCategory(categoryRepository.findByCategoryId(1L))
                .setCntLike(1)
                .setCntShow(1)
                .setItemImagesUrl("fdsfsd,fsdfds,fssd");

        Item newItem = itemRepository.save(item);
        Assertions.assertNotNull(newItem);

    }

    @Test
    @Transactional
    public void read(){
        Item item = itemRepository.findByItemId(1L);

        System.out.println("-----------------------------아이템 기본 정보-----------------------------");
        System.out.println("아이템 이름 : " + item.getName());
        System.out.println("아이템 글 제목 : " + item.getTitle());
        System.out.println("아이템 글 내용 : " + item.getContent());
        System.out.println("아이템 상태( 판매중, 거래중 등 ) : " + item.getStatus());
        System.out.println("아이템 가격 : " + item.getPrice());
        System.out.println("아이템 등록일 : " + item.getRegisteredAt());
        System.out.println("-----------------------------아이템 판매자 정보-----------------------------");
        System.out.println("아이템 판매자 계정 : " + item.getUser().getAccount());
        System.out.println("-----------------------------아이템 카테고리 정보-----------------------------");
        System.out.println("아이템 카테고리 타입 : " + item.getCategory().getType());
        System.out.println("-----------------------------아이템 오더 정보-----------------------------");
        item.getOrderDetailList().forEach(o->{
            System.out.println("아이템 주문 상태 : " + o.getStatus());
            System.out.println("아이템 주문자 계정 : " + o.getUser().getAccount());
        });



    }
}
