package com.pandasaza.base.service;

import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.response.UserApiResponse;
import com.pandasaza.base.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserApiLogicServiceTest {

    @InjectMocks //테스트의 주체
    private UserApiLogicService userApiLogicService;

    @Mock   //Mock으로 된걸 만들어서 InjectMocks로된거에 주입 시켜 준다는 느낌
    private UserRepository userRepository;

    @Test
    void getPerson(){
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(User.builder()


                                //.nation("korea")
                                .university("dongguk")
                                //.profileIcon("gggg")
                                //.authMethods()
                                //.authHistory()
                                .build()));

        Header<UserApiResponse> userApiResponseHeader = userApiLogicService.read(1L);
        UserApiResponse data = userApiResponseHeader.getData();

        assertThat(data.getAccount()).isEqualTo("이도팔");

    }

}