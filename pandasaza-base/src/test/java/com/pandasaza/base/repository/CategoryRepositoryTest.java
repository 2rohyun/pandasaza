package com.pandasaza.base.repository;

import com.pandasaza.base.BaseApplicationTests;
import com.pandasaza.base.model.entity.Category;
import com.pandasaza.base.model.entity.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CategoryRepositoryTest extends BaseApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create(){
        Category category = new Category();
        category.setCategoryId(1L)
                .setGroupCode(100)
                .setType("전자기기");

        Category newCategory = categoryRepository.save(category);
        Assertions.assertNotNull(newCategory);
    }

    @Test
    @Transactional
    public void read(){
        Category category = categoryRepository.findByCategoryId(1L);

        System.out.println(category.getType());
        System.out.println(category.getGroupCode());
        category.getItemList().forEach(i->{
            System.out.println(i.getName());
            System.out.println(i.getPrice());
            System.out.println(i.getContent());
        });

    }
}
