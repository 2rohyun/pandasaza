package com.pandasaza.base.model.service;

import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserItemService {

    private final UserRepository userRepository;

    public List<Long> getUserSellItem(Long id) {

        Optional<User> user = userRepository.findById(id);

        List<Long> ItemIdList = null;

        for(Item i : user.get().getItemList()){
            ItemIdList = Collections.singletonList(i.getItemId());
        }

        return ItemIdList;

    }

}
