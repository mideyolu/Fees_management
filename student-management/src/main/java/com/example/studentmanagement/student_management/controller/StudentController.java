package com.example.studentmanagement.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.studentmanagement.student_management.dto.UpdateParentRequest;
import com.example.studentmanagement.student_management.repository.StudentRepository;
import com.example.studentmanagement.student_management.service.StudentService;
import com.example.studentmanagement.student_management.utils.StudentUtils;
import com.example.studentmanagement.student_management.config.JwtUtil;


@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PatchMapping("/assign-parent")
    public ResponseEntity<String> assignParent(
        @RequestHeader("Authorization") String token,
        @RequestBody UpdateParentRequest request
    ) {
        String userId = jwtUtil.extractUserId(token);
        return StudentUtils.assignParent(
            userId,
            request.getParentEmail(),
            studentService::assignParentToStudent,
            studentRepository
        );
    }
}


