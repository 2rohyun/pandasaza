package com.pandasaza.base.model.network.response;

import com.pandasaza.base.model.enumclass.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnactiveUserApiResponse {

    private Long uuId;

    private String account;

    private String password;

    private UserStatus status;

    private String email;

    private String phoneNumber;

    private LocalDateTime registeredAt;

    private String nation;

    private List<String> authMethods;

    private List<String> authHistory;

    private String profileIcon;

    private LocalDateTime lastLoginAt;

    private String university;

    private Long userUserId;

}
