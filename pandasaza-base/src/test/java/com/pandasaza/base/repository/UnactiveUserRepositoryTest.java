package com.pandasaza.base.repository;

import com.pandasaza.base.BaseApplicationTests;
import com.pandasaza.base.model.entity.UnactiveUser;
import com.pandasaza.base.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class UnactiveUserRepositoryTest extends BaseApplicationTests {

    @Autowired
    private UnactiveUserRepository unactiveUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        UnactiveUser unactiveUser = new UnactiveUser();

        List<User> user = userRepository.findAll();
        LocalDateTime today = LocalDateTime.now();

        user.forEach(u->{
            if(ChronoUnit.DAYS.between(u.getLastLoginAt(),today) > 100){
                unactiveUser.setUuId(u.getUserId());
                //unactiveUser.setAccount(u.getAccount());
                unactiveUser.setEmail(u.getEmail());
                //unactiveUser.setIsCertified(u.getIsCertified());
                //unactiveUser.setNation(u.getNation());
                //unactiveUser.setPassword(u.getPassword());
                unactiveUser.setLastLoginAt(u.getLastLoginAt());
                //unactiveUser.setPhoneNumber(u.getPhoneNumber());
                unactiveUser.setRegisteredAt(u.getRegisteredAt());
                unactiveUser.setStatus(u.getStatus());
                unactiveUser.setUniversity(u.getUniversity());
                unactiveUser.setUser(u);

                UnactiveUser newUnactiveUser = unactiveUserRepository.save(unactiveUser);
                Assertions.assertNotNull(newUnactiveUser);
            }

        });
    }

}
