package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controllers;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Alert;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.AlertType;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Notification;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.*;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.AlertSevice;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/alerts")
public class AlertController {

    Logger logger = LoggerFactory.getLogger(AlertController.class);

    @Autowired
    AlertSevice alertSevice;


    @Operation(summary = "Create a Alert", description = "Returns created Alert")
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createAlert(@Valid @RequestBody AlertDto alertDto) {
        try {
            logger.info("Attempting to create a new alert.");
            AlertDtoInfo alertDtoInfo = alertSevice.createAlert(alertDto);
            logger.info("Successfully created a new alert");
            return new ResponseEntity<>(new ApiResponse<>(alertDtoInfo, "alert is created sucessfully", true), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage());
            return new ResponseEntity<>(new ApiResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while creating a alert: " + e.getMessage());
            return new ResponseEntity<>(new ApiResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }

    }



    @Operation(summary = "to Save Response of Alert", description = "Returns Saved Alert with Response")
    @PatchMapping("/{alertId}")
    public ResponseEntity<ApiResponse<?>> saveResponse(@PathVariable("alertId") Long alertId, @Valid @RequestBody AlertResponse alertResponse) {
        try{
            logger.info("Attempting to save response");
            AlertDtoInfo alertDtoInfo =alertSevice.saveResponse(alertId,alertResponse);
            logger.info("Successfully saved response of alert");
            return new ResponseEntity<>(new ApiResponse<>(alertDtoInfo, "alert response is saved", true), HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            logger.warn(e.getMessage());
            throw e;
        }
        catch (Exception e){
            logger.error("A problem occurred while saving response of alert: " + e.getMessage());
            return new ResponseEntity<>(new ApiResponse<>(null, e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAlertsByUserId(@PathVariable("userId") Long userId) {
        try{
            logger.info("Attempting to get user alerts");
            List<AlertDtoInfo> alertList = alertSevice.getUserAlerts(userId);
            logger.info("Successfully got alert list ");
            return new ResponseEntity<>(new ApiResponse<>(alertList,"found all alerts",true),HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            logger.warn(e.getMessage());
            throw e;
        }
        catch (Exception e){
            logger.error("A problem occurred while getting lis of notification : " + e.getMessage());
            return new ResponseEntity<>(new ApiResponse<>(null, e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}