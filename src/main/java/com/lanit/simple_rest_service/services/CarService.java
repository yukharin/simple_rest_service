package com.lanit.simple_rest_service.services;

import com.lanit.simple_rest_service.entities.Car;
import com.lanit.simple_rest_service.entities.CarDto;
import com.lanit.simple_rest_service.entities.Person;
import com.lanit.simple_rest_service.repositories.CarRepository;
import com.lanit.simple_rest_service.repositories.PersonRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;
import java.util.List;

@Service
public class CarService {

    private final Validator validator;
    private final CarRepository carRepository;
    private final PersonRepository personRepository;

    @Autowired
    public CarService(@NonNull final CarRepository carRepository,
                      @NonNull final PersonRepository personRepository,
                      @NonNull final Validator validator) {
        this.carRepository = carRepository;
        this.personRepository = personRepository;
        this.validator = validator;
    }

    @Transactional(rollbackFor = Exception.class)
    public Car getCarById(long id) {
        return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no such entity"));
    }


    @Transactional(rollbackFor = Exception.class)
    public void createCar(@NonNull CarDto carDto) {
        carRepository.findById(carDto.getId()).ifPresent(e -> {
            throw new EntityExistsException("Entity with this id already exists");
        });
        Person owner = personRepository.findById(carDto.getOwnerId()).orElseThrow(() ->
                new EntityNotFoundException("This owner doesn't exist"));
        Car car = new Car();
        car.setModel(carDto.getModel());
        car.setHorsepower(carDto.getHorsepower());
        car.setId(carDto.getId());
        car.setOwner(owner);
        validator.validate(car);
        carRepository.save(car);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCar(long id, @NonNull CarDto carDto) {
        Car car = carRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no entity to update."));
        Person owner = personRepository.findById(carDto.getOwnerId()).orElseThrow(() ->
                new EntityNotFoundException("User with this id doesn't exist."));
        car.setHorsepower(carDto.getHorsepower());
        car.setModel(carDto.getModel());
        car.setOwner(owner);
        validator.validate(car);
        carRepository.save(car);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        carRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no entity to delete."));
        carRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Iterable<Car> cars() {
        return carRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Car> findByOwnerId(long id) {
        return carRepository.findByOwnerId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public long countCars() {
        return carRepository.count();
    }

    @Transactional(rollbackFor = Exception.class)
    public long countUniqueNames() {
        return carRepository.countUniqueNames();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteAll() {
        carRepository.deleteAll();
    }

}
