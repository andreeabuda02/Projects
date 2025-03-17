package com.example.Microservice_User_A2.dtos.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private static final String PASSWORD_REGEX =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W]).{6,20})";

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        // No initialization required in this case
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && password.length() >= 4 &&
                Pattern.compile(PASSWORD_REGEX).matcher(password).matches();
    }
}
