package com.example.studentmanagement.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.studentmanagement.student_management.model.Student;
import com.example.studentmanagement.student_management.model.Parent;
import com.example.studentmanagement.student_management.dto.SignupRequest;
import com.example.studentmanagement.student_management.dto.ParentSignupRequest;
import com.example.studentmanagement.student_management.dto.ChangePasswordRequest;
import com.example.studentmanagement.student_management.dto.JwtResponse;
import com.example.studentmanagement.student_management.dto.LoginRequest;
import com.example.studentmanagement.student_management.utils.UserUtils;
import com.example.studentmanagement.student_management.model.Role;

@Service
public class AuthService {

    @Autowired
    private UserUtils userUtils;

    // Student Signup
    public ResponseEntity<String>  signupStudent(SignupRequest signupRequest) {
        return userUtils.signupUser(Role.STUDENT, signupRequest.getEmail(), signupRequest.getFirstName(),
                signupRequest.getLastName(), signupRequest.getPassword(), new Student(),
                signupRequest.getDateOfBirth(), signupRequest.getGender(), signupRequest.getAddress(),
                signupRequest.getPhoneNumber(), signupRequest.getGrade(), signupRequest.getEnrollmentDate(),
                signupRequest.getParentId());
    }

    // Parent Signup
    public ResponseEntity<String>  signupParent(ParentSignupRequest parentSignupRequest) {
        return userUtils.signupUser(Role.PARENT, parentSignupRequest.getEmail(), parentSignupRequest.getFirstName(),
                parentSignupRequest.getLastName(), parentSignupRequest.getPassword(), new Parent(),
                null, null, parentSignupRequest.getAddress(), parentSignupRequest.getPhoneNumber(), null, null, null);
    }

    // Student Login
    public JwtResponse loginStudent(LoginRequest studentLoginRequest) {
        return userUtils.loginUser(Role.STUDENT, studentLoginRequest.getEmail(), studentLoginRequest.getPassword());
    }

    // Parent Login
    public JwtResponse loginParent(LoginRequest parentLoginRequest) {
        return userUtils.loginUser(Role.PARENT, parentLoginRequest.getEmail(), parentLoginRequest.getPassword());
    }

    // Change Password
    public ResponseEntity<String> changePassword(String token, ChangePasswordRequest request) {
        return userUtils.changePassword(
            token,
            request.getOldPassword(),
            request.getNewPassword()
        );
    }


}
