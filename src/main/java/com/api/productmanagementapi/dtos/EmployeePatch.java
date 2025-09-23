package com.api.productmanagementapi.dtos;

import com.api.productmanagementapi.entity.Employee;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

public record EmployeePatch(
        @Pattern(regexp = ".*\\S.*", message = "Name cannot be blank")
        String name,

        @DecimalMin(value = "0.00", inclusive = true, message = "Price cannot be negative")
        BigDecimal price,

        @Min(value = 0, message = "Quantity cannot be negative")
        Integer quantity
) {
    public void applyPartially(Employee target) {
        if (name != null) target.setName(name);
        if (price != null) target.setPrice(price);
        if (quantity != null) target.setQuantity(quantity);
    }
}
