package com.pandasaza.base.service;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.OrderDetail;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.ItemApiRequest;
import com.pandasaza.base.model.network.request.OrderDetailApiRequest;
import com.pandasaza.base.model.network.response.ItemApiResponse;
import com.pandasaza.base.model.network.response.OrderDetailApiResponse;
import com.pandasaza.base.repository.ItemRepository;
import com.pandasaza.base.repository.OrderDetailRepository;
import com.pandasaza.base.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailApiLogicService implements CrudInterface<OrderDetailApiRequest, OrderDetailApiResponse> {

    private final OrderDetailRepository orderDetailRepository;

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    @Override
    public Header<OrderDetailApiResponse> create(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest orderDetailApiRequest = request.getData();

        OrderDetail orderDetail = OrderDetail.builder()
                .item(itemRepository.getOne(orderDetailApiRequest.getItemItemId()))
                .user(userRepository.getOne(orderDetailApiRequest.getUserUserId()))
                .status(orderDetailApiRequest.getStatus())
                .build();

        OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);

        return response(newOrderDetail);

    }

    @Override
    public Header<OrderDetailApiResponse> read(Long id) {
        Optional<OrderDetail> optional = orderDetailRepository.findById(id);

        return optional
                .map(orderDetail -> response(orderDetail))
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header<OrderDetailApiResponse> update(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest orderDetailApiRequest = request.getData();

        Optional<OrderDetail> optional = orderDetailRepository.findById(orderDetailApiRequest.getOrderId());

        return optional.map(orderDetail->{

            orderDetail.setStatus(orderDetailApiRequest.getStatus());

            return orderDetail;
        })
                .map(orderDetail->orderDetailRepository.save(orderDetail))
                .map(updateOrderDetail->response(updateOrderDetail))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<OrderDetail> optional = orderDetailRepository.findById(id);

        return optional.map(orderDetail->{
            orderDetailRepository.delete(orderDetail);
            return Header.OK();
        })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private Header<OrderDetailApiResponse> response(OrderDetail orderDetail){

        OrderDetailApiResponse orderDetailApiResponse = OrderDetailApiResponse.builder()
                .orderId(orderDetail.getOrderId())
                .userUserId(orderDetail.getUser().getUserId())
                .itemItemId(orderDetail.getItem().getItemId())
                .status(orderDetail.getStatus())
                .build();
        //Header + data return
        return Header.OK(orderDetailApiResponse);
    }
}
