package com.pandasaza.base.controller.api;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.AccountApiRequest;
import com.pandasaza.base.model.network.request.AccountApiResponse;
import com.pandasaza.base.service.AccountApiLogicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountApiController implements CrudInterface<AccountApiRequest, AccountApiResponse> {

    private final AccountApiLogicService accountApiLogicService;
    @Override
    public Header<AccountApiResponse> create(@RequestBody Header<AccountApiRequest> accountApiRequest) {
        if (accountApiRequest == null) {
            log.error("received Request : {}", accountApiRequest);
        }
        // 에러가 안일어난경우
        else {
            log.info("succeful Request : {}", accountApiRequest);
        }
        return accountApiLogicService.create(accountApiRequest);
    }

    @Override
    public Header<AccountApiResponse> read(@PathVariable Long id) {
        if (id == null) {
            log.error("received Request : {}", id);
        }
        // 에러가 안일어난경우
        else {
            log.info("succeful Request : {}", id);
        }
        return accountApiLogicService.read(id);
    }

    @Override
    public Header<AccountApiResponse> update(Header<AccountApiRequest> accountApiRequest) {
        if (accountApiRequest == null) {
            log.error("received Request : {}", accountApiRequest);
        }
        // 에러가 안일어난경우
        else {
            log.info("succeful Request : {}", accountApiRequest);
        }
        return accountApiLogicService.update(accountApiRequest);
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
        return accountApiLogicService.delete(id);
    }
}
