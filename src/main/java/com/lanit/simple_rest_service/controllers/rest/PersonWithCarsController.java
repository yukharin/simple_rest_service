package com.lanit.simple_rest_service.controllers.rest;

import com.lanit.simple_rest_service.entities.PersonWithCars;
import com.lanit.simple_rest_service.services.PersonWithCarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personwithcars")
public class PersonWithCarsController {

    @Autowired
    private PersonWithCarsService personWithCarsService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public PersonWithCars getPersonWithCar(@PathVariable long id) {
        return personWithCarsService.getPerson(id);
    }

}
