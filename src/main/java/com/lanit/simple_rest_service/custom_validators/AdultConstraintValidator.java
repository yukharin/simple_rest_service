package com.lanit.simple_rest_service.custom_validators;

import com.lanit.simple_rest_service.entities.Person;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class AdultConstraintValidator implements ConstraintValidator<Adult, Person> {

    private static final int ADULT_AGE = 18;

    @Override
    public boolean isValid(Person person, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate date = person.getBirthdate();
        return (Period.between(date, LocalDate.now())).getYears() >= ADULT_AGE;
    }
}
