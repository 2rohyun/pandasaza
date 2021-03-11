package com.pandasaza.base.service;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.entity.Dib;
import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.DIbApiRequest;
import com.pandasaza.base.model.network.request.ItemApiRequest;
import com.pandasaza.base.model.network.response.DibApiResponse;
import com.pandasaza.base.model.network.response.ItemApiResponse;
import com.pandasaza.base.repository.DibRepository;
import com.pandasaza.base.repository.ItemRepository;
import com.pandasaza.base.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DibApiLogicService implements CrudInterface<DIbApiRequest, DibApiResponse> {

    private final DibRepository dibRepository;

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    @Override
    public Header<DibApiResponse> create(Header<DIbApiRequest> request) {
        DIbApiRequest dibApiRequest = request.getData();

        Dib dib = Dib.builder()
                .item(itemRepository.getOne(dibApiRequest.getItemItemId()))
                .user(userRepository.getOne(dibApiRequest.getUserUserId()))
                .build();

        Dib newDib = dibRepository.save(dib);

        return response(newDib);
    }

    @Override
    public Header<DibApiResponse> read(Long id) {
        Optional<Dib> optional = dibRepository.findById(id);

        return optional
                .map(dib -> response(dib))
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header<DibApiResponse> update(Header<DIbApiRequest> request) { return null; }

    @Override
    public Header delete(Long id){ return null; }

    public Header deleteByItemIdAndUserId(Long itemId, Long userId) {
        Optional<Dib> optional = dibRepository.findByItemIdAndUserId(itemId,userId);

        return optional.map(dib->{
            dibRepository.delete(dib);
            return Header.OK();
        })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private Header<DibApiResponse> response(Dib dib){

        DibApiResponse dibApiResponse = DibApiResponse.builder()
                .dibId(dib.getDibId())
                .itemItemId(dib.getItem().getItemId())
                .userUserId(dib.getUser().getUserId())
                .build();

        return Header.OK(dibApiResponse);
    }
}
