package com.lanit.simple_rest_service.controllers.rest;

import com.lanit.simple_rest_service.entities.Statistics;
import com.lanit.simple_rest_service.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatiscticsController {

    @Autowired
    private StatisticsService statisticsService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Statistics getStatistics() {
        return statisticsService.getStatistics();
    }
}
