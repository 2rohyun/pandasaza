package com.pandasaza.base.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDataApiResponse {

    private Long userId;

    private String email;

    private String account;

    private String phoneNumber;

    private String university;

}
