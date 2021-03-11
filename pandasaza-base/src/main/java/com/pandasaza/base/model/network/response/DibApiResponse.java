package com.pandasaza.base.model.network.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DibApiResponse {

    private Long dibId;

    private Long userUserId;

    private Long itemItemId;

}
