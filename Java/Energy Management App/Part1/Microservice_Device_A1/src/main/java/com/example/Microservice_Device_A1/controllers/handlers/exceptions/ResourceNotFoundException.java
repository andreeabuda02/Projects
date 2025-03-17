package com.example.Microservice_Device_A1.controllers.handlers.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AbstractException {
    private static final String MESSAGE = "Resource not found!";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    public ResourceNotFoundException(String resource) {
        super(MESSAGE, resource, HTTP_STATUS);
    }
}
