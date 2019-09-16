package com.lanit.simple_rest_service.controllers.rest;

import com.lanit.simple_rest_service.entities.PersonWithCars;
import com.lanit.simple_rest_service.services.PersonWithCarsService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personwithcars")
public class PersonWithCarsController {

    private final PersonWithCarsService personWithCarsService;

    @Autowired
    public PersonWithCarsController(@NonNull final PersonWithCarsService personWithCarsService) {
        this.personWithCarsService = personWithCarsService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PersonWithCars getPersonWithCar(@RequestParam long personid) {
        return personWithCarsService.getPerson(personid);
    }

}
