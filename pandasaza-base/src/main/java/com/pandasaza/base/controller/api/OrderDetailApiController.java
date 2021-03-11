package com.pandasaza.base.controller.api;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.OrderDetailApiRequest;
import com.pandasaza.base.model.network.response.ItemApiResponse;
import com.pandasaza.base.model.network.response.OrderDetailApiResponse;
import com.pandasaza.base.service.OrderDetailApiLogicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderDetailApiController implements CrudInterface<OrderDetailApiRequest, OrderDetailApiResponse> {

    private final OrderDetailApiLogicService orderDetailApiLogicService;

    @Override
    @PostMapping("")
    public Header<OrderDetailApiResponse> create(@RequestBody Header<OrderDetailApiRequest> request) {
        log.info("{}",request);
        return orderDetailApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<OrderDetailApiResponse> read(@PathVariable Long id) {
        log.info("read id : {}", id);
        return orderDetailApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<OrderDetailApiResponse> update(@RequestBody Header<OrderDetailApiRequest> request) {
        return orderDetailApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) { return orderDetailApiLogicService.delete(id); }

    /*
    @Override
    public Header<OrderDetailApiResponse> read(Long id1, Long id2){ return null; }

    @Override
    public Header delete(Long id1, Long id2) { return null; }

     */
}
