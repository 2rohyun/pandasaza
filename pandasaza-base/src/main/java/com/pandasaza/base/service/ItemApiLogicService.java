package com.pandasaza.base.service;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.entity.Category;
import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.ItemImage;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.ItemApiRequest;
import com.pandasaza.base.model.network.request.UserApiRequest;
import com.pandasaza.base.model.network.response.*;
import com.pandasaza.base.model.service.ThumbnailItemService;
import com.pandasaza.base.repository.*;
import com.pandasaza.base.utils.Uploader;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.CharacterData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemApiLogicService {

    private final ItemRepository itemRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final ThumbnailItemService thumbnailItemService;

    private final UserApiLogicService userApiLogicService;

    private final ThumbnailItemRepository thumbnailItemRepository;

    private final DibRepository dibRepository;

    private final ItemImageRepository itemImageRepository;

    public List<String> create(ItemApiRequest request, List<String> uploadUrlList) {

        ItemApiRequest itemApiRequest = request;


        Item item = Item.builder()
                .registeredAt(LocalDateTime.now())
                .status("REGISTERED")
                .content(itemApiRequest.getContent())
                .name(itemApiRequest.getName())
                //.category(categoryRepository.getOne(itemApiRequest.getCategoryCategoryId()))
                //.user(userRepository.getOne(itemApiRequest.getUserUserId()))
                .title(itemApiRequest.getTitle())
                .price(itemApiRequest.getPrice())
//                .itemImagesUrl(
//                        //List to String
//                        itemApiRequest.getItemImagesUrl()
//                                .stream()
//                                .map(String::valueOf)
//                                .collect(Collectors.joining(","))
//                )
                .cntLike(0)
                .cntShow(0)
                .build();
        Item newItem = itemRepository.save(item);

        ItemImage itemImage = ItemImage.builder()
                .imageUrl(
                        uploadUrlList.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")))
                .item(newItem)
                .build();

        itemImageRepository.save(itemImage);
        //thumbnailItemService.create(newItem.getItemId());
        return uploadUrlList;
    }

    public Header<ItemApiResponse> read(Long id) {
        Optional<Item> optional = itemRepository.findById(id);
        if (optional == null) {
            log.error("received Request : {}", optional);
        }
        // ????????? ??????????????????
        else {
            log.info("succeful Request : {}", optional);
        }

        //????????? ??????
        itemRepository.updateHitByItemId(id);
        thumbnailItemRepository.updateHitByItemId(id);

        return optional
                .map(item -> response(item))
                .orElseGet(
                        () -> Header.ERROR("????????? ??????")
                );
    }

    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
        ItemApiRequest itemApiRequest = request.getData();

        Optional<Item> optional = itemRepository.findById(itemApiRequest.getItemId());
        return optional.map(item -> {

            item.setStatus("REGISTERED")
                    .setPrice(itemApiRequest.getPrice())
                    .setContent(itemApiRequest.getContent())
                    .setTitle(itemApiRequest.getTitle())
                    .setName(itemApiRequest.getName())
//                    .setItemImagesUrl(
//                            //List to String
//                            itemApiRequest.getItemImagesUrl()
//                                    .stream()
//                                    .map(String::valueOf)
//                                    .collect(Collectors.joining(","))
//                    )
                    .setCategory(categoryRepository.findByCategoryId(itemApiRequest.getCategoryCategoryId()))
                    .setUser(userRepository.findByUserId(itemApiRequest.getUserUserId()))
                    .setCntLike(itemApiRequest.getCntLike())
                    .setCntShow(itemApiRequest.getCntShow());

            log.error("gg: {}", item);
            return item;
        })
                .map(item -> itemRepository.save(item))
                .map(updateItem -> response(updateItem))
                .orElseGet(() -> Header.ERROR("????????? ??????"));
    }

    public Header delete(Long id) {
        Optional<Item> optional = itemRepository.findById(id);

        return optional.map(item -> {
            thumbnailItemService.delete(item.getItemId());
            itemRepository.delete(item);
            return Header.OK();
        })
                .orElseGet(() -> Header.ERROR("????????? ??????"));
    }

    private Header<ItemApiResponse> response(Item item) {
        // String to List
//        List<String> itemImagesURLList = Stream.of(
//                item.getItemImagesUrl()
//                        .split(",", -1))
//                .collect(Collectors.toList());

        ItemApiResponse itemApiResponse = ItemApiResponse.builder()
                .itemId(item.getItemId())
                .categoryId(item.getCategory().getCategoryId())
                .registeredAt(item.getRegisteredAt())
                .status(item.getStatus())
                .content(item.getContent())
                .name(item.getName())
                .price(item.getPrice())
                .title(item.getTitle())
                //.itemImagesUrl(itemImagesURLList)
                //.cntLike(dibRepository.getLikeCount(item.getItemId()))
                .cntShow(item.getCntShow())
                .type(item.getCategory().getType())
                .build();
        //Header + data return
        return Header.OK(itemApiResponse);
    }
}

/**
    public Header<ItemUserInfoApiResponse> itemUserInfo(Long id) {

        //level1 : item
        Item item = itemRepository.getOne(id);
        itemRepository.updateHitByItemId(id);
        thumbnailItemRepository.updateHitByItemId(id);

        ItemApiResponse itemApiResponse = response(item).getData();

        //level2 : user
        User user = item.getUser();
        SellerApiResponse sellerApiResponse = userApiLogicService.sellerResponse(user).getData();

        List<Item> itemList = item.getUser().getItemList();

        List<SellerRefApiResponse> sellerRefApiResponse = userApiLogicService.sellerRefResponse(user,id).getData();
        List<ItemRefApiResponse> itemRefApiResponse = itemRefResponse(item, id).getData();

        itemApiResponse.setSellerApiResponse(sellerApiResponse);
        itemApiResponse.setSellerRefApiResponse(sellerRefApiResponse);
        itemApiResponse.setItemRefApiResponse(itemRefApiResponse);

        ItemUserInfoApiResponse itemUserInfoApiResponse = ItemUserInfoApiResponse.builder()
                .itemApiResponse(itemApiResponse)
                .build();

        return Header.OK(itemUserInfoApiResponse);
    }

    private Header<List<ItemRefApiResponse>> itemRefResponse(Item item, Long id) {

        List<ItemRefApiResponse> itemRefApiResponses = new ArrayList<>(Collections.emptyList());

        List<Item> categoryItem = itemRepository.findByCategoryId(item.getCategory().getCategoryId());

        categoryItem.forEach(eachItem->{
            if(!eachItem.getItemId().equals(id)) {
                List<String> itemImagesURLList = Stream.of(
                        eachItem.getItemImagesUrl()
                                .split(",", -1))
                        .collect(Collectors.toList());

                ItemRefApiResponse itemRefApiResponse = ItemRefApiResponse.builder()
                        .itemId(eachItem.getItemId())
                        .itemImageUrl(itemImagesURLList.get(0))
                        .price(eachItem.getPrice())
                        .title(eachItem.getTitle())
                        .build();

                itemRefApiResponses.add(itemRefApiResponse);
            }
        });
            return Header.OK(itemRefApiResponses);
        }
    }
**/