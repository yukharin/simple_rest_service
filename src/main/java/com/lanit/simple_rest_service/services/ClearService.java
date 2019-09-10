package com.lanit.simple_rest_service.services;

import com.lanit.simple_rest_service.repositories.CarRepository;
import com.lanit.simple_rest_service.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClearService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private PersonRepository personRepository;

    @Transactional(rollbackFor = Exception.class)
    public void deleteAll() {
        carRepository.deleteAll();
    }

}
