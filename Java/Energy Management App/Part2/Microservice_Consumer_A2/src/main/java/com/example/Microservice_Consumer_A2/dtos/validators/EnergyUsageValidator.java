package com.example.Microservice_Consumer_A2.dtos.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnergyUsageValidator implements ConstraintValidator<ValidEnergyUsage, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != null && value > 0;
    }
}
