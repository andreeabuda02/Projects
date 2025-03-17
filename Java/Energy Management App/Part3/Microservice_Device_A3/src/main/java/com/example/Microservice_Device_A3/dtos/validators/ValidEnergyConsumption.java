package com.example.Microservice_Device_A3.dtos.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnergyConsumptionValidator.class)
public @interface ValidEnergyConsumption {
    String message() default "Invalid energy consumption value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
