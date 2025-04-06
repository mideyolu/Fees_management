package com.example.studentmanagement.student_management.error;

public class UserException extends RuntimeException {

    private String errorMessage;

    public UserException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
