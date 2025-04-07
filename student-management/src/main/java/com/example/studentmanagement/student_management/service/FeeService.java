package com.example.studentmanagement.student_management.service;

import com.example.studentmanagement.student_management.model.Fee;
import com.example.studentmanagement.student_management.repository.FeeRepository;
import com.example.studentmanagement.student_management.repository.StudentRepository;
import com.example.studentmanagement.student_management.utils.FeeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FeeService {

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Fee saveFee(Fee fee, Long studentId) {
        return FeeUtils.saveFee(fee, studentId, studentRepository, feeRepository);
    }

    public List<Fee> getFeesByStudentId(Long studentId) {
        return FeeUtils.getFeesByStudentId(studentId, studentRepository, feeRepository);
    }

    public Optional<Fee> getFeeById(Long id) {
        return feeRepository.findById(id);
    }

    public Fee updateFee(Long id, Fee updatedFee, Long studentId) {
        return FeeUtils.updateFee(id, updatedFee, studentId, studentRepository, feeRepository);
    }

    @Transactional
    public void deleteFee(Long id, Long studentId) {
        FeeUtils.deleteFee(id, studentId, feeRepository);
    }
}
