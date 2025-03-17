package com.example.Microservice_Consumer_A3.configurations.handlers.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource) {
        super(resource);
    }
}
