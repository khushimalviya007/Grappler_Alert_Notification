package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controller;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Alert;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AlertController {

//  Retrieve a list of alerts for a User by UserId
    @Operation(summary = "Get a List of Alert by UserID", description = "Returns a List of Alert as per the UserId")
    @GetMapping("/alert/user/{userId}")
//  public ResponseEntity<List<Alert>> getUserAlerts(@PathVariable("userId")Long userId)
    public String getUserAlert(@PathVariable("userId")Long userId){
        return "We are getting alert by userId : "+userId;
    }

    //fetching alert by ticketId

    //  Retrieve a list of Alert for a Ticket by TicketId
//  public ResponseEntity<List<Alert>> getTicketAlerts(@PathVariable("ticketId")Long ticketId)
    @Operation(summary = "Get a List of Alert as per TicketId", description = "Returns a List of Alerts  as per the TicketId")
    @GetMapping("/alert/ticket/{ticketId}")
    public String getTicketAlert(@PathVariable("ticketId")Long ticketId){
        return "We are getting alert by ticketId : "+ticketId;
    }



    //Create a new alert
    //    POST /api/alerts:             Create a new alert.
//  public Notification createAlert( @RequestBody Alert Alert)
    @Operation(summary = "To create an Alert", description = "Returns the created Alert")
    @PostMapping("/alert/create")
    public Alert createAlert(Alert alert){
        return alert;
    }


    @Operation(summary = "to Save Response of Alert", description = "Returns Saved Alert with Response")
    @PutMapping("alert/response/{alertId}")
    public String saveAlertResponse(@PathVariable("alertId")Long alertId, String response){
        return "alert response is saved";
    }





}
