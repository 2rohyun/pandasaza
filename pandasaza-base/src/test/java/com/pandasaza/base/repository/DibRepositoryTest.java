package com.pandasaza.base.repository;

import com.pandasaza.base.BaseApplicationTests;
import com.pandasaza.base.model.entity.Dib;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

public class DibRepositoryTest extends BaseApplicationTests {

    @Autowired
    private DibRepository dibRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create(){
        Dib dib = new Dib();

        dib.setUser(userRepository.findByUserId(1L));
        dib.setItem(itemRepository.findByItemId(1L));

        Dib newDib = dibRepository.save(dib);
        Assertions.assertNotNull(newDib);
    }

    @Test
    @Transactional
    public void read(){
        Optional<Dib> dib = dibRepository.findById(1L);

        dib.ifPresent(d->{
            System.out.println("찜한 아이템 이름 : " + d.getItem().getName());
            //System.out.println("찜한 유저 계정 : " + d.getUser().getAccount());
        });
    }
}
