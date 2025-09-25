package com.api.employeemanagementapi.dtos;

import com.api.employeemanagementapi.entity.Employee;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeUpdate(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "salary is required")
        @DecimalMin(value = "0.00", inclusive = true, message = "salary cannot be negative")
        BigDecimal salary,


        @NotBlank (message = "hireDate is required")
        LocalDate hireDate
) {
    public void applyTo(Employee target) {
        target.setName(name);
        target.setSalary(salary);
        target.setHireDate(hireDate);
    }
}
