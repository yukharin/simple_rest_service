package com.lanit.simple_rest_service.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CarDto {

    private Long id;

    private String model;

    private Integer horsepower;

    private Long ownerId;

    public CarDto(Car car) {
        this.id = car.getId();
        this.model = car.getModel();
        this.horsepower = car.getHorsepower();
        this.ownerId = car.getOwner().getId();
    }

}
