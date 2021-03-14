package com.pandasaza.base.service;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.model.enumclass.UserRole;
import com.pandasaza.base.model.enumclass.UserStatus;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.UserApiRequest;
import com.pandasaza.base.model.network.response.SellerApiResponse;
import com.pandasaza.base.model.network.response.UserApiResponse;
import com.pandasaza.base.model.service.UnactiveUserService;
import com.pandasaza.base.model.service.UserReviewService;
import com.pandasaza.base.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {

    private final UserRepository userRepository;

    private final UnactiveUserService unactiveUserService;

    private final PasswordEncoder passwordEncoder;

    private final UserReviewService userReviewService;

    @Override
    // login 서버에서 handling
    public Header<UserApiResponse> create(Header<UserApiRequest> request) { return null; }

    @Override
    public Header<UserApiResponse> read(Long id) {
        //id -> repository 에서 getOne, getById 통해 가져온다.
        Optional<User> optional = userRepository.findById(id);

        //user -> userApiResponse return
        return optional
                .map(user -> response(user)) //map : 형 변환 리턴
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        //1. 데이터 가져오기
        UserApiRequest userApiRequest = request.getData();
        //2. id-> user data 찾기
        Optional<User> optional = userRepository.findById(userApiRequest.getUserId());
        unactiveUserService.delete(userApiRequest.getUserId());

        String encodePassword = passwordEncoder.encode(userApiRequest.getPassword());

        return optional.map(user->{
            //3. update
            user.setAccount(userApiRequest.getAccount())
                    .setPassword(encodePassword)
                    .setEmail(userApiRequest.getEmail())
                    .setUniversity(userApiRequest.getUniversity())
                    .setNation(userApiRequest.getNation())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setAuthMethods(
                            userApiRequest.getAuthMethods()
                                    .stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.joining(","))
                    )
                    .setAuthHistory(
                            userApiRequest.getAuthHistory()
                                    .stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.joining(","))
                    )
                    .setStatus(userApiRequest.getStatus())
                    .setLastLoginAt(userApiRequest.getLastLoginAt())
                    .setProfileIcon(userApiRequest.getProfileIcon());

            return user;
        })
                .map(user->userRepository.save(user))
                .map(updateUser->response(updateUser))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        //1. id -> repository -> user
        Optional<User> optional = userRepository.findById(id);
        //2. repository -> delete
        return optional.map(user->{
            userRepository.delete(user);
            //3. response return
            return Header.OK();
        })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    public Header<UserApiResponse> response(User user){


        List<Long> itemIdList = new ArrayList<>();
        if(user.getItemList() != null){
            user.getItemList().forEach(item->
            {
                itemIdList.add(item.getItemId());
            });
        }

        List<String> authMethodsList = Stream.of(
                user.getAuthMethods()
                        .split(",",-1))
                .collect(Collectors.toList());

        List<String> authHistoryList = Stream.of(
                user.getAuthHistory()
                        .split(",",-1))
                .collect(Collectors.toList());

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .account(user.getAccount())
                .userId(user.getUserId())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .email(user.getEmail())
                .registeredAt(user.getRegisteredAt())
                .lastLoginAt(user.getLastLoginAt())
                .nation(user.getNation())
                .university(user.getUniversity())
                .profileIcon(user.getProfileIcon())
                .authMethods(authMethodsList)
                .authHistory(authHistoryList)
                .itemList(itemIdList)
                .score(userReviewService.getAvgScore(user))
                .build();

        return Header.OK(userApiResponse);
    }

    public Header<SellerApiResponse> sellerResponse(User user){
        SellerApiResponse sellerApiResponse = SellerApiResponse.builder()
                .userId(user.getUserId())
                .account(user.getAccount())
                .nation(user.getNation())
                .university(user.getUniversity())
                .profileIcon(user.getProfileIcon())
                .score(userReviewService.getAvgScore(user))
                .build();

        return Header.OK(sellerApiResponse);
    }

}
