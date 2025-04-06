package com.example.studentmanagement.student_management.dto;

import javax.validation.constraints.NotBlank;

public class UpdateParentRequest {
    @NotBlank
    private String parentEmail;

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }
}
