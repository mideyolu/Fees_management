package com.example.studentmanagement.student_management.dto;

import java.time.LocalDate;
import java.util.List;

public class ParentStudentDetailsDTO {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String grade;
    private String email;
    private List<FeeDetailsDTO> feeDetails;

    // Constructor
    public ParentStudentDetailsDTO() {}

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<FeeDetailsDTO> getFeeDetails() {
        return feeDetails;
    }

    public void setFeeDetails(List<FeeDetailsDTO> feeDetails) {
        this.feeDetails = feeDetails;
    }

    // Inner FeeDetailsDTO class
    public static class FeeDetailsDTO {
        private Double amountPaid;
        private String paymentStatus;
        private String paymentDate;

        // Constructor
        public FeeDetailsDTO() {}

        // Getters and Setters
        public Double getAmountPaid() {
            return amountPaid;
        }

        public void setAmountPaid(Double amountPaid) {
            this.amountPaid = amountPaid;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getPaymentDate() {
            return paymentDate;
        }

        public void setPaymentDate(String paymentDate) {
            this.paymentDate = paymentDate;
        }
    }
}
