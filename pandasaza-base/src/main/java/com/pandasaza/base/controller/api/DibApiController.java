package com.pandasaza.base.controller.api;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.DIbApiRequest;
import com.pandasaza.base.model.network.response.DibApiResponse;
import com.pandasaza.base.service.DibApiLogicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/dib")
@RequiredArgsConstructor
public class DibApiController implements CrudInterface<DIbApiRequest, DibApiResponse> {

    private final DibApiLogicService dibApiLogicService;

    @Override
    @PostMapping("")
    public Header<DibApiResponse> create(@RequestBody Header<DIbApiRequest> request) {
        log.info("{}",request);
        return dibApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}") // todo : 사용자 아이디 and 아이템 아이디 )
    public Header<DibApiResponse> read(@PathVariable(name = "id") Long id) {
        log.info("read id : {}", id);
        return dibApiLogicService.read(id);
    }

    @Override
    public Header<DibApiResponse> update(Header<DIbApiRequest> request) {
        return null;
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) { return null; }

    @DeleteMapping("{item_id}/{user_id}")
    public Header delete(@PathVariable(name = "item_id") Long itemId,
                         @PathVariable(name = "user_id") Long userId) {
        return dibApiLogicService.deleteByItemIdAndUserId(itemId, userId);
    }

    /*

    @Override
    public Header<DibApiResponse> read(Long id){ return null; }

    @Override
    public Header delete(Long id) { return null; }

     */

}
