package com.example.Microservice_Device_A1.controllers.handlers.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractException extends RuntimeException {
    private final String resource;
    private final HttpStatus status;

    public AbstractException(String message, String resource, HttpStatus status) {
        super(message);
        this.resource = resource;
        this.status = status;
    }

}
