package com.lanit.simple_rest_service.services;

import com.lanit.simple_rest_service.entities.Car;
import com.lanit.simple_rest_service.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;

@Service
public class CarService {

    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = factory.getValidator();

    @Autowired
    private CarRepository carRepository;

    @Transactional(rollbackFor = Exception.class)
    public Car getCarById(long id) {
        return carRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no such entity"));
    }


    @Transactional(rollbackFor = Exception.class)
    public void createCar(Car car) {
        boolean exists = carRepository.findById(car.getId()).isPresent();
        if (exists) {
            throw new EntityExistsException("This entity already exists.");
        } else {
            carRepository.save(car);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCar(Long id, @Valid Car car) {
        boolean exists = carRepository.findById(id).isPresent();
        if (exists) {
            carRepository.save(car);
        } else {
            throw new EntityNotFoundException("There is no entity to update.");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        carRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCar(@Valid Car car) {
        carRepository.delete(car);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Car> cars() {
        return carRepository.findAll();
    }

}
