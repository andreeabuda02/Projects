package com.example.Microservice_Consumer_A3.configurations.handlers;

import com.example.Microservice_Consumer_A3.configurations.handlers.exceptions.EntityValidationException;
import com.example.Microservice_Consumer_A3.configurations.handlers.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(EntityValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationError(EntityValidationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", ex.getMessage());
        response.put("validationErrors", ex.getValidationErrors());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "An unexpected error occurred.");
        response.put("details", ex.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }
}
