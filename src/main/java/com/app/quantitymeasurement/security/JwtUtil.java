package com.app.quantitymeasurement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component("appJwtUtil")
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expirationMillis;

    public JwtUtil(@Value("${app.jwt.secret}") String secret,
                   @Value("${app.jwt.expiration}") long expirationMillis) {
        this.expirationMillis = expirationMillis;

        // Ensure secret is not null/empty
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("JWT secret must be configured and non-empty");
        }

        byte[] keyBytes = ensureMinLength(secret.getBytes(StandardCharsets.UTF_8), 32);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    private static byte[] ensureMinLength(byte[] src, int minLen) {
        if (src.length >= minLen) return src;
        byte[] padded = new byte[minLen];
        System.arraycopy(src, 0, padded, 0, src.length);
        for (int i = src.length; i < minLen; i++) {
            padded[i] = (byte) (i * 31); // deterministic filler
        }
        return padded;
    }

    public String generateToken(String email, String name, Collection<String> roles) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be null or blank when generating JWT");
        }

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMillis);

        Map<String, Object> claims = new HashMap<>();
        claims.put("name", name == null ? "" : name);
        claims.put("roles", roles == null || roles.isEmpty() ? List.of("ROLE_USER") : new ArrayList<>(roles));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            // Optionally log the exception here
            return false;
        }
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    public String getEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Object roles = parseClaims(token).get("roles");
        if (roles instanceof List<?>) {
            return ((List<?>) roles).stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .toList();
        }
        return List.of();
    }
}
