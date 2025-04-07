package com.example.studentmanagement.student_management.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.studentmanagement.student_management.model.Parent;
import com.example.studentmanagement.student_management.model.Student;
import com.example.studentmanagement.student_management.service.ParentService;
import com.example.studentmanagement.student_management.service.FeeService;
import com.example.studentmanagement.student_management.dto.ParentStudentDetailsDTO;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParentUtils {

    @Autowired
    private ParentService parentService;

    @Autowired
    private FeeService feeService;

    // Method to get children details for a parent based on parent email
    public List<ParentStudentDetailsDTO> getChildrenDetails(Long parentId) {
        // Get the list of children (students) for parent
        List<Student> students = parentService.getChildrenByParentId(parentId);

        // Convert Student to ParentStudentDetailsDTO and filter by parent-child association
        return students.stream()
                .map(student -> {
                    // Ensure the student is assigned to the correct parent
                    if (student.getParent() != null && student.getParent().getId().equals(parentId)) {
                        ParentStudentDetailsDTO dto = new ParentStudentDetailsDTO();
                        dto.setFirstName(student.getFirstName());
                        dto.setLastName(student.getLastName());
                        dto.setDateOfBirth(student.getDateOfBirth());
                        dto.setGender(student.getGender());
                        dto.setGrade(student.getGrade());
                        dto.setEmail(student.getEmail());

                        // Get fee details for this student
                        List<ParentStudentDetailsDTO.FeeDetailsDTO> feeDetails = feeService.getFeesByStudentId(student.getId())
                                .stream()
                                .map(fee -> {
                                    ParentStudentDetailsDTO.FeeDetailsDTO feeDTO = new ParentStudentDetailsDTO.FeeDetailsDTO();
                                    feeDTO.setAmountPaid(fee.getAmountPaid());
                                    feeDTO.setPaymentStatus(fee.getPaymentStatus().name());
                                    feeDTO.setPaymentDate(fee.getPaymentDate().toString());
                                    return feeDTO;
                                })
                                .collect(Collectors.toList());

                        dto.setFeeDetails(feeDetails);
                        return dto;
                    }
                    return null; // If the parent is not assigned to this student, return null
                })
                .filter(dto -> dto != null) // Filter out null values
                .collect(Collectors.toList());
    }

    // Method to check if the parent is authorized to view the student's details
    public boolean isAuthorizedParentForStudent(Long parentId, Long studentId) {
        return parentService.isAuthorizedParentForStudent(parentId, studentId);
    }
}
