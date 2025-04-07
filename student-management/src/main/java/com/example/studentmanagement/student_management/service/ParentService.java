package com.example.studentmanagement.student_management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.studentmanagement.student_management.model.Parent;
import com.example.studentmanagement.student_management.model.Student;
import com.example.studentmanagement.student_management.repository.ParentRepository;
import com.example.studentmanagement.student_management.repository.StudentRepository;

@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Get the parent_id (Long) by email
    public Long getParentIdByEmail(String email) {
        Parent parent = parentRepository.findByEmail(email);
        if (parent != null) {
            return parent.getId(); // parent.getId() returns Long
        }
        return null; // If no parent is found with the provided email
    }

    // Get list of students (children) associated with a parent
    public List<Student> getChildrenByParentId(Long parentId) {
        return studentRepository.findByParentId(parentId); // Ensure you have this method in the StudentRepository
    }

    // Ensure the parent is authorized to view the student details
    public boolean isAuthorizedParentForStudent(Long parentId, Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null && student.getParent() != null && student.getParent().getId().equals(parentId)) {
            return true;
        }
        return false; // If the parent is not authorized for the student
    }
}
