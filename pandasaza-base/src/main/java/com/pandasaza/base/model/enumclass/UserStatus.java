package com.pandasaza.base.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Getter
public enum UserStatus {

    REGISTERED(0,"등록","사용자 등록상태"),
    UNREGISTERED(1,"해지","사용자 해지상태")
    ;

    private Integer id;
    private String title;
    private String description;
}
