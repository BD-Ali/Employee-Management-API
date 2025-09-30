<!-- Project badges -->
<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-REST%20API-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/JPA-PostgreSQL-4169E1?logo=postgresql&logoColor=white" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/License-MIT-black" alt="MIT">
  <img src="https://img.shields.io/badge/Docs-OpenAPI%2FSwagger-85EA2D?logo=swagger&logoColor=white" alt="Swagger">
</p>

<h1 align="center">👔 Employee Management API</h1>
<p align="center">
  A clean Spring Boot REST API with DTOs, validation, a standardized response wrapper, and centralized error handling.
</p>

---

## 🔎 Overview

- CRUD + <strong>PATCH</strong> endpoints under <code>/api/employees</code>
- <strong>DTOs</strong> for input/output (entity not exposed): <code>EmployeeCreate</code>, <code>EmployeeUpdate</code>, <code>EmployeePatch</code>
- <strong>GlobalResponse&lt;T&gt;</strong> wrapper for consistent <em>success / error</em> shapes
- <strong>Validation</strong> on inputs and <strong>@ControllerAdvice</strong> for errors
- <strong>Spring Data JPA</strong> + PostgreSQL driver

---

## 📑 Table of Contents

- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [API Summary](#-api-summary)
- [Request Models (DTOs)](#-request-models-dtos)
- [Response Shape](#-response-shape)
- [Validation & Error Handling](#-validation--error-handling)
- [Run Locally](#-run-locally)
- [OpenAPI / Swagger](#-openapi--swagger)
- [License](#-license)

---

## 🧱 Tech Stack

- **Spring Boot** (Web, Validation, Data JPA)
- **PostgreSQL** JDBC driver
- **Maven**

> Optionally add **springdoc-openapi** if you want interactive docs.

---

## 🗂️ Project Structure

```
src/main/java/com/api/employeemanagementapi
├── EmployeeManagementApiApplication.java
├── controller/
│   └── EmployeeController.java
├── dtos/
│   ├── EmployeeCreate.java
│   ├── EmployeeUpdate.java
│   └── EmployeePatch.java
├── entity/
│   └── Employee.java
├── repository/
│   └── EmployeeRepository.java
├── service/
│   ├── EmployeeService.java
│   └── EmployeeServiceImpl.java
└── shared/
    ├── CustomResponseException.java
    ├── GlobalExceptionResponse.java
    └── GlobalResponse.java
```

> The <code>shared</code> package includes a generic response wrapper, a global exception handler, and a custom exception type.

---

## 🚦 API Summary

> Base path: <code>/api/employees</code>

| Method | Endpoint                   | Purpose                      |
|------: |----------------------------|------------------------------|
| GET    | `/api/employees`           | List all employees           |
| GET    | `/api/employees/{id}`      | Get one employee by id       |
| POST   | `/api/employees`           | Create an employee           |
| PUT    | `/api/employees/{id}`      | Replace an employee          |
| PATCH  | `/api/employees/{id}`      | Partially update an employee |
| DELETE | `/api/employees/{id}`      | Delete an employee           |

<details>
<summary><strong>Sample create (POST)</strong></summary>

```http
POST /api/employees
Content-Type: application/json

{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "phoneNumber": "050-0000000",
  "position": "Software Engineer",
  "salary": 15000.00,
  "hireDate": "2024-06-01"
}
```
</details>

<details>
<summary><strong>Sample success response</strong></summary>

```json
{
  "status": "SUCCESS",
  "data": {
    "id": 1,
    "name": "Jane Doe",
    "email": "jane@example.com",
    "phoneNumber": "050-0000000",
    "position": "Software Engineer",
    "salary": 15000.00,
    "hireDate": "2024-06-01"
  }
}
```
</details>

---

## 📨 Request Models (DTOs)

```java
// EmployeeCreate.java
class EmployeeCreate {
  String name;        // @NotBlank @Size(max = 100)
  String email;       // @NotBlank @Email
  String phoneNumber; // @Size(max = 30)
  String position;    // @Size(max = 100)
  BigDecimal salary;  // @NotNull @DecimalMin("0.00") @Digits(integer = 10, fraction = 2)
  LocalDate hireDate; // @PastOrPresent
}
```

```java
// EmployeeUpdate.java (replace semantics, same constraints as create)
class EmployeeUpdate {
  String name;
  String email;
  String phoneNumber;
  String position;
  BigDecimal salary;
  LocalDate hireDate;
}
```

```java
// EmployeePatch.java (partial update; only non-null fields applied)
class EmployeePatch {
  String name;        // if present, must not be blank
  String email;       // if present, must be valid
  String phoneNumber; // if present, <= 30 chars
  String position;    // if present, <= 100 chars
  BigDecimal salary;  // if present, >= 0
  LocalDate hireDate; // if present, must be past/present
}
```

---

## 📦 Response Shape

All endpoints return **GlobalResponse&lt;T&gt;**:

```json
// success
{
  "status": "SUCCESS",
  "data": { /* payload */ }
}
```
```json
// error
{
  "status": "ERROR",
  "errors": [
    { "message": "Explanation of what went wrong" }
  ]
}
```

---

## 🧯 Validation & Error Handling

Centralized handler **GlobalExceptionResponse** maps common problems to structured errors:

- `CustomResponseException` → HTTP status from the exception (e.g. 404 for missing id)
- `MethodArgumentNotValidException` / `ConstraintViolationException` → **400 Bad Request** (validation details)
- `MethodArgumentTypeMismatchException` → **400 Bad Request** (wrong param types)
- `DataIntegrityViolationException` → **409 Conflict** (DB constraint)
- Generic `Exception` → **500 Internal Server Error**

All error payloads use `GlobalResponse` with an `errors` array of `{ "message": "..." }` items.

---

## ▶️ Run Locally

1) Configure datasource in `src/main/resources/application.properties` (adjust to your env):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/employees
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
> If using Docker, ensure your `docker-compose.yml` Postgres service matches these values.

2) Start the app:
```bash
./mvnw spring-boot:run
# http://localhost:8080
```

---

## 📘 OpenAPI / Swagger

If `springdoc-openapi` is added, you can access:

- Swagger UI → `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON → `http://localhost:8080/v3/api-docs`

> If it’s not enabled yet, add the dependency and it will work out of the box.

---

## 🪪 License

This project is licensed under the **MIT License**.
