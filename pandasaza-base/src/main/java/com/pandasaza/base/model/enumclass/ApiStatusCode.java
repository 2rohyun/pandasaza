package com.pandasaza.base.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiStatusCode {
    SUCCESS("PS00"),
    FAILURE("PS40"),
    RETRY("PS70"),
    ACCESS_DENIED("PS99");

    String stateCode;
}

