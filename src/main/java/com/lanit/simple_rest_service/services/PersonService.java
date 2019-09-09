package com.lanit.simple_rest_service.services;

import com.lanit.simple_rest_service.entities.Person;
import com.lanit.simple_rest_service.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Transactional(rollbackFor = Exception.class)
    public Person getPersonById(long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createPerson(@Valid Person person) {
        boolean exists = personRepository.findById(person.getId()).isPresent();
        if (exists) {
            throw new EntityExistsException("This entity already exists.");
        } else {
            personRepository.save(person);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePerson(Long id, @Valid Person person) {
        boolean exists = personRepository.findById(id).isPresent();
        if (exists) {
            personRepository.save(person);
        } else {
            throw new EntityNotFoundException("There is no entity to update.");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        personRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePerson(@Valid Person person) {
        personRepository.delete(person);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Person> persons() {
        return personRepository.findAll();
    }

}
