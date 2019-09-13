package com.lanit.simple_rest_service.entities;

import com.lanit.simple_rest_service.custom_validators.Adult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;


@Data
@AllArgsConstructor
@Entity
@Table(name = "cars")
@NoArgsConstructor
public class Car {

    @NotNull(message = "{car.id.notnull}")
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "{car.model.notnull}")
    @Pattern(regexp = "^([a-zA-Z0-9]+)([\\-])([a-zA-Z0-9\\-]+)$", message = "{car.model.pattern}")
    @Column(name = "model", nullable = false)
    private String model;

    @Positive(message = "{car.horsepower.positive}")
    @NotNull(message = "{car.horsepower.notnull}")
    @Column(name = "horsepower", nullable = false)
    private Integer horsepower;

    @Adult(message = "{car.owner.adult}")
    @NotNull(message = "{car.owner.notnull}")
    @JoinColumn(name = "id_person", referencedColumnName = "id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Person owner;


}
