package com.api.employeemanagementapi.controller;
import com.api.employeemanagementapi.shared.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api.employeemanagementapi.dtos.signupRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/signup")
    public ResponseEntity<GlobalResponse<String>> signup(@RequestBody signupRequest signupRequest) {
        System.out.println(signupRequest.employeeId());
        return new ResponseEntity<>( new GlobalResponse<>("signed up"),  HttpStatus.OK);
    }
}
