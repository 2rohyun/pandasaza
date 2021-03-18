package com.pandasaza.base.controller.api;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.UserApiRequest;
import com.pandasaza.base.model.network.response.UserApiResponse;
import com.pandasaza.base.service.UserApiLogicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController implements CrudInterface<UserApiRequest, UserApiResponse> {

    private final UserApiLogicService userApiLogicService;

    @Override
    @PostMapping("")
    public Header<UserApiResponse> create(@RequestBody Header<UserApiRequest> userApiRequest) {
        log.info("{}",userApiRequest);
        return userApiLogicService.create(userApiRequest);
    }

    @Override
    @GetMapping("{id}")
    public Header<UserApiResponse> read(@PathVariable Long id) {
        log.info("read id : {}", id);
        return userApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<UserApiResponse> update(@RequestBody Header<UserApiRequest> userApiRequest) {
        return userApiLogicService.update(userApiRequest);
    }

    @Override
    @DeleteMapping("{id}")
    public Header<UserApiResponse> delete(@PathVariable Long id) {
        return userApiLogicService.delete(id);
    }

    /*
    @Override
    public Header<UserApiResponse> read(Long id1, Long id2){ return null; }

    @Override
    public Header delete(Long id1, Long id2) { return null; }

     */
}