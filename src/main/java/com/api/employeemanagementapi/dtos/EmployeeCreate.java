package com.api.employeemanagementapi.dtos;

import com.api.employeemanagementapi.entity.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record EmployeeCreate(
        @NotBlank(message = "name must not be blank")
        @Size(max = 100, message = "name must be at most 100 characters")
        @Column(nullable = false, length = 100)
        String FirstName,

        @NotBlank(message = "name must not be blank")
        @Size(max = 100, message = "name must be at most 100 characters")
        @Column(nullable = false, length = 100)
        String LastName,

        @NotNull(message = "salary must not be null")
        @PositiveOrZero(message = "salary must be >= 0")
        @Digits(integer = 10, fraction = 2, message = "salary must have max 10 digits and 2 decimals")
        @Column(nullable = false, precision = 12, scale = 2)
        BigDecimal salary,

        @NotNull(message = "hireDate must not be null")
        @Column(nullable = false)
        LocalDate hireDate,

        @NotBlank(message = "email must not be blank")
        @Email(message = "email must be a valid email address")
        @Size(max = 100, message = "email must be at most 100 characters")
        @Column(nullable = false, unique = true, length = 100)
        String email,

        @NotBlank(message = "phoneNumber must not be blank")
        @Pattern(regexp = "\\+?[0-9]{7,15}", message = "phoneNumber must be a valid phone number")
        @Size(max = 15, message = "phoneNumber must be at most 15 characters")
        @Column(nullable = false, unique = true, length = 15)
        String phoneNumber,

        @NotBlank(message = "position must not be blank")
        @Size(max = 50, message = "position must be at most 50 characters")
        @Column(nullable = false, length = 50)
        String position
) {
    public Employee toEntity() {
        Employee p = new Employee();
        p.setFirstName(FirstName);
        p.setLastName(LastName);
        p.setSalary(salary);
        p.setHireDate(hireDate);
        p.setEmail(email);
        p.setPhoneNumber(phoneNumber);
        p.setPosition(position);
        return p;
    }
}
