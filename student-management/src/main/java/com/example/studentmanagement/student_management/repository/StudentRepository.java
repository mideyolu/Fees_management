package com.example.studentmanagement.student_management.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.studentmanagement.student_management.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);

    boolean existsByEmail(String email);

    // Custom query to get students by parentId
    List<Student> findByParentId(Long parentId);  // This method will retrieve all students linked to a specific parent.

    @Modifying
    @Query("UPDATE Student s SET s.parent.id = :parentId WHERE s.id = :studentId")
    void updateStudentParent(@Param("studentId") Long studentId,
                            @Param("parentId") Long parentId);

}
