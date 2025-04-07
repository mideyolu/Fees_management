package com.example.studentmanagement.student_management.repository;

import com.example.studentmanagement.student_management.model.Fee;
import com.example.studentmanagement.student_management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    List<Fee> findByStudent(Student student);

    // Custom delete query to directly delete from the 'fee' table
    @Transactional
    @Modifying
    @Query("DELETE FROM Fee f WHERE f.paymentId = :paymentId")
    void deleteByPaymentId(Long paymentId);
}
