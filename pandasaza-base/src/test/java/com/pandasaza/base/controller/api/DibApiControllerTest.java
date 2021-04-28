package com.pandasaza.base.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandasaza.base.model.entity.Dib;
import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.repository.DibRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
class DibApiControllerTest {

    @Autowired
    DibApiController dibApiController;

    @Autowired
    DibRepository dibRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(dibApiController)
                .alwaysDo(print())
                .build();
    }

    @Test
    void create() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/dib")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"data\" : {\n" +
                                "        \"userUserId\" : 1,\n" +
                                "        \"itemItemId\" : 3\n" +
                                "    }\n" +
                                "}"))
                .andExpect(status().isOk());
        Dib result =  dibRepository.findAll(Sort.by(Sort.Direction.DESC, "dibId")).get(0);

        assertAll(
                () -> assertThat(result.getUser().getUserId()).isEqualTo(1),
                () -> assertThat(result.getItem().getItemId()).isEqualTo(3)
        );
    }

    @Test
    void read() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/dib/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userUserId").value(1))
                .andExpect(jsonPath("$.data.itemItemId").value(3)
                );
    }

}