package com.lanit.simple_rest_service;

import com.lanit.simple_rest_service.entities.Person;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;

public class Main {

    private static ValidatorFactory vf = Validation.byDefaultProvider().configure().buildValidatorFactory();
    private static Validator validator = vf.getValidator();

    public static void main(String[] args) {
        Person person = new Person(-1L, "Kevin", LocalDate.of(1994, 7, 15));
        validator.validate(person);
    }
}
