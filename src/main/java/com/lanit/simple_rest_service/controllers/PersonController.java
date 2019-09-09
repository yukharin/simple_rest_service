package com.lanit.simple_rest_service.controllers;

import com.lanit.simple_rest_service.entities.Person;
import com.lanit.simple_rest_service.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/person")
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public void savePerson(Person person) {
        personService.createPerson(person);
    }

    @GetMapping
    public List<Person> persons() {
        return personService.persons();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.deleteById(id);
    }

    @PutMapping("/id")
    public void updatePerson(@PathVariable Long id, @RequestBody Person person) {
        personService.updatePerson(id, person);
    }

}
