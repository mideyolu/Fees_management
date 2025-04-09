package com.example.studentmanagement.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.studentmanagement.student_management.service.FeeService;
import com.example.studentmanagement.student_management.service.ParentService;
import com.example.studentmanagement.student_management.config.JwtUtil;
import com.example.studentmanagement.student_management.dto.ParentStudentDetailsDTO;
import com.example.studentmanagement.student_management.utils.ParentUtils;
import java.util.List;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    @Autowired
    private ParentService parentService;

    // @Autowired
    // private FeeService feeService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ParentUtils parentUtils; // Inject ParentUtils

    // Endpoint to get children details for a parent
    @GetMapping("/children")
    public List<ParentStudentDetailsDTO> getChildrenDetails(@RequestHeader("Authorization") String token) {
        // Extract parent email from JWT token using JwtUtil
        String parentEmail = jwtUtil.extractEmail(token);
        Long parentId = parentService.getParentIdByEmail(parentEmail);

        if (parentId == null) {
            throw new RuntimeException("Parent not found.");
        }

        // Use ParentUtils to get children details
        return parentUtils.getChildrenDetails(parentId);
    }
}
