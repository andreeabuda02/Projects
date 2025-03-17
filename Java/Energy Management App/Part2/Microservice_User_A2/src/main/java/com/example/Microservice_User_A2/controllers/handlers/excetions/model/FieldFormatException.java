package com.example.Microservice_User_A2.controllers.handlers.excetions.model;

import org.springframework.http.HttpStatus;

public class FieldFormatException extends AbstractException{
    private static final String MESSAGE = "Invalid field format!";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_ACCEPTABLE;

    public FieldFormatException(String resource) {
        super(MESSAGE, resource, HTTP_STATUS);
    }

}
