package com.api.employeemanagementapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "salary", nullable = false)
    private BigDecimal salary;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    public Employee() {}
}
