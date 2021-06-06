package com.pandasaza.base.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileApiResponse {
    private Long profileId;

    private String profileName;

    private String profileImage;

    private Date registeredAt;

    private Date updatedAt;
}
