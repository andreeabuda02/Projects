package com.example.Microservice_Device_A3.controllers.handlers.exceptions;

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
