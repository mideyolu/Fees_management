package com.example.studentmanagement.student_management.controller;

import com.example.studentmanagement.student_management.dto.CreateFeeRequest;
import com.example.studentmanagement.student_management.model.Fee;
import com.example.studentmanagement.student_management.service.FeeService;
import com.example.studentmanagement.student_management.utils.FeeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fee")
public class FeeController {

    @Autowired
    private FeeService feeService;

    @Autowired
    private FeeUtils feeUtils;

    @PostMapping("/add")
    public ResponseEntity<?> addFee(
        @RequestHeader("Authorization") String token,
        @Valid @RequestBody CreateFeeRequest request
    ) {
        try {
            Long studentId = feeUtils.extractStudentId(token);
            Fee savedFee = feeService.saveFee(feeUtils.buildFeeFromRequest(request), studentId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFee);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/my-fees")
    public ResponseEntity<?> getStudentFees(@RequestHeader("Authorization") String token) {
        try {
            Long studentId = feeUtils.extractStudentId(token);
            List<Fee> fees = feeService.getFeesByStudentId(studentId);
            return ResponseEntity.ok(fees);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFeeById(
        @RequestHeader("Authorization") String token,
        @PathVariable Long id
    ) {
        Long studentId = feeUtils.extractStudentId(token);
        Optional<Fee> feeOpt = feeService.getFeeById(id);

        if (feeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fee not found");
        }

        Fee fee = feeOpt.get();
        if (!fee.getStudent().getId().equals(studentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        return ResponseEntity.ok(fee);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFee(
        @RequestHeader("Authorization") String token,
        @PathVariable Long id,
        @Valid @RequestBody CreateFeeRequest request
    ) {
        try {
            Long studentId = feeUtils.extractStudentId(token);
            Fee updatedFee = feeService.updateFee(id, feeUtils.buildFeeFromRequest(request), studentId);
            return ResponseEntity.ok(updatedFee);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFee(
        @RequestHeader("Authorization") String token,
        @PathVariable Long id
    ) {
        try {
            Long studentId = feeUtils.extractStudentId(token);
            feeService.deleteFee(id, studentId);
            return ResponseEntity.ok("Fee record deleted successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
