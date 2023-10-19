package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controller;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Alert;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/api")
public class AlertController {

//  Retrieve a list of alerts for a User by UserId
    @Operation(summary = "Get a List of Alert by UserID", description = "Returns a List of Alert as per the UserId")
    @GetMapping("/{userId}/alert")
//  public ResponseEntity<List<Alert>> getUserAlerts(@PathVariable("userId")Long userId)
    public ResponseEntity<List<Alert>> getUserAlert(@PathVariable("userId")Long userId){
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    //fetching alert by ticketId

    //  Retrieve a list of Alert for a Ticket by TicketId
//  public ResponseEntity<List<Alert>> getTicketAlerts(@PathVariable("ticketId")Long ticketId)
    @Operation(summary = "Get a List of Alert as per TicketId", description = "Returns a List of Alerts  as per the TicketId")
    @GetMapping("/{ticketId}/alert")
    public ResponseEntity<List<Alert>> getTicketAlert(@PathVariable("ticketId")Long ticketId){
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }



    //Create a new alert
    //    POST /api/alerts:             Create a new alert.
//  public Notification createAlert( @RequestBody Alert Alert)
    @Operation(summary = "To create an Alert", description = "Returns the created Alert")
    @PostMapping("/alert")
    public ResponseEntity<Alert> createAlert(Alert alert){
        return ResponseEntity.ok(alert);
    }


    @Operation(summary = "to Save Response of Alert", description = "Returns Saved Alert with Response")
    @PatchMapping("alert/{alertId}")
    public ResponseEntity<String> saveAlertResponse(@PathVariable("alertId")Long alertId, String response){
        return ResponseEntity.ok("alert response is saved");
    }





}
