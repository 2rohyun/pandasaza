package com.pandasaza.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BaseApplication {
    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "classpath:application-credential.yml";

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }

    // TODO( Category --> 기획팀에서 분류가 정확히 결정나면 데이터 삽입 작업 )
    // TODO ( Chatting --> 주문 거래 서버 프로토타이핑 후 채팅 서버 구현하면서 고민해야할 부분 )
    // TODO ( Admin --> 아직 admin view 기획되어 있지 않기 때문에 추후 고민 )

}
