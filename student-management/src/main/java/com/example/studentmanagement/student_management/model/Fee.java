package com.example.studentmanagement.student_management.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

@Entity
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Positive(message = "Amount must be positive")
    private Double amountPaid;

    @PastOrPresent(message = "Payment date cannot be in the future")
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)  // This ensures the enum is saved as a string in the database
    @Column(name = "payment_status")  // Ensure this matches the column name in your database
    private PaymentStatus paymentStatus;

    // Enum for payment status
    public enum PaymentStatus {
        PAID, PENDING
    }

    // Getters and setters
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
