package com.pandasaza.base.service;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.entity.Category;
import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.ItemApiRequest;
import com.pandasaza.base.model.network.request.UserApiRequest;
import com.pandasaza.base.model.network.response.ItemApiResponse;
import com.pandasaza.base.model.network.response.ItemUserInfoApiResponse;
import com.pandasaza.base.model.network.response.SellerApiResponse;
import com.pandasaza.base.model.network.response.UserApiResponse;
import com.pandasaza.base.model.service.ThumbnailItemService;
import com.pandasaza.base.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ItemApiLogicService implements CrudInterface<ItemApiRequest, ItemApiResponse> {

    private final ItemRepository itemRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final ThumbnailItemService thumbnailItemService;

    private final UserApiLogicService userApiLogicService;

    private final ThumbnailItemRepository thumbnailItemRepository;

    private final DibRepository dibRepository;

    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {
        ItemApiRequest itemApiRequest = request.getData();

        Item item = Item.builder()
                .registeredAt(LocalDateTime.now())
                .status(itemApiRequest.getStatus())
                .content(itemApiRequest.getContent())
                .name(itemApiRequest.getName())
                .category(categoryRepository.getOne(itemApiRequest.getCategoryCategoryId()))
                .user(userRepository.getOne(itemApiRequest.getUserUserId()))
                .title(itemApiRequest.getTitle())
                .price(itemApiRequest.getPrice())
                .itemImagesUrl(
                        //List to String
                        itemApiRequest.getItemImagesUrl()
                                .stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(","))
                )
                .cntLike(0)
                .cntShow(0)
                .build();

        Item newItem = itemRepository.save(item);
        thumbnailItemService.create(newItem.getItemId());

        return response(newItem);
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {
        Optional<Item> optional = itemRepository.findById(id);

        //조회수 증가
        itemRepository.updateHitByItemId(id);
        thumbnailItemRepository.updateHitByItemId(id);

        return optional
                .map(item -> response(item))
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
        ItemApiRequest itemApiRequest = request.getData();

        Optional<Item> optional = itemRepository.findById(itemApiRequest.getItemId());

        return optional.map(item->{

            item.setStatus(itemApiRequest.getStatus())
                    .setPrice(itemApiRequest.getPrice())
                    .setContent(itemApiRequest.getContent())
                    .setTitle(itemApiRequest.getTitle())
                    .setName(itemApiRequest.getName())
                    .setItemImagesUrl(
                            //List to String
                            itemApiRequest.getItemImagesUrl()
                                    .stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.joining(","))
                    )
                    .setCntLike(itemApiRequest.getCntLike())
                    .setCntShow(itemApiRequest.getCntShow());

            return item;
        })
                .map(item->itemRepository.save(item))
                .map(updateItem->response(updateItem))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<Item> optional = itemRepository.findById(id);

        return optional.map(item->{
            thumbnailItemService.delete(item.getItemId());
            itemRepository.delete(item);
            return Header.OK();
        })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private Header<ItemApiResponse> response(Item item){
        // String to List
        List<String> itemImagesURLList = Stream.of(
                item.getItemImagesUrl()
                        .split(",",-1))
                        .collect(Collectors.toList());

        ItemApiResponse itemApiResponse = ItemApiResponse.builder()
                .itemId(item.getItemId())
                .categoryCategoryId(item.getCategory().getCategoryId())
                .registeredAt(item.getRegisteredAt())
                .status(item.getStatus())
                .content(item.getContent())
                .name(item.getName())
                .price(item.getPrice())
                .title(item.getTitle())
                .itemImagesUrl(itemImagesURLList)
                .cntLike(dibRepository.getLikeCount(item.getItemId()))
                .cntShow(item.getCntShow())
                .type(item.getCategory().getType())
                .build();
        //Header + data return
        return Header.OK(itemApiResponse);
    }


    public Header<ItemUserInfoApiResponse> itemUserInfo(Long id) {

        //level1 : item
        Item item = itemRepository.getOne(id);
        itemRepository.updateHitByItemId(id);
        thumbnailItemRepository.updateHitByItemId(id);

        ItemApiResponse itemApiResponse = response(item).getData();

        //level2 : user
        User user = item.getUser();
        SellerApiResponse sellerApiResponse = userApiLogicService.sellerResponse(user).getData();

        itemApiResponse.setSellerApiResponse(sellerApiResponse);

        ItemUserInfoApiResponse itemUserInfoApiResponse = ItemUserInfoApiResponse.builder()
                .itemApiResponse(itemApiResponse)
                .build();

        return Header.OK(itemUserInfoApiResponse);
    }

}
