package com.pandasaza.base.controller.api;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.ItemApiRequest;
import com.pandasaza.base.model.network.response.DibApiResponse;
import com.pandasaza.base.model.network.response.ItemApiResponse;
import com.pandasaza.base.model.network.response.ItemUserInfoApiResponse;
import com.pandasaza.base.service.ItemApiLogicService;
import com.pandasaza.base.utils.Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemApiLogicService itemApiLogicService;

    private final Uploader uploader;

    @PostMapping("")
    public List<String> create(@RequestParam("image") List<MultipartFile> imageList,
                                          @ModelAttribute ItemApiRequest request) throws IOException {
        if (request == null) {
            throw new NullPointerException("request doesn't exist");
        }
        else {
            log.info("succeful Request : {}", request);
            List<String> fileUrlList = uploader.upload(imageList, "static");

            return itemApiLogicService.create(request, fileUrlList);
        }
    }

    @GetMapping("{id}")
    public Header<ItemApiResponse> read(@PathVariable Long id) {
        log.info("read id : {}", id);
        return itemApiLogicService.read(id);
    }

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

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return itemApiLogicService.delete(id);
    }

}

    /**
    @GetMapping("{id}/item-user-info")
    public Header<ItemUserInfoApiResponse> itemUserInfo(@PathVariable Long id){
        return itemApiLogicService.itemUserInfo(id);
    }
    **/


    /*
    @Override
    public Header<ItemApiResponse> read(Long id1, Long id2){ return null; }

    @Override
    public Header delete(Long id1, Long id2) { return null; }

     */

