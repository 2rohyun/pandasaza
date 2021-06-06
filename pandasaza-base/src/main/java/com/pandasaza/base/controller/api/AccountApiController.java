package com.pandasaza.base.controller.api;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.AccountApiRequest;
import com.pandasaza.base.model.network.request.AccountApiResponse;
import com.pandasaza.base.service.AccountApiLogicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountApiController implements CrudInterface<AccountApiRequest, AccountApiResponse> {

    private final AccountApiLogicService accountApiLogicService;
    @Override
    @PostMapping("")
    public Header<AccountApiResponse> create(@Valid @RequestBody Header<AccountApiRequest> accountApiRequest) {
        if (accountApiRequest == null) {
            log.error("received Request : {}", accountApiRequest);
        }
        // 에러가 안일어난경우
        else {
            log.info("successful Request : {}", accountApiRequest);
        }
        return accountApiLogicService.create(accountApiRequest);
    }

    @Override
    @GetMapping("{id}")
    public Header<AccountApiResponse> read(@PathVariable Long id) {
        if (id == null) {
            log.error("received Request : {}", id);
        }
        // 에러가 안일어난경우
        else {
            log.info("successful Request : {}", id);
        }
        return accountApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<AccountApiResponse> update(Header<AccountApiRequest> accountApiRequest) {
        if (accountApiRequest == null) {
            log.error("received Request : {}", accountApiRequest);
        }
        // 에러가 안일어난경우
        else {
            log.info("successful Request : {}", accountApiRequest);
        }
        return accountApiLogicService.update(accountApiRequest);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(Long id) {
        if (id == null) {
            log.error("received Request : {}", id);
        }
        // 에러가 안일어난경우
        else {
            log.info("successful Request : {}", id);
        }
        return accountApiLogicService.delete(id);
    }
}
