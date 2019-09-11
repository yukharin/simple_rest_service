package com.lanit.simple_rest_service.custom_validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AdultConstraintValidator.class)
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Adult {

    String message() default "Age of person should be 18 or more";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
