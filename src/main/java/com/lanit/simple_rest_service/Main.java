package com.lanit.simple_rest_service;

import com.lanit.simple_rest_service.entities.Car;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;

public class Main {

    private static ValidatorFactory vf = Validation.byDefaultProvider().configure().buildValidatorFactory();
    private static Validator validator = vf.getValidator();

    public static void main(String[] args) {
        Car car = null;
        Optional<Car> optional = Optional.ofNullable(car);
        System.out.println(optional.orElseThrow(IllegalArgumentException::new));
    }
}
