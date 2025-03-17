package com.example.Microservice_Consumer_A2.dtos.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnergyUsageValidator.class)
public @interface ValidEnergyUsage {
    String message() default "Invalid energy usage value. It must be positive.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
