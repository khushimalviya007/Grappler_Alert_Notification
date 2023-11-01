package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controllers;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.TriggerOptions;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ApiResponse;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.TriggerOptionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/triggerOptions")
public class TriggerOptionController {
    private static final Logger logger = LoggerFactory.getLogger(TriggerOptionController.class);

    @Autowired
    private TriggerOptionsService triggerOptionsService;

    @GetMapping
    public ResponseEntity getAll() {
        try {
            logger.info("Attempting to retrieve all trigger options.");
            List<TriggerOptions> allTriggers = this.triggerOptionsService.getAllTriggers();

            if (allTriggers == null || allTriggers.isEmpty()) {
                logger.warn("No trigger options found.");
                return new ResponseEntity<>(new ApiResponse<>("No data available","No data available", false), HttpStatus.NOT_FOUND);

            }

            logger.info("Successfully retrieved all trigger options.");
            return new ResponseEntity<>(new ApiResponse<>(allTriggers,"Successfully retrieved all trigger options.", true), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("An error occurred while retrieving trigger options: " + e.getMessage(), e);
            return new ResponseEntity<>(new ApiResponse<>(null,e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
