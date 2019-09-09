package com.lanit.simple_rest_service.entities;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "persons")
public class Person {

    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Past
    @Column(name = "birthdate", nullable = false)
    private Date birthDate;

    @OneToMany
    private List<Car> cars;

}
