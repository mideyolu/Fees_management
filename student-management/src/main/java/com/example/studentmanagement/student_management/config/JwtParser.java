package com.example.studentmanagement.student_management.config;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import java.util.Date;
import java.security.Key;

@Component
public class JwtParser {
    public String cleanToken(String token) {
        if (token == null) throw new JwtException("Token cannot be null");
        String cleaned = token.replaceAll("(?i)^bearer\\s*", "").trim();
        if (cleaned.isEmpty()) throw new JwtException("Empty token after cleaning");
        return cleaned;
    }

    public Claims extractAllClaims(String token, Key signingKey) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
