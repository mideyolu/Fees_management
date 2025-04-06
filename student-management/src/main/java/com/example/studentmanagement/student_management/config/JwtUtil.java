package com.example.studentmanagement.student_management.config;

import org.springframework.stereotype.Component;
import com.example.studentmanagement.student_management.dto.JwtResponse;
import com.example.studentmanagement.student_management.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import java.security.Key;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final JwtGenerator generator;
    private final JwtParser parser;
    private final Key signingKey;

    public JwtUtil(JwtGenerator generator, JwtParser parser) {
        this.generator = generator;
        this.parser = parser;
        this.signingKey = generator.getSigningKey();
    }

    // Generates a JWT token for the given userId, email, and role.
    public JwtResponse generateToken(String userId, String email, Role role) {
        return generator.generateToken(userId, email, role, signingKey);
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public boolean validateToken(String token, String userId) {
        try {
            final String cleanedToken = parser.cleanToken(token);
            final Claims claims = parser.extractAllClaims(cleanedToken, signingKey);
            return userId.equals(claims.getSubject()) && !parser.isTokenExpired(claims);
        } catch (JwtException e) {
            return false;
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final String cleanedToken = parser.cleanToken(token);
        final Claims claims = parser.extractAllClaims(cleanedToken, signingKey);
        return claimsResolver.apply(claims);
    }
}
