package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/employee/compensation")
    public Compensation create(@RequestBody Compensation comp) {
        LOG.debug("Received compensation create request for [{}]", comp);

        return compensationService.create(comp);
    }

    @GetMapping("/employee/{id}/compensation")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received employee compensation request for id [{}]", id);

        return compensationService.read(id);
    }
    
}