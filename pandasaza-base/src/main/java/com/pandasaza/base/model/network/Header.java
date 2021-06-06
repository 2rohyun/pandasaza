package com.pandasaza.base.model.network;

import com.pandasaza.base.model.enumclass.ApiStatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header<T> {

    //api 통신 시간
    private LocalDateTime transactionTime; // String으로 정의하여 시간을 어떻게 보여줄 것인지 설정 가능하지만 기본 사용

    private ApiStatusCode apiStatusCode;

    private String message;

    //api 응답 코드
    private String resultCode;

    //api 부가 설명
    private String description;

    private T data;

    private Pagination pagination;

    public static <T> Header<T> OK(){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .build();
    }

    public static <T> Header<T> OK(T data){
        return (Header<T>) Header.builder()
                .apiStatusCode(ApiStatusCode.SUCCESS)
                .message("registered message")
                .data(data)
                .build();
    }

    public static <T> Header<T> ERROR(String description){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description)
                .build();
    }

    public static <T> Header<T> OK(T data,Pagination pagination){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .pagination(pagination)
                .build();
    }
}

