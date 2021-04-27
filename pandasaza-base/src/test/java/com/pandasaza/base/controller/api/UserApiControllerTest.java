package com.pandasaza.base.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
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


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Slf4j
class UserApiControllerTest {

    @Autowired
    private UserApiController userApiController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(userApiController)
                //.alwaysdo(print()) // anddo 생략가능
                .build();
    }


    @Test
    void create() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"data\": {\n" +
                                "        \"account\" : \"이도팔\",\n" +
                                "        \"password\" : \"password\",\n" +
                                "        \"email\" : \"anan@gmail.com\",\n" +
                                "        \"phoneNumber\" : \"010-1111-2222\",\n" +
                                "        \"nation\" : \"korea\",\n" +
                                "        \"university\" : \"dongguk\",\n" +
                                "        \"profileIcon\" : \"gggg\",\n" +
                                "        \"authMethods\":[\n" +
                                "            \"학생증 인증\", \"여권 인증\"\n" +
                                "        ],\n" +
                                "        \"authHistory\":[\n" +
                                "            \"학생증 인증\", \"여권 인증\"\n" +
                                "        ]\n" +
                                "    }\n" +
                                "}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        User result = userRepository.findAll(Sort.by(Sort.Direction.DESC,"userId")).get(0);

        assertAll(
                () -> assertThat(result.getAccount()).isEqualTo("이도팔"),
                () -> assertThat(result.getPassword()).isEqualTo("password"),
                () -> assertThat(result.getEmail()).isEqualTo("anan@gmail.com"),
                () -> assertThat(result.getPhoneNumber()).isEqualTo("010-1111-2222"),
                () -> assertThat(result.getNation()).isEqualTo("korea"),
                () -> assertThat(result.getUniversity()).isEqualTo("dongguk"),
                () -> assertThat(result.getProfileIcon()).isEqualTo("gggg"));
    }

    @Test
    @Transactional
    void read() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/5"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.account").value("13"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("aaa@naver.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.phoneNumber").value("01011111111"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.nation").value("korea"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.university").value("donnguk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.authMethods.[0]").value("학생증 인증"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.authMethods.[1]").value("여권 인증"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.authHistory.[0]").value("학생증 인증"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.authHistory.[1]").value("여권 인증"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.profileIcon").value("gggg")
                );
    }


    @Test
    @Transactional
    void update() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"data\": {\n" +
                                "        \"userId\" : 2,\n" +
                                "        \"account\" : \"이도칠\",\n" +
                                "        \"password\" : \"1123\",\n" +
                                "        \"email\" : \"aaa@naver.com\",\n" +
                                "        \"phoneNumber\" : \"01011111111\",\n" +
                                "        \"nation\" : \"korea\",\n" +
                                "        \"university\" : \"dongguk\",\n" +
                                "        \"profileIcon\" : \"gggg\",\n" +
                                "        \"authMethods\":[\n" +
                                "            \"학생증 인증\", \"여권 인증\"\n" +
                                "        ],\n" +
                                "        \"authHistory\":[\n" +
                                "            \"학생증 인증\", \"여권 인증\"\n" +
                                "        ]\n" +
                                "    }\n" +
                                "}"))

                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        User result = userRepository.findByUserId(2L);

        assertAll(
                () -> assertThat(result.getAccount()).isEqualTo("이도칠"),
                () -> assertThat(result.getEmail()).isEqualTo("aaa@naver.com"),
                () -> assertThat(result.getPhoneNumber()).isEqualTo("01011111111"),
                () -> assertThat(result.getNation()).isEqualTo("korea"),
                () -> assertThat(result.getUniversity()).isEqualTo("dongguk"),
                () -> assertThat(result.getProfileIcon()).isEqualTo("gggg"));

    }


    @Test
    @Transactional
    void delete() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/user/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(!userRepository.findById(1L).isPresent());
        //assertThat(userRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));
    }

    private String toJsonString(User user) throws JsonProcessingException {
        return objectMapper.writeValueAsString(user);
    }

    @Test
    void read_user_data() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/data/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("aaa@naver.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.phoneNumber").value("01011111111"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.account").value("이도칠"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.university").value("donnguk")
                );
    }

    @Test
    @Transactional
    void read_user_profile() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/profile/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.account").value("이도칠"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.nation").value("korea"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.score").value("NaN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.sellItems.[0]").value(16L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.university").value("donnguk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.authMethods.[0]").value("학생증 인증"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.authMethods.[1]").value("여권 인증"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.authHistory.[0]").value("학생증 인증"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.authHistory.[1]").value("여권 인증"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.profileIcon").value("gggg")
                );
    }
}