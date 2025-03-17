package com.example.Microservice_User_A3.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtValidator {
    private static final String ENCRYPTION_KEY = "12pXzQdGw8PzNvUXuuADt9enWi7xmrJ3bbDWfe2R12T2c9O0e4XTl0JzYctYkU2wSpLau85mUqpVgETI";
    private static final int TOKEN_VALIDITY_PERIOD = 24 * 60 * 60 * 1000;  // 24 ore in milisecunde
    private static final String TOKEN_SUBJECT_PREFIX = "user_logged/";
    private static final Key SIGNING_KEY = new SecretKeySpec(
            ENCRYPTION_KEY.getBytes(StandardCharsets.UTF_8),
            SignatureAlgorithm.HS512.getJcaName()
    );

    public String generateToken(UUID userId, String role) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(TOKEN_SUBJECT_PREFIX + role + "/" + userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_PERIOD))
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    private boolean hasTokenExpired(Claims tokenDetails) {
        return tokenDetails.getExpiration().before(new Date());
    }

    public boolean validateToken(String jwtToken) {
        if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
            return false;
        }
        jwtToken = jwtToken.replace("Bearer ", "");

        try {
            Claims tokenDetails = Jwts.parserBuilder()
                    .setSigningKey(SIGNING_KEY)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

            return !hasTokenExpired(tokenDetails);
        } catch (Exception ex) {
            return false;
        }
    }

}
