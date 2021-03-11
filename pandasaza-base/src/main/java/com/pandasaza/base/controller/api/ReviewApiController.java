package com.pandasaza.base.controller.api;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.OrderDetailApiRequest;
import com.pandasaza.base.model.network.request.ReviewApiRequest;
import com.pandasaza.base.model.network.response.OrderDetailApiResponse;
import com.pandasaza.base.model.network.response.ReviewApiResponse;
import com.pandasaza.base.service.ReviewApiLogicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewApiController implements CrudInterface<ReviewApiRequest, ReviewApiResponse> {

    private final ReviewApiLogicService reviewApiLogicService;

    @Override
    @PostMapping("")
    public Header<ReviewApiResponse> create(@RequestBody Header<ReviewApiRequest> request) {
        log.info("{}",request);
        return reviewApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<ReviewApiResponse> read(@PathVariable Long id) {
        log.info("read id : {}", id);
        return reviewApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<ReviewApiResponse> update(@RequestBody Header<ReviewApiRequest> request) {
        return reviewApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) { return reviewApiLogicService.delete(id); }

    /*
    @Override
    public Header<ReviewApiResponse> read(Long id1, Long id2){ return null; }

    @Override
    public Header delete(Long id1, Long id2) { return null; }

     */
}
