package com.pandasaza.base.model.network.request;

import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DIbApiRequest {

    private Long dibId;

    private Long userUserId;

    private Long itemItemId;

}
