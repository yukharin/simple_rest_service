package com.lanit.simple_rest_service.services;

import com.lanit.simple_rest_service.entities.Car;
import com.lanit.simple_rest_service.entities.Person;
import com.lanit.simple_rest_service.repositories.CarRepository;
import com.lanit.simple_rest_service.repositories.PersonRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;

@Service
public class CarService {

    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = factory.getValidator();

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private PersonRepository personRepository;

    @Transactional(rollbackFor = Exception.class)
    public Car getCarById(long id) {
        return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no such entity"));
    }


    @Transactional(rollbackFor = Exception.class)
    public void createCar(@NonNull Car car) {
        carRepository.findById(car.getId()).ifPresent(e -> {
            throw new EntityExistsException("Entity with this id already exists");
        });
        Optional.ofNullable(car.getOwner()).orElseThrow(() ->
                new ValidationException("Car should have a field 'owner' non nullable"));
        Person owner = personRepository.findById(car.getOwner().getId()).orElseThrow(() ->
                new ValidationException("User with this id doesn't exist."));
        car.setOwner(owner);
        validator.validate(car);
        carRepository.save(car);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCar(long id, @NonNull Car car) {
        carRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no entity to update."));
        Optional.ofNullable(car.getOwner()).orElseThrow(() ->
                new ValidationException("Car should have a field 'owner' non nullable"));
        Person owner = personRepository.findById(car.getOwner().getId()).orElseThrow(() ->
                new ValidationException("User with this id doesn't exist."));
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
    public void deleteCar(@NonNull Car car) {
        validator.validate(car);
        carRepository.findById(car.getId()).orElseThrow(() ->
                new EntityNotFoundException("There is no entity to delete."));
        carRepository.delete(car);
    }

    @Transactional(rollbackFor = Exception.class)
    public Iterable<Car> cars() {
        return carRepository.findAll();
    }

}
