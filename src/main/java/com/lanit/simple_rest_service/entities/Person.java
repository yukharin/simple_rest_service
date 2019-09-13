package com.lanit.simple_rest_service.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lanit.simple_rest_service.deserializer.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @NotNull(message = "{person.id.notnull}")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "{person.name.notnull}")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "{person.birthDate.notnull}")
    @Past(message = "{person.birthDate.past}")
    @Column(name = "birthdate", nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate birthDate;

}
