package com.lanit.simple_rest_service.controllers.rest;

import com.lanit.simple_rest_service.entities.Car;
import com.lanit.simple_rest_service.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/car")
@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void saveCar(@RequestBody Car car) {
        System.err.println("CAR: " + car);
        carService.createCar(car);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Car> getCars() {
        System.err.println("I AM HERE!");
        return carService.cars();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Car getCar(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void updateCar(@PathVariable Long id, @RequestBody Car car) {
        carService.updateCar(id, car);
    }


}
