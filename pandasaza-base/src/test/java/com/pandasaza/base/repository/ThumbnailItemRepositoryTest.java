package com.pandasaza.base.repository;

import com.pandasaza.base.BaseApplicationTests;
import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.ThumbnailItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ThumbnailItemRepositoryTest extends BaseApplicationTests {

    @Autowired
    private ThumbnailItemRepository thumbnailItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create(){
        ThumbnailItem thumbnailItem = new ThumbnailItem();
        List<Item> item = itemRepository.findAll();
        item.forEach(i->{
            thumbnailItem.setName(i.getName());
            thumbnailItem.setPrice(i.getPrice());
            thumbnailItem.setStatus(i.getStatus());
            thumbnailItem.setRegisteredAt(i.getRegisteredAt());
            thumbnailItem.setTiId(i.getItemId());
            thumbnailItem.setItem(i);

            ThumbnailItem newThumbnailItem = thumbnailItemRepository.save(thumbnailItem);

            Assertions.assertNotNull(newThumbnailItem);
        });
    }
}
