package com.lanit.simple_rest_service.entities;

import lombok.Data;

@Data
public class CarDto {

    private Long id;

    private String model;

    private Integer horsepower;

    private Long ownerId;

}
