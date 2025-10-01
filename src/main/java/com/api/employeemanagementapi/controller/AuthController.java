package com.api.employeemanagementapi.controller;

import com.api.employeemanagementapi.dtos.SignupRequest;
import com.api.employeemanagementapi.service.AuthService;
import com.api.employeemanagementapi.service.UserAccountService;
import com.api.employeemanagementapi.shared.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserAccountService userAccountService;

    public AuthController(AuthService authService, UserAccountService userAccountService) {
        this.authService = authService;
        this.userAccountService = userAccountService;
    }

    @PostMapping("/signup")
    public ResponseEntity<GlobalResponse<String>> signup(@Valid @RequestBody SignupRequest signupRequest) {
        // Will be implemented when user registration flow is ready
        return new ResponseEntity<>(new GlobalResponse<>("signed up"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<String>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        if (username == null || password == null) {
            return new ResponseEntity<>(new GlobalResponse<>("Username and password are required"),
                HttpStatus.BAD_REQUEST);
        }

        boolean isAuthenticated = authService.authenticate(username, password);
        if (!isAuthenticated) {
            return new ResponseEntity<>(new GlobalResponse<>("Invalid credentials"),
                HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(new GlobalResponse<>("logged in"), HttpStatus.OK);
    }
}
