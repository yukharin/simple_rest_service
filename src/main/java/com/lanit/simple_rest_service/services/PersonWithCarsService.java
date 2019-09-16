package com.lanit.simple_rest_service.services;

import com.lanit.simple_rest_service.entities.Car;
import com.lanit.simple_rest_service.entities.CarDto;
import com.lanit.simple_rest_service.entities.Person;
import com.lanit.simple_rest_service.entities.PersonWithCars;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonWithCarsService {

    private final PersonService personService;
    private final CarService carService;

    @Autowired
    public PersonWithCarsService(@NonNull final PersonService personService,
                                 @NonNull final CarService carService) {
        this.personService = personService;
        this.carService = carService;
    }

    @Transactional(rollbackFor = Exception.class)
    public PersonWithCars getPerson(long id) {
        PersonWithCars personWithCars = new PersonWithCars();
        List<Car> cars = carService.findByOwnerId(id);
        List<CarDto> carDtos = new ArrayList<>();
        for (Car car : cars) {
            CarDto carDto = new CarDto();
            carDto.setId(car.getId());
            carDto.setHorsepower(car.getHorsepower());
            carDto.setModel(car.getModel());
            carDto.setOwnerId(car.getOwner().getId());
            carDtos.add(carDto);
        }
        personWithCars.setCars(carDtos);
        Person person = personService.getPersonById(id);
        personWithCars.setId(person.getId());
        personWithCars.setBirthdate(person.getBirthDate());
        personWithCars.setName(person.getName());
        return personWithCars;
    }


}
