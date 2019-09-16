package com.lanit.simple_rest_service.controllers.rest;

import com.lanit.simple_rest_service.entities.Car;
import com.lanit.simple_rest_service.entities.CarDto;
import com.lanit.simple_rest_service.services.CarService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/car")
@RestController
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(@NonNull final CarService carService) {
        this.carService = carService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void saveCar(@RequestBody CarDto car) {
        carService.createCar(car);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Iterable<Car> getCars() {
        return carService.cars();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Car getCar(@PathVariable long id) {
        return carService.getCarById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable long id) {
        carService.deleteById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void updateCar(@PathVariable long id, @RequestBody CarDto carDto) {
        carService.updateCar(id, carDto);
    }


}
