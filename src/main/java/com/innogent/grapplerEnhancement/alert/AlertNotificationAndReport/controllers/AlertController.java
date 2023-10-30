package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controllers;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Alert;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.AlertDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ApiResponse;
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

@RestController
@RequestMapping("/alerts")
public class AlertController {

    Logger logger= LoggerFactory.getLogger(AlertController.class);

    @Autowired
    AlertSevice alertSevice;


//    @Operation(summary = "To create an Alert", description = "Returns the created Alert")
//    @PostMapping
//    public ResponseEntity<AlertDto> createAlert(@Valid @RequestBody AlertDto alertDto){
//        try {
//            logger.info("Attempting to create a new ticket.");
//            AlertDto alertDto2 = alertSevice.createAlert(alertDto);
//            logger.info("Successfully created a new alert");
//            return new ResponseEntity(alertDto2,HttpStatus.CREATED);
//        } catch (DataIntegrityViolationException e) {
//            logger.error("Database error: " + e.getMessage());
//            return new ResponseEntity( new ApiResponse(e.getMessage(),false), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            logger.error("An error occurred while creating a ticket: " + e.getMessage());
//            return new ResponseEntity( new ApiResponse(e.getMessage(),false), HttpStatus.BAD_REQUEST);
//        }
//    }
//
////  Retrieve a list of alerts for a User by UserId
//    @Operation(summary = "Get a List of Alert by UserID", description = "Returns a List of Alert as per the UserId")
//    @GetMapping("/users/{userId}")
////  public ResponseEntity<List<Alert>> getUserAlerts(@PathVariable("userId")Long userId)
//    public ResponseEntity<List<Alert>> getUserAlert(@PathVariable("userId")Long userId){
//        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
//    }
//
//    //fetching alert by ticketId
//
//    //  Retrieve a list of Alert for a Ticket by TicketId
////  public ResponseEntity<List<Alert>> getTicketAlerts(@PathVariable("ticketId")Long ticketId)
//    @Operation(summary = "Get a List of Alert as per TicketId", description = "Returns a List of Alerts  as per the TicketId")
//    @GetMapping("/tickets/{ticketId}")
//    public ResponseEntity<List<Alert>> getTicketAlert(@PathVariable("ticketId")Long ticketId){
//        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
//    }
//
//
//
//    //Create a new alert
//    //    POST /api/alerts:             Create a new alert.
////  public Notification createAlert( @RequestBody Alert Alert)
//
//
//
//    @Operation(summary = "to Save Response of Alert", description = "Returns Saved Alert with Response")
//    @PatchMapping("/{alertId}")
//    public ResponseEntity<String> saveAlertResponse(@PathVariable("alertId")Long alertId, String response){
//        return ResponseEntity.ok("alert response is saved");
//    }





}
