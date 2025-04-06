package com.example.studentmanagement.student_management.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.studentmanagement.student_management.model.Fee;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    List<Fee> findByStudentId(Long studentId);
   // boolean existsByIdAndStudentId(Long feeId, Long studentId);
}
