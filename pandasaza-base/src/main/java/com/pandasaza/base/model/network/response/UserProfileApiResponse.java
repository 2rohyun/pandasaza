package com.pandasaza.base.model.network.response;

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
public class UserProfileApiResponse {

    private Long userId;

    private String account;

    private String university;

    private String profileIcon;

    private String nation;

    private float score;

    private List<String> authMethods;

    private List<String> authHistory;

    private LocalDateTime lastLoginAt;

    private List<Long> sellItems;

    private List<Integer> scoreHistory;

    private List<String> reviewHistory;

}
