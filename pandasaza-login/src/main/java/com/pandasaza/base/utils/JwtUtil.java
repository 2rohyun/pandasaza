package com.pandasaza.base.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JwtUtil {

    private Key key = Keys.hmacShaKeyFor("12345678901234567890123456789012".getBytes());

/*
    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor("12345678901234567890123456789012".getBytes());
    }

 */

    public String createToken(Long userId, String account) {
        JwtBuilder builder = Jwts.builder()
                .claim("userId", userId)
                .claim("account", account);

        return builder
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
}
