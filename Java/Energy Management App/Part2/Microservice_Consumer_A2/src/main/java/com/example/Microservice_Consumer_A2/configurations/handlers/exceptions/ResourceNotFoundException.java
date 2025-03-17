package com.example.Microservice_Consumer_A2.configurations.handlers.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource) {
        super(resource);
    }
}
