package com.lanit.simple_rest_service.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
public class ValidatorConfig {

    @Bean
    public Validator validator() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        return vf.getValidator();
    }

}
