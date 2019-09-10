package com.lanit.simple_rest_service.controllers.rest;

import com.lanit.simple_rest_service.services.ClearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClearController {

    @Autowired
    private ClearService clearService;

    @GetMapping("/clear")
    public void clear() {
        clearService.deleteAll();
    }

}
