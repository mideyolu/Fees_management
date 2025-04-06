
package com.example.studentmanagement.student_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.studentmanagement.student_management.model.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    @Query("SELECT p FROM Parent p WHERE p.email = :email")
    Parent findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

}
