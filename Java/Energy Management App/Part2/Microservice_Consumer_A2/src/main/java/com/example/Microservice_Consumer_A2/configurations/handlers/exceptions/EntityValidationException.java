package com.example.Microservice_Consumer_A2.configurations.handlers.exceptions;

import java.util.Map;

public class EntityValidationException extends RuntimeException {
    private final Map<String, String> validationErrors;

    public EntityValidationException(String message, Map<String, String> validationErrors) {
        super(message);
        this.validationErrors = validationErrors;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }
}
