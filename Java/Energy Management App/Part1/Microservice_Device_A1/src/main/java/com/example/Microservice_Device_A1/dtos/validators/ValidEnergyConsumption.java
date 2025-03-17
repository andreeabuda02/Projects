package com.example.Microservice_Device_A1.dtos.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnergyConsumptionValidator.class)
public @interface ValidEnergyConsumption {
    String message() default "Invalid energy consumption value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
