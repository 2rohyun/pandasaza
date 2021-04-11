package com.pandasaza.base.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerRefApiResponse {

    private Long itemId;

    private String itemImageUrl;

    private String title;

    private BigDecimal price;

}
