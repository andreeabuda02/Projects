package com.example.Microservice_Device_A1.controllers.handlers.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends AbstractException {
    private static final String MESSAGE = "Resource duplicated!";
    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public DuplicateResourceException(String resource) {
        super(MESSAGE, resource, HTTP_STATUS);
    }
}