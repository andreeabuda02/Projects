package com.example.Microservice_User_A1.dtos.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator {
    private static final String EMAIL_REGEX = "[a-zA-Z0-9_\\-]+@([a-zA-Z0-9]+).+(com|ro|es|org)";

    public static boolean isValid(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

}
