package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controller;

import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateTimeKeyDeserializer;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Ticket;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.TicketRepositary;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;


    @Operation(summary = "Create a ticket", description = "returns the created ticket")
    @PostMapping
    public ResponseEntity<Ticket> createTicket(Ticket ticket){
        return ticketService.createTicket(ticket);
    }




    @Operation(summary = "Delete a ticket by ticketId", description = "Returns the deletion status")
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<String> deleteTicket(@PathVariable("ticketId")Long ticketId){
        return ticketService.deleteTicket(ticketId);
    }

    @Operation(summary = "update a ticket by ticketId", description = "Returns the updated ticket object status")
    @PatchMapping("/{ticketId}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable("projectId")Long ticketId, @RequestBody Ticket ticket){
        return ticketService.updateTicket(ticketId,ticket);
    }

//    @Operation(summary = "Get a Ticket by TicketId", description = "Returns a Ticket as per the TicketId")
//    @GetMapping("/{ticketId}")
//    public ResponseEntity<Object> getTicketByTicketId(@PathVariable("ticketId")Long ticketId){
//        return ticketService.getTicketByTicketId(ticketId);
//    }



//    @Operation(summary = "Get a list of Tickets by userID ", description = "Returns a List of Ticket as per the userId")
//    @GetMapping("{userId}")
//    public ResponseEntity<List<Ticket>> getTicketByUserId(@PathVariable("userId")Long userId){
//        return ticketService.getTicketByUserId(userId);
//    }

//    @Operation(summary = "Daily Update", description = "Returns the daily Update")
//    @GetMapping("/ticket/userUpdate/{userId}")
//    public String dailyUpdate(@PathVariable("userId")Long userId){
//        return "this is my daily update";
//    }

}
