package com.pandasaza.base.repository;

import com.pandasaza.base.BaseApplicationTests;
import com.pandasaza.base.model.entity.Admin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class AdminRepositoryTest extends BaseApplicationTests {

    @Autowired
    private AdminRepository adminRepository;


    @Test
    public void create(){
        Admin admin = new Admin();

        admin.setAdminId(1L);
        admin.setLastLoginAt(LocalDateTime.now());
        admin.setLoginFailCount(0);
        admin.setAccount("AdminUser01");
        admin.setPassword("AdminUser01");
        admin.setStatus("REGISTERED");
        admin.setRole("AdminUser01");
        admin.setPasswordUpdatedAt(LocalDateTime.now());
        admin.setRegisteredAt(LocalDateTime.now());

        Admin newAdmin = adminRepository.save(admin);
        Assertions.assertNotNull(newAdmin);

        newAdmin.setAccount("CHANGE");
        adminRepository.save(newAdmin);
    }

    @Test
    public void read(){

        Optional<Admin> admin = adminRepository.findById(1L);

        admin.ifPresent(a->{
           System.out.println(a.getAccount());
            System.out.println(a.getPassword());
            System.out.println(a.getRegisteredAt());
            System.out.println(a.getStatus());
            System.out.println(a.getLastLoginAt());
            System.out.println(a.getLoginFailCount());
            System.out.println(a.getPasswordUpdatedAt());
            System.out.println(a.getRole());
        });

    }

    @Test
    public void update(){

    }

    @Test
    public void delete(){

    }
}
