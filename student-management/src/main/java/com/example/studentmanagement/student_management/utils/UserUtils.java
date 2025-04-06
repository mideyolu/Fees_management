package com.example.studentmanagement.student_management.utils;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.studentmanagement.student_management.model.Parent;
import com.example.studentmanagement.student_management.model.Role;
import com.example.studentmanagement.student_management.model.Student;
import com.example.studentmanagement.student_management.model.User;
import com.example.studentmanagement.student_management.config.JwtUtil;
import com.example.studentmanagement.student_management.dto.JwtResponse;
import com.example.studentmanagement.student_management.error.UserException;
import com.example.studentmanagement.student_management.repository.StudentRepository;
import com.example.studentmanagement.student_management.repository.ParentRepository;

@Service
public class UserUtils {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentRepository parentRepository;

    // Signup logic using only StudentRepository and ParentRepository
    public ResponseEntity<String> signupUser(Role role, String email, String firstName, String lastName, String password,
                                             User entity, LocalDate dateOfBirth, String gender,
                                             String address, String phoneNumber, String grade,
                                             LocalDate enrollmentDate, Long parentId) {

        if (entity instanceof Student) {
            if (studentRepository.existsByEmail(email)) {
                throw new UserException("Email already exists");
            }

            Student student = (Student) entity;
            student.setEmail(email);
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setPassword(passwordEncoder.encode(password));
            student.setAddress(address);
            student.setPhoneNumber(phoneNumber);
            student.setDateOfBirth(dateOfBirth);
            student.setGender(gender);
            student.setGrade(grade);
            student.setEnrollmentDate(enrollmentDate);

            if (parentId != null) {
                Parent parent = parentRepository.findById(parentId).orElse(null);
                student.setParent(parent);
            }

            studentRepository.save(student);
            return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered");

        } else if (entity instanceof Parent) {
            if (parentRepository.existsByEmail(email)) {
                throw new UserException("Email already exists");
            }

            Parent parent = (Parent) entity;
            parent.setEmail(email);
            parent.setFirstName(firstName);
            parent.setLastName(lastName);
            parent.setPassword(passwordEncoder.encode(password));
            parent.setAddress(address);
            parent.setPhoneNumber(phoneNumber);

            parentRepository.save(parent);
            return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered");

        } else {
            throw new UserException("Unsupported entity type");
        }
    }

    // Login logic
    public JwtResponse loginUser(Role role, String email, String password) {
        User user;

        if (role == Role.STUDENT) {
            user = studentRepository.findByEmail(email);
        } else if (role == Role.PARENT) {
            user = parentRepository.findByEmail(email);
        } else {
            throw new UserException("Invalid role provided");
        }

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException("Invalid email or password");
        }

        return jwtUtil.generateToken(user.getId().toString(), user.getEmail(), role);
    }

    public ResponseEntity<String> changePassword(String token, String oldPassword, String newPassword) {
        try {
            // Extract user info from token
            String userId = jwtUtil.extractUserId(token);
            String role = jwtUtil.extractRole(token);

            // Find user based on role
            User user = null;
            if (role.equals(Role.STUDENT.name())) {
                user = studentRepository.findById(Long.parseLong(userId)).orElse(null);
            } else if (role.equals(Role.PARENT.name())) {
                user = parentRepository.findById(Long.parseLong(userId)).orElse(null);
            }

            // Verify user exists
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            // Verify old password matches
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect old password");
            }

            // Update password
            user.setPassword(passwordEncoder.encode(newPassword));

            // Save based on role
            if (user instanceof Student) {
                studentRepository.save((Student) user);
            } else if (user instanceof Parent) {
                parentRepository.save((Parent) user);
            }

            return ResponseEntity.ok("Password changed successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error changing password: " + e.getMessage());
        }
    }
}
