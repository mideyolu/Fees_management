package com.example.studentmanagement.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.student_management.model.Parent;
import com.example.studentmanagement.student_management.repository.ParentRepository;

@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;

    // Get the parent_id (Long) by email
    public Long getParentIdByEmail(String email) {
        Parent parent = parentRepository.findByEmail(email);
        if (parent != null) {
            return parent.getId();  // parent.getId() returns Long
        }
        return null;  // If no parent is found with the provided email
    }
}
