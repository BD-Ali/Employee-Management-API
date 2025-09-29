# Employee Management API

A clean, production‑ready **Spring Boot** REST API for managing employees. It exposes CRUD endpoints, validates input (e.g., non‑blank email), and persists data in **PostgreSQL**. Ideal as a starter backend for HR dashboards or as a learning project for Spring Data JPA.

<p align="left">
  <img alt="Java" src="https://img.shields.io/badge/Java-21%2B-%23ea2d2e?logo=java&logoColor=white">
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-3.5.x-6DB33F?logo=spring-boot&logoColor=white">
  <img alt="Build" src="https://img.shields.io/badge/Maven-Build-blue?logo=apache-maven">
  <img alt="Database" src="https://img.shields.io/badge/PostgreSQL-16-336791?logo=postgresql&logoColor=white">
</p>

> **Heads‑up:** The app expects a running PostgreSQL instance. If the DB isn’t reachable, startup will fail with a `Connection refused` error. See **Quick Start** and **Troubleshooting** below.

---

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Data Model](#data-model)
- [Quick Start](#quick-start)
    - [Option A — Docker (recommended)](#option-a--docker-recommended)
    - [Option B — Local PostgreSQL](#option-b--local-postgresql)
- [Running the App](#running-the-app)
- [API](#api)
    - [Request/Response Examples](#requestresponse-examples)
- [Validation & Errors](#validation--errors)
- [Troubleshooting](#troubleshooting)
- [Next Steps](#next-steps)
- [License](#license)

---

## Features
- ✅ RESTful CRUD for employees
- ✅ Input validation (e.g., email must be non‑blank)
- ✅ Standardized API responses (success/error)
- ✅ Database persistence with Spring Data JPA
- ✅ Actuator health endpoint enabled

## Tech Stack
- **Java**: 21+ (works with 24 as well)
- **Spring Boot**: 3.5.x
    - Spring Web
    - Spring Data JPA
    - Validation (Jakarta Validation)
    - Actuator
- **Database**: PostgreSQL (via JDBC + HikariCP)
- **Build**: Maven
- **Lombok** (for boilerplate reduction)

## Project Structure
```
src/main/java/com/api/employeemanagementapi
├─ EmployeeManagementApiApplication.java
├─ entity/
│  └─ Employee.java
├─ repository/
│  └─ EmployeeRepository.java
├─ service/
│  ├─ EmployeeService.java
│  └─ EmployeeServiceImpl.java
├─ controller/
│  └─ EmployeeController.java
└─ shared/ (standardized responses/errors, etc.)
```

## Data Model
**Employee**
- `id: Long` (PK, generated)
- `name: String`
- `email: String` *(must not be blank)*
- `phoneNumber: String`
- `position: String`
- `salary: BigDecimal`
- `hireDate: LocalDate`

---

## Quick Start

### Option A — Docker
Run PostgreSQL in a container mapped to port **5434** (as commonly used in local dev):
```bash
docker run --name pg-employees -e POSTGRES_DB=employees   -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres   -p 5434:5432 -d postgres:16
```
Check logs until you see “database system is ready to accept connections”:
```bash
docker logs -f pg-employees
```

### Option B — Local PostgreSQL
Install Postgres and create a DB:
```sql
CREATE DATABASE employees;
CREATE USER postgres WITH PASSWORD 'postgres';
GRANT ALL PRIVILEGES ON DATABASE employees TO postgres;
```

### Spring configuration (`application.properties`)
```properties
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5434/employees
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Actuator
management.endpoints.web.exposure.include=health,info
```
> Change the port/credentials to match your environment.

---

## Running the App
```bash
# build
mvn clean package

# run
mvn spring-boot:run
# or
java -jar target/employee-management-api-*.jar
```

Health check:
```
GET http://localhost:8080/actuator/health
```

---

## API
Base URL: `http://localhost:8080/api/employees`

| Method | Path                | Description                |
|-------:|---------------------|----------------------------|
|  GET   | `/`                 | List all employees         |
|  GET   | `/{id}`             | Get employee by id         |
|  POST  | `/`                 | Create employee            |
|  PUT   | `/{id}`             | Replace employee           |
|  PATCH | `/{id}`             | Partial update employee    |
| DELETE | `/{id}`             | Delete employee            |


### Request/Response Examples

**Create**
```bash
curl -i -X POST http://localhost:8080/api/employees   -H "Content-Type: application/json"   -d '{
    "name": "Jane Doe",
    "email": "jane@company.com",
    "phoneNumber": "+972-50-123-4567",
    "position": "Software Engineer",
    "salary": 18500.00,
    "hireDate": "2025-09-01"
  }'
```

**Get All**
```bash
curl -s http://localhost:8080/api/employees | jq
```

**Update (PUT)**
```bash
curl -i -X PUT http://localhost:8080/api/employees/1   -H "Content-Type: application/json"   -d '{
    "name": "Jane A. Doe",
    "email": "jane@company.com",
    "phoneNumber": "+972-50-123-4567",
    "position": "Senior Engineer",
    "salary": 21000.00,
    "hireDate": "2025-09-01"
  }'
```

**Partial Update (PATCH)**
```bash
curl -i -X PATCH http://localhost:8080/api/employees/1   -H "Content-Type: application/json"   -d '{ "position": "Team Lead", "salary": 23000.00 }'
```

**Delete**
```bash
curl -i -X DELETE http://localhost:8080/api/employees/1
```

---

## Validation & Errors
The API returns standardized error payloads when validation fails. Example:
```json
{
  "status": "error",
  "data": null,
  "errors": [
    { "message": "Email cannot be null or blank" }
  ]
}
```
Typical checks:
- `email` must not be blank (and should be a valid format if you add `@Email`)
- `name` must not be blank
- `salary` should be non‑negative, etc.

---

## Troubleshooting
- **`Connection to localhost:5434 refused`**  
  Ensure Postgres is running and listening on the configured port. If your DB is on the default port, change the URL to `5432`:
  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/employees
  ```

- **Dialect error (`Unable to determine Dialect ...`)**  
  This is a side‑effect of failing to connect to the DB. Fix the connection first.

- **Wipe local schema quickly (dev‑only):**
  ```sql
  DROP SCHEMA public CASCADE;
  CREATE SCHEMA public;
  ```

---

## License
This project is open source.
```
MIT License © {2025} {ALi BD}
```
