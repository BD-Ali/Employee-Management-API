package com.api.employeemanagementapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table (name = "user-account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class UserAccount {
    @NotNull(message = "Id cannot be null")
    @Id
    private UUID id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "role", nullable = false, length = 20)
    private String role = "USER";

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "employee",unique = true , nullable = false)
    private Employee employee;

}
