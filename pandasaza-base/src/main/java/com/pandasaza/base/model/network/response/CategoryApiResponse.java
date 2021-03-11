package com.pandasaza.base.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoryApiResponse {    // TODO( delete this class if doesn't need )

    private Long categoryId;

    private String type;

    private int groupCode;

}
