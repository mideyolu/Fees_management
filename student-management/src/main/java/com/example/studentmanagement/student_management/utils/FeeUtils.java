package com.example.studentmanagement.student_management.utils;

import com.example.studentmanagement.student_management.config.JwtUtil;
import com.example.studentmanagement.student_management.dto.CreateFeeRequest;
import com.example.studentmanagement.student_management.model.Fee;
import com.example.studentmanagement.student_management.model.Student;
import com.example.studentmanagement.student_management.repository.FeeRepository;
import com.example.studentmanagement.student_management.repository.StudentRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeeUtils {

    @Autowired
    private JwtUtil jwtUtil;

    public Long extractStudentId(String token) {
        String rawToken = token.replace("Bearer ", "").trim(); // sanitize if needed
        return Long.parseLong(jwtUtil.extractUserId(rawToken));
    }

    public Fee buildFeeFromRequest(CreateFeeRequest request) {
        Fee fee = new Fee();
        fee.setAmountPaid(request.getAmountPaid());
        fee.setPaymentDate(request.getPaymentDate());
        fee.setPaymentStatus(request.getStatus());
        return fee;
    }

    public static Fee saveFee(Fee fee, Long studentId, StudentRepository studentRepo, FeeRepository feeRepo) {
        Optional<Student> studentOpt = studentRepo.findById(studentId);
        if (studentOpt.isPresent()) {
            fee.setStudent(studentOpt.get());
            return feeRepo.save(fee);
        } else {
            throw new IllegalArgumentException("Student not found");
        }
    }

    public static List<Fee> getFeesByStudentId(Long studentId, StudentRepository studentRepo, FeeRepository feeRepo) {
        Optional<Student> studentOpt = studentRepo.findById(studentId);
        return studentOpt.map(feeRepo::findByStudent)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }

    public static Fee updateFee(Long id, Fee updatedFee, Long studentId, StudentRepository studentRepo, FeeRepository feeRepo) {
        Optional<Fee> existingFeeOpt = feeRepo.findById(id);
        Optional<Student> studentOpt = studentRepo.findById(studentId);

        if (existingFeeOpt.isPresent() && studentOpt.isPresent()) {
            Fee existingFee = existingFeeOpt.get();
            if (!existingFee.getStudent().getId().equals(studentId)) {
                throw new IllegalArgumentException("You can only update your own fees.");
            }

            existingFee.setAmountPaid(updatedFee.getAmountPaid());
            existingFee.setPaymentDate(updatedFee.getPaymentDate());
            existingFee.setPaymentStatus(updatedFee.getPaymentStatus());

            return feeRepo.save(existingFee);
        }

        throw new IllegalArgumentException("Fee or Student not found");
    }

    public static void deleteFee(Long id, Long studentId, FeeRepository feeRepo) {
        Optional<Fee> feeOpt = feeRepo.findById(id);
        if (feeOpt.isPresent()) {
            Fee fee = feeOpt.get();
            if (!fee.getStudent().getId().equals(studentId)) {
                throw new IllegalArgumentException("You can only delete your own fees.");
            }
            feeRepo.deleteByPaymentId(id);
        } else {
            throw new IllegalArgumentException("Fee not found");
        }
    }
}
