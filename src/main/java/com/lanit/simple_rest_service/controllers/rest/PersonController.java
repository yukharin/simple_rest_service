package com.lanit.simple_rest_service.controllers.rest;

import com.lanit.simple_rest_service.entities.Person;
import com.lanit.simple_rest_service.services.PersonService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/person")
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(@NonNull final PersonService personService) {
        this.personService = personService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void savePerson(@RequestBody Person person) {
        personService.createPerson(person);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Person> persons() {
        return personService.persons();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id, HttpServletRequest request) {
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
