package com.pandasaza.base.service;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.model.enumclass.UserStatus;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.UserApiRequest;
import com.pandasaza.base.model.network.response.*;
import com.pandasaza.base.model.service.UnactiveUserService;
import com.pandasaza.base.model.service.UserItemService;
import com.pandasaza.base.model.service.UserReviewService;
import com.pandasaza.base.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class
UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {

    private final UserRepository userRepository;

    private final UnactiveUserService unactiveUserService;

    private final UserReviewService userReviewService;

    private final UserItemService userItemService;

    @Override
    // login 서버에서 handling
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {
        // 1. request data
        UserApiRequest userApiRequest = request.getData();

        // 2. User 생성
        User user = User.builder()
                .email(userApiRequest.getEmail())
                .nationality(userApiRequest.getNationality())
                .university(userApiRequest.getUniversity())
                .authHistory(userApiRequest.getAuthHistory().get(0))
                .authMethods(userApiRequest.getAuthMethods().get(0))
                .build();

        User newUser = userRepository.save(user);

        //3. 생성된 데이터 --> userApiResponse return
        return response(newUser);

    }

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

        //String encodePassword = passwordEncoder.encode(userApiRequest.getPassword());

        return optional.map(user->{
            //3. update
            user
                    .setEmail(userApiRequest.getEmail())
                    .setUniversity(userApiRequest.getUniversity())
                    .setNationality(userApiRequest.getNationality())
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
                    .setLastLoginAt(userApiRequest.getLastLoginAt());
            log.error("gg: {}",user);
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
                .userId(user.getUserId())
                .status(user.getStatus())
                .email(user.getEmail())
                .registeredAt(user.getRegisteredAt())
                .lastLoginAt(user.getLastLoginAt())
                .nation(user.getNationality())
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
                .nation(user.getNationality())
                .university(user.getUniversity())
                .score(userReviewService.getAvgScore(user))
                .build();

        return Header.OK(sellerApiResponse);
    }

    public Header<List<SellerRefApiResponse>> sellerRefResponse(User user, Long id){
        List<SellerRefApiResponse> sellerRefApiResponses = new ArrayList<>(Collections.emptyList());

        if(user.getItemList().size() >= 5) {
            user.getItemList().subList(0, 4).forEach(item -> {

                if(!item.getItemId().equals(id)) {
                    List<String> itemImagesURLList = Stream.of(
                            item.getItemImagesUrl()
                                    .split(",", -1))
                            .collect(Collectors.toList());

                    SellerRefApiResponse sellerRefApiResponse = SellerRefApiResponse.builder()
                            .itemId(item.getItemId())
                            .price(item.getPrice())
                            .title(item.getTitle())
                            .itemImageUrl(itemImagesURLList.get(0))
                            .build();
                    sellerRefApiResponses.add(sellerRefApiResponse);
                }
            });
        } else {
            user.getItemList().forEach(item -> {
                if(!item.getItemId().equals(id)) {
                    List<String> itemImagesURLList = Stream.of(
                            item.getItemImagesUrl()
                                    .split(",", -1))
                            .collect(Collectors.toList());

                    SellerRefApiResponse sellerRefApiResponse = SellerRefApiResponse.builder()
                            .itemId(item.getItemId())
                            .price(item.getPrice())
                            .title(item.getTitle())
                            .itemImageUrl(itemImagesURLList.get(0))
                            .build();
                    sellerRefApiResponses.add(sellerRefApiResponse);
                }
            });
        }

        return Header.OK(sellerRefApiResponses);

    }








/**
    public Header<UserProfileApiResponse> userProfileResponse(User user){

        List<String> authMethodsList = Stream.of(
                user.getAuthMethods()
                        .split(",",-1))
                .collect(Collectors.toList());

        List<String> authHistoryList = Stream.of(
                user.getAuthHistory()
                        .split(",",-1))
                .collect(Collectors.toList());

        UserProfileApiResponse userProfileApiResponse = UserProfileApiResponse.builder()
                .userId(user.getUserId())
                .lastLoginAt(user.getLastLoginAt()) // TODO ( 로그인 시 last_login_at 업데이트 )
                .nation(user.getNationality())
                .score(userReviewService.getAvgScore(user))
                .university(user.getUniversity())
                .authMethods(authMethodsList) // TODO ( 동민이 어드민 페이지 완성되면 디테일하게 구현 )
                .authHistory(authHistoryList)
                .sellItems(userItemService.getUserSellItem(user.getUserId()))
                .scoreHistory(userReviewService.getScoreHistory(user))
                .reviewHistory(userReviewService.getReviewHistory(user))
                .build();

                return Header.OK(userProfileApiResponse);
    }


    public Header<UserDataApiResponse> userDataResponse(User user) {
        UserDataApiResponse userDataApiResponse = UserDataApiResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .university(user.getUniversity())
                .build();

        return Header.OK(userDataApiResponse);
    }

    public Header<UserDataApiResponse> readUserData(Long id) {
        //id -> repository 에서 getOne, getById 통해 가져온다.
        Optional<User> optional = userRepository.findById(id);

        //user -> userApiResponse return
        return optional
                .map(user -> userDataResponse(user)) //map : 형 변환 리턴
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }

    public Header<UserProfileApiResponse> readUserProfile(Long id) {
        Optional<User> optional = userRepository.findById(id);

        //user -> userApiResponse return
        return optional
                .map(user -> userProfileResponse(user)) //map : 형 변환 리턴
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }
 **/
}
