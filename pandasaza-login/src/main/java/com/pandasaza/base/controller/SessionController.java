package com.pandasaza.base.controller;

import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.model.network.request.SessionRequest;
import com.pandasaza.base.model.network.response.SessionResponse;
import com.pandasaza.base.model.service.UserLoginService;
import com.pandasaza.base.service.UserApiLogicService;
import com.pandasaza.base.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class SessionController {

    private final UserLoginService userLoginService;

    private final JwtUtil jwtUtil;

    @PostMapping("/session")
    public ResponseEntity<SessionResponse> create(
            @RequestBody SessionRequest resource
    ) throws URISyntaxException {
        String account = resource.getAccount();
        String password = resource.getPassword();

        User user = userLoginService.authenticate(account, password);

        String accessToken = jwtUtil.createToken(user.getUserId(), user.getAccount());

        String url = "/session";

        return ResponseEntity.created(new URI(url)).body(
                SessionResponse.builder()
                .accessToken(accessToken)
                .build()
        );
    }
}
