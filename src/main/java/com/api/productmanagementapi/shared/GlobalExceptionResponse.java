package com.api.productmanagementapi.shared;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionResponse {


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<GlobalResponse<?>> handleNoResourceFoundException(NoResourceFoundException ex) {
        var errors = List.of(new GlobalResponse.ErrorItem("Resource not found"));
        return new ResponseEntity<>(new GlobalResponse<>(errors), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomResponseException.class)
    public ResponseEntity<GlobalResponse<?>> handleCustomResponseException(CustomResponseException ex) {
        var errors = List.of(new GlobalResponse.ErrorItem(ex.getMessage()));
        return new ResponseEntity<>(new GlobalResponse<>(errors), HttpStatus.valueOf(ex.getCode()) );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
     public ResponseEntity<GlobalResponse<?>> handleBodyValidation(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new GlobalResponse.ErrorItem(fe.getField() + ": " + fe.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(new GlobalResponse<>(errors));
     }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GlobalResponse<?>> handleConstraint(ConstraintViolationException ex) {
        var errors = ex.getConstraintViolations().stream()
                .map(v -> new GlobalResponse.ErrorItem(v.getPropertyPath() + ": " + v.getMessage()))
                .toList();
        return ResponseEntity.badRequest().body(new GlobalResponse<>(errors));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GlobalResponse<?>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        var errors = List.of(new GlobalResponse.ErrorItem("Invalid parameter: " + ex.getName()));
        return ResponseEntity.badRequest().body(new GlobalResponse<>(errors));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GlobalResponse<?>> handleConflict(DataIntegrityViolationException ex) {
        var errors = List.of(new GlobalResponse.ErrorItem("Data conflict"));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new GlobalResponse<>(errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<?>> handleGenericException(Exception ex) {
        var errors = List.of(new GlobalResponse.ErrorItem("Internal server error"));
        return new ResponseEntity<>(new GlobalResponse<>(errors), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalResponse<Void>> handleUnreadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(new GlobalResponse<>(List.of(new GlobalResponse.ErrorItem("Invalid or missing request body"))));
    }

}
