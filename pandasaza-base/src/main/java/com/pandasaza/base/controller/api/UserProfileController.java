package com.pandasaza.base.controller.api;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.UserProfileApiRequest;
import com.pandasaza.base.model.network.response.UserProfileApiResponse;
import com.pandasaza.base.service.UserApiLogicService;
import com.pandasaza.base.service.UserProfileApiLogicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user_profile")
@RequiredArgsConstructor
public class UserProfileController implements CrudInterface<UserProfileApiRequest, UserProfileApiResponse> {

    private final UserProfileApiLogicService userProfileApiLogicService;

    @Override
    public Header<UserProfileApiResponse> create(Header<UserProfileApiRequest> userProfileApiRequest) {
        if (userProfileApiRequest == null) {
            log.error("received Request : {}", userProfileApiRequest);
        }
        // 에러가 안일어난경우
        else {
            log.info("succeful Request : {}", userProfileApiRequest);
        }
        return userProfileApiLogicService.create(userProfileApiRequest);
    }

    @Override
    public Header<UserProfileApiResponse> read(Long id) {
        if (id == null) {
            log.error("received Request : {}", id);
        }
        // 에러가 안일어난경우
        else {
            log.info("succeful Request : {}", id);
        }
        return userProfileApiLogicService.read(id);
    }

    @Override
    public Header<UserProfileApiResponse> update(Header<UserProfileApiRequest> userProfileApiRequest) {
        if (userProfileApiRequest == null) {
            log.error("received Request : {}", userProfileApiRequest);
        }
        // 에러가 안일어난경우
        else {
            log.info("succeful Request : {}", userProfileApiRequest);
        }
        return userProfileApiLogicService.update(userProfileApiRequest);
    }

    @Override
    public Header delete(Long id) {
        if (id == null) {
            log.error("received Request : {}", id);
        }
        // 에러가 안일어난경우
        else {
            log.info("succeful Request : {}", id);
        }
        return userProfileApiLogicService.delete(id);
    }
}
