package com.pandasaza.base.controller.api;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.ThumbnailItemApiRequest;
import com.pandasaza.base.model.network.response.ThumbnailItemApiResponse;
import com.pandasaza.base.service.ThumbnailItemApiLogicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/thumbnail_item")
@RequiredArgsConstructor
public class ThumbnailItemApiController implements CrudInterface<ThumbnailItemApiRequest, ThumbnailItemApiResponse> {

    private final ThumbnailItemApiLogicService thumbnailItemApiLogicService;

    @GetMapping("")
    public Header<List<ThumbnailItemApiResponse>> search(@PageableDefault(sort = "tiId", direction = Sort.Direction.ASC,size = 20)Pageable pageable){
        log.info("{}",pageable);
        return thumbnailItemApiLogicService.search(pageable);
    }

    @Override
    public Header<ThumbnailItemApiResponse> create(Header<ThumbnailItemApiRequest> request) {
        return null;
    }

    @Override
    @GetMapping("{id}")
    public Header<ThumbnailItemApiResponse> read(@PathVariable Long id) {
        log.info("read id : {}", id);
        return thumbnailItemApiLogicService.read(id);
    }

    @Override
    public Header<ThumbnailItemApiResponse> update(Header<ThumbnailItemApiRequest> request) {
        return null;
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) { return thumbnailItemApiLogicService.delete(id); }
}
