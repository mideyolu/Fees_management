package com.example.studentmanagement.student_management.controller;

import com.example.studentmanagement.student_management.dto.ChangePasswordRequest;
import com.example.studentmanagement.student_management.dto.JwtResponse;
import com.example.studentmanagement.student_management.dto.SignupRequest;
import com.example.studentmanagement.student_management.dto.LoginRequest;
import com.example.studentmanagement.student_management.dto.ParentSignupRequest;
import com.example.studentmanagement.student_management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Student Signup
    @PostMapping("/signup-student")
    public ResponseEntity<String> signupStudent(@RequestBody SignupRequest signupRequest) {
        // Call the signupStudent method and return a success message
        authService.signupStudent(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered");
    }

    // Student Login
    @PostMapping("/login-student")
    public JwtResponse loginStudent(@RequestBody LoginRequest studentLoginRequest) {
        return authService.loginStudent(studentLoginRequest);
    }

    // Parent Signup
    @PostMapping("/signup-parent")
    public ResponseEntity<String>  signupParent(@RequestBody ParentSignupRequest parentSignupRequest) {
        return authService.signupParent(parentSignupRequest);
    }

    // Parent Login
    @PostMapping("/login-parent")
    public JwtResponse loginParent(@RequestBody LoginRequest parentLoginRequest) {
        return authService.loginParent(parentLoginRequest);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordRequest changePasswordRequest) {
        return authService.changePassword(token, changePasswordRequest);
    }

    // Live Route
    @GetMapping("/live")
    public String live() {
        return "Server is live";
    }
}
