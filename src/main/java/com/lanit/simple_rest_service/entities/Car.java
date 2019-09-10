package com.lanit.simple_rest_service.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Data
@Entity
@Table(name = "cars")
public class Car {

    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @NotNull
    @Column(name = "model", nullable = false)
    private String model;

    @Positive
    @NotNull
    @Column(name = "horsepower", nullable = false)
    private int horsepower;


    @NotNull
    @JoinColumn(name = "id_person", referencedColumnName = "id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Person owner;

}
