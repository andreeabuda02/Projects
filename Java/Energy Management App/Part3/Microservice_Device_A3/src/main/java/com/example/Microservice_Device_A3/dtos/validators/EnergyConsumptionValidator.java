package com.example.Microservice_Device_A3.dtos.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnergyConsumptionValidator implements ConstraintValidator<ValidEnergyConsumption, Double> {
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != null && value > 0 && value <= 1000;
    }
}
