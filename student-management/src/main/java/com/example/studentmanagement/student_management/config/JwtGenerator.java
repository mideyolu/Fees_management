package com.example.studentmanagement.student_management.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.example.studentmanagement.student_management.dto.JwtResponse;
import com.example.studentmanagement.student_management.model.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtGenerator {

    @Value("${jwt.secret.key}")
    private String secretKey;

    public JwtResponse generateToken(String userId, String email, Role role, Key signingKey) {
        long expirationTime = System.currentTimeMillis() + 86400000; // 24 hours
        String token = Jwts.builder()
                .setSubject(userId)
                .claim("role", role.name())
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(expirationTime))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
        return new JwtResponse(token, userId, email, role.name(), expirationTime);
    }

    public Key getSigningKey() {
        return new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName());
    }
}
