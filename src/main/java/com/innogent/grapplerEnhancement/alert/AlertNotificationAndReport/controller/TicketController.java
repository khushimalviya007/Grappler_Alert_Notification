package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controller;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.Exception.CustomException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Ticket;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.TicketRepositary;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    TicketRepositary ticketRepositary;

    Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Operation(summary = "Create a ticket", description = "returns the created ticket")
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        try {
            logger.info("Received a request to create a ticket: {}", ticket);
            ResponseEntity<Ticket> response = ticketService.createTicket(ticket);
            logger.info("Ticket created successfully");
            return response;
        } catch (Exception ex) {
            logger.error("An error occurred while trying to creating a ticket");
            throw ex;
        }
    }

//    @Operation(summary = "Delete a ticket by ticketId", description = "Returns the deletion status")
//    @DeleteMapping("/{ticketId}")
//    public ResponseEntity<String> deleteTicket(@PathVariable("ticketId") Long ticketId) {
//        try {
//            logger.info("Received a request to delete a ticket with ID: {}", ticketId);
//            Optional<Ticket> optionalTicket = ticketRepositary.findById(ticketId);
//            if (optionalTicket.isPresent()) {
//                ticketRepositary.deleteById(ticketId);
//                logger.info("Ticket with ID {} has been deleted successfully", ticketId);
//                return ResponseEntity.ok("Ticket with ID " + ticketId + " has been successfully deleted.");
//            } else {
//                throw new CustomException("Ticket with ID " + ticketId + " not found.", false);
//            }
//        } catch (CustomException c) {
//            return ResponseEntity.status().body(c.getMessage());
//        } catch (Exception e) {
//            logger.error("An error occurred while trying to delete the ticket with ID {}: {}", ticketId, e.getMessage());
//            throw e;
//        }
//    }

//    @Operation(summary = "update a ticket by ticketId", description = "Returns the updated ticket object status")
//    @PatchMapping("/{ticketId}")
//    public ResponseEntity<Ticket> updateTicket(@PathVariable("projectId")Long ticketId, @RequestBody Ticket ticket){
//        return ticketService.updateTicket(ticketId,ticket);
//    }

    @Operation(summary = "Get a Ticket by TicketId", description = "Returns a Ticket as per the TicketId")
    @GetMapping("/{ticketId}")
    public ResponseEntity<Object> getTicketByTicketId(@PathVariable("ticketId")Long ticketId){
        return ticketService.getTicketByTicketId(ticketId);
    }


//    @Operation(summary = "Daily Update", description = "Returns the daily Update")
//    @GetMapping("/ticket/userUpdate/{userId}")
//    public String dailyUpdate(@PathVariable("userId")Long userId){
//        return "this is my daily update";
//    }

}
