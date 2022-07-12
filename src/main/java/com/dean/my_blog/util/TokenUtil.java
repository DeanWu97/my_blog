package com.dean.my_blog.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TokenUtil {
    private static HashMap<String, Object> defaultHeader = new HashMap<String, Object>(2){{put("alg","HS256");put("typ","JWT");}};
    private static String key = "adfwtqhfffaghq2423.asdfqhhydb";

    public static String generateToken(String userEmail) {
        Long expireAt = Instant.now().plus(1L,ChronoUnit.DAYS).toEpochMilli();
        HashMap<String, Object> payload = new HashMap<String, Object>();
        //TODO:get from user threadLoal
        payload.put("userEmail",userEmail);
        return Jwts.builder()
                .setHeader(defaultHeader)
                .setClaims(payload)
                .setExpiration(Date.from(Instant.ofEpochMilli(expireAt)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public static boolean validToken(String token) {
        try {
            Claims claims = (Claims) Jwts.parser().setSigningKey(key).parse(token).getBody();
        } catch (ExpiredJwtException e) {
            log.info("token has expired");
            return false;
        }
        return true;
    }

    public static String getEmailFromToken(String token) {
        Claims claims = (Claims) Jwts.parser().setSigningKey(key).parse(token).getBody();
        return claims.get("userEmail").toString();
    }
}
