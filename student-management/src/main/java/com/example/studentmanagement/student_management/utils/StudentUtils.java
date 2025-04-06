package com.example.studentmanagement.student_management.utils;

import com.example.studentmanagement.student_management.model.Student;
import com.example.studentmanagement.student_management.repository.StudentRepository;
import org.springframework.http.ResponseEntity;

public class StudentUtils {

    public static ResponseEntity<String> assignParent(
        String userIdFromToken,
        String parentEmail,
        ParentAssignmentHandler handler,
        StudentRepository studentRepository
    ) {
        try {
            // 1. Extract and validate student ID
            Long studentId = Long.parseLong(userIdFromToken);

            // 2. Check if student already has a parent assigned
            Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

            if (student.getParent() != null) {
                return ResponseEntity.badRequest()
                    .body("Parent already assigned. Cannot reassign.");
            }

            // 3. Process parent assignment
            boolean success = handler.assignParent(studentId, parentEmail.trim());

            return success ?
                ResponseEntity.ok("Parent assigned successfully") :
                ResponseEntity.badRequest().body("Parent not found");

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid student ID format");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error: " + e.getMessage());
        }
    }

    @FunctionalInterface
    public interface ParentAssignmentHandler {
        boolean assignParent(Long studentId, String parentEmail);
    }
}
