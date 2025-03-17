package com.example.Microservice_User_A2.dtos.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<ValidName, String> {
    private static final String NAME_REGEX = "^[A-Za-z]+([ '-][A-Za-z]+)*$";

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 50;

    @Override
    public void initialize(ValidName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return name != null &&
                name.length() >= MIN_LENGTH &&
                name.length() <= MAX_LENGTH &&
                Pattern.compile(NAME_REGEX).matcher(name).matches();
    }
}
