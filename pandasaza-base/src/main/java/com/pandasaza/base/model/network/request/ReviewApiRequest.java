package com.pandasaza.base.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewApiRequest {

    private Long reviewId;

    private String title;

    private String content;

    private Integer score;

    private LocalDateTime registeredAt;

    private Long orderDetailOrderDetailId;

}
