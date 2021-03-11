package com.pandasaza.base.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailApiRequest {

    private Long orderId;

    private String status;

    private Long userUserId;

    private Long itemItemId;

}
