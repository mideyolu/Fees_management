package com.example.studentmanagement.student_management.dto;

public class JwtResponse {

    private String token;
    private String userId;
    private String email;
    private String role;
    private long expirationTime; 

    public JwtResponse(String token, String userId, String email, String role, long expirationTime) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.expirationTime = expirationTime;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
