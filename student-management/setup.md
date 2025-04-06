```markdown
# 🎓 Fees Management System

A Spring Boot-based Student Management System with support for JWT authentication and MySQL integration.

---

## Backend Setup Guide

### Prerequisites

- Java 17 or higher installed
- Maven installed
- MySQL running via [XAMPP](https://www.apachefriends.org/index.html)

---

### Database Setup

1. Open **XAMPP Control Panel**
2. Start **MySQL**
3. Create the database:

```sql
CREATE DATABASE student_management;
```

---

### 🔧 Configure `application.properties`

Navigate to `src/main/resources/application.properties` and update the file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> Replace `yourpassword` with your actual MySQL root password. Adjust the port if different (e.g., `3307`).

---

### Running the Application

#### Option 1: Using IDE

- Open the project in your IDE (e.g., VS Code)
- Press `Ctrl + F5` or click the **Run** button


> The application will start at:
> `http://localhost:8080/`

---

## API Endpoints Overview

| Method | Endpoint                           | Description                  |
|--------|------------------------------------|------------------------------|
| POST   | `auth/signup/student`              | Register a new student       |
| POST   | `auth/signup/parent`               | Register a new parent        |
| POST   | `auth/login/student`               | Student login                |
| POST   | `auth/login/parent`                | Parent login                 |
| PATCH  | `auth/change-password`             | Change password (both roles) |
| PATCH  | `/api/student/assign-parent`       | Assign parent to student     |

> All requests should use `Content-Type: application/json`
> Add token to headers like this:
> `Authorization: Bearer <token>`

---

## Project Structure

```
student-management/
├── model/
│   ├── Student.java
│   ├── Parent.java
│   ├── Fee.java
│   ├── Role.java
│   └── User.java
├── dto/
│   ├── SignupRequest.java
│   ├── ParentSignupRequest.java
│   ├── LoginRequest.java
│   ├── UpdateParentRequest.java
│   ├── JwtResponse.java
│   └── ChangePasswordRequest.java
├── repository/
│   ├── StudentRepository.java
│   └── ParentRepository.java
├── controller/
│   ├── AuthController.java
│   ├── StudentController.java
│   └── ParentController.java
├── service/
│   ├── AuthService.java
│   ├── StudentService.java
│   └── ParentService.java
├── utils/
│   ├── UserUtils.java
│   └── StudentUtils.java
├── config/
│   ├── JwtUtil.java
│   ├── JwtGenerator.java
│   ├── JwtParser.java
│   ├── JwtFilter.java
│   └── SecurityConfig.java
├── resources/
│   └── application.properties
└── StudentManagementApplication.java
```

---

## Testing the API

Use [Postman](https://www.postman.com/) Preferably download the local setup file

### Auth Endpoints

#### Register Student

```http
POST /signup/student
```

**Body:**
```json
{
    "firstName": "firstname",
    "lastName": "lastName",
    "dateOfBirth": "2000-01-01",
    "gender": "Male",
    "address": "...........",
    "phoneNumber": "08093935678",
    "email": "userEmail@gmail.com",
    "grade": "x level",
    "enrollmentDate": "2023-09-01",
    "parent": null, //for now
    "password": "hikaix_&99"
}
```

#### 🔸 Register Parent

```http
POST /signup/parent
```

**Body:**
```json
{
    "firstName": "firstname",
    "lastName": "lastName",
    "address": "123 Main St",
    "phoneNumber": "26478434654",
    "email": "userEmail@gmail.com",
    "password": "userPassword"
}
```

#### 🔸 Login (Both Roles)

```http
POST /login/student
POST /login/parent
```

**Body:**
```json
{
  "email": "useremail@gmail.com",
  "password": "userpassword"
}
```

---

### 🔐 Secure Endpoints

#### 🔄 Change Password

```http
PATCH /change-password
```

**Body:**
```json
{
  "oldPassword": "oldpasswordvalue",
  "newPassword": "newpasswordvalue"
}
```

#### Assign Parent to Student

```http
PATCH /api/student/assign-parent
```

**Body:**
```json
{
  "parentEmail": "registeredparent@gmail.com"
}
```

> Make sure to include your JWT token in the `Authorization` header for secure endpoints.

---
