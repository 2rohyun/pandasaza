package com.pandasaza.base.model.dto;

import com.pandasaza.base.model.enumclass.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UserDto {

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

    private String auth;
}
