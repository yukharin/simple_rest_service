package com.lanit.simple_rest_service.services;

import com.lanit.simple_rest_service.entities.Person;
import com.lanit.simple_rest_service.repositories.PersonRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final Validator validator;

    @Autowired
    public PersonService(@NonNull final PersonRepository personRepository,
                         @NonNull final Validator validator) {
        this.personRepository = personRepository;
        this.validator = validator;
    }


    @Transactional(rollbackFor = Exception.class)
    public Person getPersonById(long id) {
        return personRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no such entity"));
    }

    @Transactional(rollbackFor = Exception.class)
    public void createPerson(@NonNull Person person) {
        personRepository.findById(person.getId()).ifPresent(e -> {
            throw new EntityExistsException("This entity already exists.");
        });
        validator.validate(person);
        personRepository.save(person);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePerson(long id, @NonNull Person person) {
        personRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no entity to update."));
        validator.validate(person);
        personRepository.save(person);

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        personRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no entity to update."));
        personRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Person> persons() {
        return personRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public long countPersons() {
        return personRepository.count();
    }

}
