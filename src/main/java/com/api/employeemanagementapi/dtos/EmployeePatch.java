package com.api.employeemanagementapi.dtos;

import com.api.employeemanagementapi.entity.Employee;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeePatch(
        @Pattern(regexp = ".*\\S.*", message = "Name cannot be blank")
        String name,

        @DecimalMin(value = "0.00", inclusive = true, message = "salary cannot be negative")
        BigDecimal salary,

        LocalDate hireDate

) {
    public void applyPartially(Employee target) {
        if (name != null) target.setName(name);
        if (salary != null) target.setSalary(salary);
        if (hireDate != null) target.setHireDate(hireDate);
    }
}
