package com.pandasaza.base.model.network.response;

import com.pandasaza.base.model.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThumbnailItemApiResponse {

    private Long tiId;

    private Long itemItemId;

    private String status;

    private String name;

    private BigDecimal price;

    private LocalDateTime registeredAt;

    private String thumbnailImageUrl;

    private String university;

    private Integer cntLike;

    private Integer cntShow;

}
