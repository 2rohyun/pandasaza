package com.pandasaza.base.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.repository.ItemRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ItemApiControllerTest {

    @Autowired
    private ItemApiController itemApiController;

    @Autowired
    private ItemRepository itemRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(itemApiController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @Transactional
    void create() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/item")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "    \"data\": {\n" +
                            "        \"content\" : \"하하하하\",\n" +
                            "        \"name\" : \"채현욱\",\n" +
                            "        \"categoryCategoryId\" : 1,\n" +
                            "        \"userUserId\" : 1,\n" +
                            "        \"title\" : \"이거모임 ㅋㅋ\",\n" +
                            "        \"price\" : 100000,\n" +
                            "        \"itemImagesUrl\" : [\n" +
                            "            \"aaaa\", \"bbbb\"\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}"))
                    .andExpect(status().isOk());
        Item result =  itemRepository.findAll(Sort.by(Sort.Direction.DESC, "itemId")).get(0);

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo("REGISTERED"),
                () -> assertThat(result.getContent()).isEqualTo("하하하하"),
                () -> assertThat(result.getCategory().getCategoryId()).isEqualTo(1L),
                () -> assertThat(result.getUser().getUserId()).isEqualTo(1L),
                () -> assertThat(result.getTitle()).isEqualTo("이거모임 ㅋㅋ"),
                () -> assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(100000))
        );
    }

    @Test
    @Transactional
    void read() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/item/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("채현욱"))
                .andExpect(jsonPath("$.data.status").value("REGISTERED"))
                .andExpect(jsonPath("$.data.title").value("이거모임 ㅋㅋ"))
                .andExpect(jsonPath("$.data.price").value(BigDecimal.valueOf(100000.0)))
                //.andExpect(jsonPath("$.data.cntLike").value(0))
                .andExpect(jsonPath("$.data.cntShow").value(1))
                .andExpect(jsonPath("$.data.categoryId").value(1)
                //.andExpect(jsonPath("$.data.userUserId").value(1)
                );
    }

    @Test
    @Transactional
    void update() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"data\": {\n" +
                                "        \"itemId\" : 6,\n" +
                                "        \"content\" : \"ㅎㅎㅎ\",\n" +
                                "        \"name\" : \"채현욱\",\n" +
                                "        \"categoryCategoryId\" : 1,\n" +
                                "        \"userUserId\" : 1,\n" +
                                "        \"title\" : \"이거모임 ㅋㅋ\",\n" +
                                "        \"price\" : 100000,\n" +
                                "        \"itemImagesUrl\" : [\n" +
                                "            \"aaaa\", \"bbbb\"\n" +
                                "        ]\n" +
                                "    }\n" +
                                "}"))

                .andExpect(status().isOk());

        Item result = itemRepository.findByItemId(6L);

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo("REGISTERED"),
                () -> assertThat(result.getContent()).isEqualTo("ㅎㅎㅎㅎ"),
                () -> assertThat(result.getCategory().getCategoryId()).isEqualTo(1L),
                () -> assertThat(result.getUser().getUserId()).isEqualTo(1L),
                () -> assertThat(result.getTitle()).isEqualTo("이거모임 ㅋㅋ"),
                () -> assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(100000))
                );

    }

    @Test
    @Transactional
    void delete() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/item/2"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(!itemRepository.findById(2L).isPresent());
        //assertThat(userRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));
    }





}