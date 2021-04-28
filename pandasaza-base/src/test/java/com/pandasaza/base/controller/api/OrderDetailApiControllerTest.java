package com.pandasaza.base.controller.api;


import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.OrderDetail;
import com.pandasaza.base.repository.OrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OrderDetailApiControllerTest {

    @Autowired
    private OrderDetailApiController orderDetailApiController;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(orderDetailApiController)
                //.alwaysdo(print()) // anddo 생략가능
                .build();
    }

    @Test
    @Transactional
    void create() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"data\" : {\n" +
                                "        \"userUserId\" : 1,\n" +
                                "        \"itemItemId\" : 3,\n" +
                                "        \"status\" : \"REGISTERED\"\n" +
                                "    }\n" +
                                "}"))
                .andExpect(status().isOk());
        OrderDetail result = orderDetailRepository.findAll(Sort.by(Sort.Direction.DESC, "orderId")).get(0);

        assertAll(
                () -> assertThat(result.getUser().getUserId()).isEqualTo(1),
                () -> assertThat(result.getItem().getItemId()).isEqualTo(3),
                () -> assertThat(result.getStatus()).isEqualTo("REGISTERED")
        );
    }

    @Test
    @Transactional
    void read() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userUserId").value(1))
                .andExpect(jsonPath("$.data.status").value("REGISTERED"))
                .andExpect(jsonPath("$.data.itemItemId").value(3)
                );
    }

    @Test
    @Transactional
    void update() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"data\" : {\n" +
                                "        \"orderId\" : 1,\n" +
                                "        \"status\" : \"UNREGISTERED\"\n" +
                                "    }\n" +
                                "}"))

                .andExpect(status().isOk());

        OrderDetail result = orderDetailRepository.findByOrderId(1L);

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo("UNREGISTERED")
        );

    }

    @Test
    @Transactional
    void delete() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/order/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(!orderDetailRepository.findById(1L).isPresent());
    }



}