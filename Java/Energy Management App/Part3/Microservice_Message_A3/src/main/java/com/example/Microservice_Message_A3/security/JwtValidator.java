package com.example.Microservice_Message_A3.security;

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

    private static final String SECURITY_KEY = "12pXzQdGw8PzNvUXuuADt9enWi7xmrJ3bbDWfe2R12T2c9O0e4XTl0JzYctYkU2wSpLau85mUqpVgETI";
    private static final long VALIDITY_DURATION_MS = 24 * 60 * 60 * 1000L; // 24 hours in milliseconds
    private static final String CLAIMS_IDENTIFIER = "session_user/";

    private static final Key SIGNING_SECRET = new SecretKeySpec(
            SECURITY_KEY.getBytes(StandardCharsets.UTF_8),
            SignatureAlgorithm.HS512.getJcaName()
    );

    public String createJwt(UUID userId, String userRole) {
        Map<String, Object> claimSet = new HashMap<>();
        claimSet.put("userId", userId.toString());
        claimSet.put("role", userRole);

        return Jwts.builder()
                .setClaims(claimSet)
                .setSubject(CLAIMS_IDENTIFIER + userRole + "/" + userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + VALIDITY_DURATION_MS))
                .signWith(SIGNING_SECRET, SignatureAlgorithm.HS512)
                .compact();
    }


    private boolean checkTokenExpiration(Claims tokenData) {
        return tokenData.getExpiration().before(new Date());
    }

    public boolean verifyToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return false;
        }
        token = token.substring(7); // Remove the "Bearer " prefix

        try {
            Claims tokenData = Jwts.parserBuilder()
                    .setSigningKey(SIGNING_SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return !checkTokenExpiration(tokenData);
        } catch (Exception e) {
            return false;
        }
    }

    public Map<String, Object> extractTokenData(String token) {
        Map<String, Object> extractedData = new HashMap<>();
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SIGNING_SECRET)
                    .build()
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
            extractedData.put("userId", claims.get("userId"));
            extractedData.put("role", claims.get("role"));
        } catch (Exception e) {
            extractedData.put("error", "Invalid token");
        }
        return extractedData;
    }
}
