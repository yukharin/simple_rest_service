package com.lanit.simple_rest_service.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PersonWithCars {

    private long id;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate birthdate;

    private List<CarDto> cars;
}
