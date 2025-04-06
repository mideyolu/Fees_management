package com.example.studentmanagement.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.studentmanagement.student_management.model.Parent;

import com.example.studentmanagement.student_management.repository.StudentRepository;
import com.example.studentmanagement.student_management.repository.ParentRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentRepository parentRepository;


    @Transactional
    public boolean assignParentToStudent(Long studentId, String parentEmail) {
        // Only check parent existence
        Parent parent = parentRepository.findByEmail(parentEmail.trim());
        if (parent == null) {
            return false;
        }

        // Direct update without fetching student
        studentRepository.updateStudentParent(studentId, parent.getId());
        return true;
    }

}
