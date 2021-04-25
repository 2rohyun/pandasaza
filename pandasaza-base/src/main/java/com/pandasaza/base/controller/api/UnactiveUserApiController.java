package com.pandasaza.base.controller.api;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.UnactiveUserApiRequest;
import com.pandasaza.base.model.network.response.UnactiveUserApiResponse;
import com.pandasaza.base.service.UnactiveUserApiLogicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/unactive_user")
@RequiredArgsConstructor
public class UnactiveUserApiController implements CrudInterface<UnactiveUserApiRequest, UnactiveUserApiResponse> {

    private final UnactiveUserApiLogicService unactiveUserApiLogicService;

    @Override
    public Header<UnactiveUserApiResponse> create(Header<UnactiveUserApiRequest> request) { return null; }

    @Override
    @GetMapping("{id}")
    public Header<UnactiveUserApiResponse> read(@PathVariable Long id) {
        log.info("read id : {}", id);
        return unactiveUserApiLogicService.read(id);
    }

    @Override
    public Header<UnactiveUserApiResponse> update(Header<UnactiveUserApiRequest> request) {
        return null;
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) { return unactiveUserApiLogicService.delete(id); }
}
