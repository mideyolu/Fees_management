package com.example.studentmanagement.student_management.model;

public interface User {
    Long getId();  // Can be either studentId or parentId
    String getEmail();
    String getPassword();
    String getFirstName();
    String getLastName();
    String getAddress();
    String getPhoneNumber();
    void setEmail(String email);
    void setPassword(String password);
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setAddress(String address);
    void setPhoneNumber(String phoneNumber);
}
