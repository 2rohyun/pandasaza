package com.pandasaza.base.model.network.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemApiRequest {

    private Long itemId;

    private String status;

    private String name;

    private String title;

    private String content;

    private BigDecimal price;

    private LocalDateTime registeredAt;

    private List<String> itemImagesUrl;

    private Integer cntLike;

    private Integer cntShow;

    private Long userUserId;

    private Long categoryCategoryId;


}
