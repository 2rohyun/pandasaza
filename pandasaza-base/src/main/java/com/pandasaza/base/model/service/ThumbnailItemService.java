package com.pandasaza.base.model.service;

import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.ThumbnailItem;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.repository.ItemRepository;
import com.pandasaza.base.repository.ThumbnailItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ThumbnailItemService {

    private final ItemRepository itemRepository;

    private final ThumbnailItemRepository thumbnailItemRepository;

    public void create(Long id){
        ThumbnailItem thumbnailItem = new ThumbnailItem();
        Optional<Item> item = itemRepository.findById(id);

        item.ifPresent(i->{
            thumbnailItem.setName(i.getName());
            thumbnailItem.setPrice(i.getPrice());
            thumbnailItem.setStatus(i.getStatus());
            thumbnailItem.setRegisteredAt(i.getRegisteredAt());
            thumbnailItem.setTiId(i.getItemId());

//            List<String> itemImagesURLList = Stream.of(
//                    i.getItemImagesUrl()
//                            .split(",",-1))
//                            .collect(Collectors.toList());
//
//            thumbnailItem.setThumbnailImageUrl(itemImagesURLList.get(0));
//            thumbnailItem.setItem(i);
//            thumbnailItem.setCntLike(i.getCntLike());
//            thumbnailItem.setCntShow(i.getCntShow());

            thumbnailItemRepository.save(thumbnailItem);

        });
    }

    public void delete(Long id){
        Optional<ThumbnailItem> optional = thumbnailItemRepository.findById(id);
        optional.ifPresent(thumbnailItemRepository::delete);
    }
}
