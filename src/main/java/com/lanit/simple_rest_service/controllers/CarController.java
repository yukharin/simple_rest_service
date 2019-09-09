package com.lanit.simple_rest_service.controllers;

import com.lanit.simple_rest_service.entities.Car;
import com.lanit.simple_rest_service.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/car")
@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping
    public void saveCar(Car car) {
        carService.createCar(car);
    }

    @GetMapping
    public List<Car> getCars() {
        System.err.println("I AM HERE!");
        return carService.cars();
    }

    @GetMapping("/{id}")
    public Car getCar(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteById(id);
    }

    @PutMapping("/{id}")
    public void updateCar(@PathVariable Long id, @RequestBody Car car) {
        carService.updateCar(id, car);
    }


}
