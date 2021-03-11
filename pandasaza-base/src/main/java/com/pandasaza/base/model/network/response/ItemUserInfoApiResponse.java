package com.pandasaza.base.model.network.response;

import com.pandasaza.base.model.entity.Item;
import com.pandasaza.base.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemUserInfoApiResponse {         //TODO( Later check )

    private ItemApiResponse itemApiResponse;

}
