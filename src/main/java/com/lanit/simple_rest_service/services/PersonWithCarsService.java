package com.lanit.simple_rest_service.services;

import com.lanit.simple_rest_service.entities.Car;
import com.lanit.simple_rest_service.entities.Person;
import com.lanit.simple_rest_service.entities.PersonWithCars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonWithCarsService {

    @Autowired
    private PersonService personService;

    @Autowired
    private CarService carService;

    @Transactional(rollbackFor = Exception.class)
    public PersonWithCars getPerson(long id) {
        PersonWithCars personWithCars = new PersonWithCars();
        List<Car> cars = carService.findByOwnerId(id);
        personWithCars.setCars(cars);
        Person person = personService.getPersonById(id);
        personWithCars.setId(person.getId());
        personWithCars.setBirthDate(person.getBirthdate());
        personWithCars.setName(person.getName());
        return personWithCars;
    }


}
