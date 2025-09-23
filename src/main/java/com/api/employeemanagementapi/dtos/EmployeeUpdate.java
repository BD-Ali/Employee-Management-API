package com.api.employeemanagementapi.dtos;

import com.api.employeemanagementapi.entity.Employee;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record EmployeeUpdate(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.00", inclusive = true, message = "Price cannot be negative")
        BigDecimal price,

        @NotNull(message = "Quantity is required")
        @Min(value = 0, message = "Quantity cannot be negative")
        Integer quantity
) {
    public void applyTo(Employee target) {
        target.setName(name);
        target.setPrice(price);
        target.setQuantity(quantity);
    }
}
