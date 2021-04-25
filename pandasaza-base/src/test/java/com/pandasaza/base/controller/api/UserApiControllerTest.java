package com.pandasaza.base.controller.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.model.enumclass.UserStatus;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.UserApiRequest;
import com.pandasaza.base.repository.UserRepository;
import com.pandasaza.base.repository.UserRepositoryTest;
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


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

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

        /*
        // UserApiRequest 생성
        UserApiRequest userApiRequest = UserApiRequest.builder()
                .account("이도팔")
                .password("password")
                .lastLoginAt(LocalDateTime.now())
                .status(UserStatus.REGISTERED)
                .registeredAt(LocalDateTime.now())
                .phoneNumber("010-1111-2222")
                .email("anan@gmail.com")
                //.authHistory(new ArrayList<>(Arrays.asList("히스토리")))
                //.authMethods(new ArrayList<>(Arrays.asList("메소드")))
                .nation("Korea")
                .university("dongguk")
                .build();
        // User 객체 생성
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .lastLoginAt(userApiRequest.getLastLoginAt())
                .status(userApiRequest.getStatus())
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .authHistory("-")
                .authMethods("-")
                .nation(userApiRequest.getNation())
                .university(userApiRequest.getUniversity())
                .build();

         */

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
                MockMvcRequestBuilders.get("/api/user/2"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.account").value("이도팔"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.password").value("password"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("anan@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.phoneNumber").value("010-1111-2222"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.nation").value("korea"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.university").value("dongguk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.profileIcon").value("gggg")
                );
    }

    /*
    @Test
    void modifyPerson() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Person result = personRepository.findById(3L).get();

        assertAll(
                () -> assertThat(result.getName()).isEqualTo("john"),
                () -> assertThat(result.getAge()).isEqualTo(10),
                () -> assertThat(result.getBloodType()).isEqualTo("AB")
        );

    }
     */

    @Test
    @Transactional
    void deletePerson() throws Exception{
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
}