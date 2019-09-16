package com.lanit.simple_rest_service.services;

import com.lanit.simple_rest_service.entities.Statistics;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatisticsService {

    private final CarService carService;
    private final PersonService personService;

    @Autowired
    public StatisticsService(@NonNull final CarService carService,
                             @NonNull final PersonService personService) {
        this.carService = carService;
        this.personService = personService;
    }

    @Transactional(rollbackFor = Exception.class)
    public Statistics getStatistics() {
        Statistics statistics = new Statistics();
        statistics.setCarcount(carService.countCars());
        statistics.setPersoncount(personService.countPersons());
        statistics.setUniquevendorcount(carService.countUniqueNames());
        return statistics;
    }

}
