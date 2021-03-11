package com.pandasaza.base.model.network.response;

import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.enumclass.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserApiResponse {

    private Long userId;

    private String account;

    private String password;

    private UserStatus status;

    private String email;

    private String phoneNumber;

    private LocalDateTime registeredAt;

    private String nation;

    private LocalDateTime lastLoginAt;

    private String university;

    private String profileIcon;

    private List<String> authMethods;

    private List<String> authHistory;

    private List<Long> itemList;

    private float score;

}
