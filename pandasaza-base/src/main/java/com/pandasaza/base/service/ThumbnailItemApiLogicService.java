package com.pandasaza.base.service;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.ThumbnailItem;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.Pagination;
import com.pandasaza.base.model.network.request.ThumbnailItemApiRequest;
import com.pandasaza.base.model.network.response.ItemApiResponse;
import com.pandasaza.base.model.network.response.ThumbnailItemApiResponse;
import com.pandasaza.base.repository.ThumbnailItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThumbnailItemApiLogicService implements CrudInterface<ThumbnailItemApiRequest, ThumbnailItemApiResponse> {

    private final ThumbnailItemRepository thumbnailItemRepository;

    public Header<List<ThumbnailItemApiResponse>> search(Pageable pageable) {
        Page<ThumbnailItem> items = thumbnailItemRepository.findAll(pageable);

        List<ThumbnailItemApiResponse> thumbnailItemApiResponseList = items.stream()
                .map(item->response(item))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(items.getTotalPages())
                .totalElements(items.getTotalElements())
                .currentPage(items.getNumber())
                .currentElements(items.getNumberOfElements())
                .build();

        return Header.OK(thumbnailItemApiResponseList,pagination);
    }

    @Override
    public Header<ThumbnailItemApiResponse> create(Header<ThumbnailItemApiRequest> request) {
        return null;
    }

    @Override
    public Header<ThumbnailItemApiResponse> read(Long id) {
        Optional<ThumbnailItem> optional = thumbnailItemRepository.findById(id);

        return optional
                .map(item -> response(item))
                .map(thumbnailItemApiResponse -> Header.OK(thumbnailItemApiResponse))
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header<ThumbnailItemApiResponse> update(Header<ThumbnailItemApiRequest> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        Optional<ThumbnailItem> optional = thumbnailItemRepository.findById(id);

        return optional.map(item->{
            thumbnailItemRepository.delete(item);
            return Header.OK();
        })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private ThumbnailItemApiResponse response(ThumbnailItem thumbnailItem){

        ThumbnailItemApiResponse thumbnailItemApiResponse = ThumbnailItemApiResponse.builder()
                .name(thumbnailItem.getName())
                .price(thumbnailItem.getPrice())
                .registeredAt(thumbnailItem.getRegisteredAt())
                .status(thumbnailItem.getStatus())
                .tiId(thumbnailItem.getTiId())
                .thumbnailImageUrl(thumbnailItem.getThumbnailImageUrl())
                .cntLike(thumbnailItem.getCntLike())
                .cntShow(thumbnailItem.getCntShow())
                .university(thumbnailItem.getItem().getUser().getUniversity())
                .categoryId(thumbnailItem.getItem().getCategory().getCategoryId())
                .build();
        //Header + data return
        return thumbnailItemApiResponse;
    }
}
