package com.api.productmanagementapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name must not be blank")
    @Size(max = 100, message = "name must be at most 100 characters")
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @NotNull(message = "price must not be null")
    @PositiveOrZero(message = "price must be >= 0")
    @Digits(integer = 10, fraction = 2, message = "price must have max 10 digits and 2 decimals")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @NotNull(message = "quantity must not be null")
    @Min(value = 0, message = "quantity must be >= 0")
    @Max(value = 1_000_000, message = "quantity must be <= 1,000,000")
    @Column(nullable = false)
    private Integer quantity;

    public Employee() {}
}


