package com.example.Microservice_Consumer_A3.configurations.handlers.exceptions;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
