// package com.example.studentmanagement.student_management.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import com.example.studentmanagement.student_management.model.Student;
// import com.example.studentmanagement.student_management.service.FeeService;
// import com.example.studentmanagement.student_management.service.ParentService;
// import com.example.studentmanagement.student_management.config.JwtUtil;
// import com.example.studentmanagement.student_management.dto.ParentStudentDetailsDTO;
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import java.util.List;
// import java.util.stream.Collectors;

// @RestController
// @RequestMapping("/api/parents")
// public class ParentController {

//     @Autowired
//     private ParentService parentService;

//     @Autowired
//     private FeeService feeService;

//     @Autowired
//     private JwtUtil jwtUtil;

//     // Endpoint to get children details for a parent
//     @GetMapping("/children")
//     public List<ParentStudentDetailsDTO> getChildrenDetails(@RequestHeader("Authorization") String token) {
//         // Extract parent email from JWT token using JwtUtil
//         String parentEmail = jwtUtil.extractEmail(token);
//         Long parentId = parentService.getParentIdByEmail(parentEmail);

//         if (parentId == null) {
//             throw new RuntimeException("Parent not found.");
//         }

//         // Get the list of children (students) for parent
//         List<Student> students = parentService.getChildrenByParentId(parentId);

//         // Convert Student to ParentStudentDetailsDTO and filter by parent-child association
//         return students.stream()
//                 .map(student -> {
//                     // Ensure the student is assigned to the correct parent
//                     if (student.getParent() != null && student.getParent().getId().equals(parentId)) {
//                         ParentStudentDetailsDTO dto = new ParentStudentDetailsDTO();
//                         dto.setFirstName(student.getFirstName());
//                         dto.setLastName(student.getLastName());
//                         dto.setDateOfBirth(student.getDateOfBirth());
//                         dto.setGender(student.getGender());
//                         dto.setGrade(student.getGrade());
//                         dto.setEmail(student.getEmail());

//                         // Get fee details for this student
//                         List<ParentStudentDetailsDTO.FeeDetailsDTO> feeDetails = feeService.getFeesByStudentId(student.getId())
//                                 .stream()
//                                 .map(fee -> {
//                                     ParentStudentDetailsDTO.FeeDetailsDTO feeDTO = new ParentStudentDetailsDTO.FeeDetailsDTO();
//                                     feeDTO.setAmountPaid(fee.getAmountPaid());
//                                     feeDTO.setPaymentStatus(fee.getPaymentStatus().name());
//                                     feeDTO.setPaymentDate(fee.getPaymentDate().toString());
//                                     return feeDTO;
//                                 })
//                                 .collect(Collectors.toList());

//                         dto.setFeeDetails(feeDetails);
//                         return dto;
//                     }
//                     return null; // If the parent is not assigned to this student, return null
//                 })
//                 .filter(dto -> dto != null) // Filter out null values
//                 .collect(Collectors.toList());
//     }
// }


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

    @Autowired
    private FeeService feeService;

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
