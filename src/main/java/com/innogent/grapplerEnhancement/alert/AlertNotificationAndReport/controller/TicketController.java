package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TicketController {

    @Operation(summary = "Get a Ticket by TicketId", description = "Returns a Ticket as per the TicketId")
    @GetMapping("/ticket/{ticketId}")
    public String getTicket(@PathVariable("ticketId")Long ticketId){
        return "this is your ticket of id : "+ticketId;
    }

//    @Operation(summary = "Daily Update", description = "Returns the daily Update")
//    @GetMapping("/ticket/userUpdate/{userId}")
//    public String dailyUpdate(@PathVariable("userId")Long userId){
//        return "this is my daily update";
//    }

}
