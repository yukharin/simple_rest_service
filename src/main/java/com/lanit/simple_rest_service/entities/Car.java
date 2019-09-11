package com.lanit.simple_rest_service.entities;

import com.lanit.simple_rest_service.custom_validators.Adult;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;


@Data
@Entity
@Table(name = "cars")
public class Car {

    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z]+)([\\-])([a-zA-Z0-9]+)$")
    @Column(name = "model", nullable = false)
    private String model;

    @Positive
    @NotNull
    @Column(name = "horsepower", nullable = false)
    private int horsepower;

    @Adult
    @NotNull
    @JoinColumn(name = "id_person", referencedColumnName = "id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Person owner;

}
