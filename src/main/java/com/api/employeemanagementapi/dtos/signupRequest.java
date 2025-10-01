package com.api.employeemanagementapi.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record signupRequest(
        @NotNull(message = "Employee ID cannot be null")
        UUID employeeId,

        @NotNull(message = "Username cannot be null")
        @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
        String username,

        @NotNull(message = "Password cannot be null")
        @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
        String password)
{
}


