package com.lanit.simple_rest_service.controllers.rest;

import com.lanit.simple_rest_service.entities.Person;
import com.lanit.simple_rest_service.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/person")
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void savePerson(@RequestBody Person person) {
        System.err.println("PERSON: " + person);
        personService.createPerson(person);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Person> persons() {
        return personService.persons();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.deleteById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/id")
    public void updatePerson(@PathVariable Long id, @RequestBody Person person) {
        personService.updatePerson(id, person);
    }

}
