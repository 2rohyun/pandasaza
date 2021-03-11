package com.pandasaza.base.model.network.response;

import com.pandasaza.base.model.enumclass.UserStatus;
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
public class SellerApiResponse {

    private Long userId;

    private String account;

    private String nation;

    private String university;

    private String profileIcon;

    private float score;

}
