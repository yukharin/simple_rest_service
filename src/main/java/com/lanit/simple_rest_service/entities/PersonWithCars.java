package com.lanit.simple_rest_service.entities;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PersonWithCars {

    private long id;

    private String name;

    private Date birthDate;

    private List<Car> cars;
}
