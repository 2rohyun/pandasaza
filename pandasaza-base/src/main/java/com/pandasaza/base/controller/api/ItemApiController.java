package com.pandasaza.base.controller.api;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.ItemApiRequest;
import com.pandasaza.base.model.network.response.DibApiResponse;
import com.pandasaza.base.model.network.response.ItemApiResponse;
import com.pandasaza.base.model.network.response.ItemUserInfoApiResponse;
import com.pandasaza.base.service.ItemApiLogicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemApiController implements CrudInterface<ItemApiRequest, ItemApiResponse> {

    private final ItemApiLogicService itemApiLogicService;

    @GetMapping("{id}/item-user-info")
    public Header<ItemUserInfoApiResponse> itemUserInfo(@PathVariable Long id){
        return itemApiLogicService.itemUserInfo(id);
    }

    @Override
    @PostMapping("")
    public Header<ItemApiResponse> create(@RequestBody Header<ItemApiRequest> request) {
        if (request == null) {
            log.error("received Request : {}", request);
        }
        // 에러가 안일어난경우
        else {
            log.info("succeful Request : {}", request);
        }
        return itemApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<ItemApiResponse> read(@PathVariable Long id) {
        log.info("read id : {}", id);
        return itemApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<ItemApiResponse> update(@RequestBody Header<ItemApiRequest> request) {
        if (request == null) {
            log.error("received Request : {}", request);
        }
        // 에러가 안일어난경우
        else {
            log.info("succeful Request : {}", request);
        }
        return itemApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) { return itemApiLogicService.delete(id); }


    /*
    @Override
    public Header<ItemApiResponse> read(Long id1, Long id2){ return null; }

    @Override
    public Header delete(Long id1, Long id2) { return null; }

     */
}
