package com.example.studentmanagement.student_management.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.example.studentmanagement.student_management.dto.JwtResponse;
import com.example.studentmanagement.student_management.model.Role;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.nio.charset.StandardCharsets;

@Component
public class JwtGenerator {
    private static final String SECRET_KEY = "5ba2db341c7647a0d5f9f11f6836ec1f";

    public JwtResponse generateToken(String userId, String email, Role role, Key signingKey) {
        long expirationTime = System.currentTimeMillis() + 86400000; // 24 hours
        String token = Jwts.builder()
                .setSubject(userId)
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(expirationTime))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
        return new JwtResponse(token, userId, email, role.name(), expirationTime);
    }

    public Key getSigningKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8),
                               SignatureAlgorithm.HS256.getJcaName());
    }
}
