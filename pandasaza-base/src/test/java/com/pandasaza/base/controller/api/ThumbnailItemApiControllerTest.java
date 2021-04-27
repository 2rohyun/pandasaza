package com.pandasaza.base.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandasaza.base.model.network.response.ThumbnailItemApiResponse;
import com.pandasaza.base.repository.DibRepository;
import com.pandasaza.base.repository.ThumbnailItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@Transactional
class ThumbnailItemApiControllerTest {

    @Autowired
    ThumbnailItemApiController thumbnailItemApiController;

    @Autowired
    ThumbnailItemRepository thumbnailItemRepository;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .alwaysDo(print())
                .build();
    }

    @Test
    void search() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/thumbnail-item")
                    .param("page", "0")
                    .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pagination.total_pages").value(1))
                .andExpect(jsonPath("$.pagination.total_elements").value(6))
                .andExpect(jsonPath("$.pagination.current_page").value(0))
                .andExpect(jsonPath("$.pagination.current_elements").value(6))
                .andExpect(jsonPath("$.data.[0].name").value("채현욱"))
                .andExpect(jsonPath("$.data.[1].name").value("채현욱"))
                .andExpect(jsonPath("$.data.[2].name").value("채현욱"))
                .andExpect(jsonPath("$.data.[3].name").value("채현욱"))
                .andExpect(jsonPath("$.data.[4].name").value("채현욱"))
                .andExpect(jsonPath("$.data.[5].name").value("채현욱")
                );
    }

    @Test
    @Transactional
    void read() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/thumbnail-item/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.cnt_like").value(0))
                .andExpect(jsonPath("$.data.cnt_show").value(1))
                .andExpect(jsonPath("$.data.name").value("채현욱"))
                .andExpect(jsonPath("$.data.price").value(BigDecimal.valueOf(100000.0))
                );
    }

    @Test
    @Transactional
    void delete() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/item/5"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(!thumbnailItemRepository.findById(5L).isPresent());
    }

}