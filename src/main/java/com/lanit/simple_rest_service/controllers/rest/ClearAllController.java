package com.lanit.simple_rest_service.controllers.rest;

import com.lanit.simple_rest_service.repositories.CarRepository;
import com.lanit.simple_rest_service.repositories.PersonRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClearAllController {

    private final CarRepository carRepository;
    private final PersonRepository personRepository;

    @Autowired
    public ClearAllController(@NonNull final CarRepository carRepository,
                              @NonNull final PersonRepository personRepository) {
        this.personRepository = personRepository;
        this.carRepository = carRepository;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/clear")
    public void clear() {
        personRepository.deleteAll();
        carRepository.deleteAll();
    }
}
