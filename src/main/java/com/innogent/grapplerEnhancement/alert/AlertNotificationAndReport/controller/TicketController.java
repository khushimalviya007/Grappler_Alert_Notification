package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controller;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Ticket;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Operation(summary = "Get a Ticket by TicketId", description = "Returns a Ticket as per the TicketId")
    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicket(@PathVariable("ticketId") Long ticketId) {
        try {
            logger.info("Attempting to retrieve ticket with ID: " + ticketId);

            Optional<Ticket> optionalTicket = ticketService.getTicket(ticketId);
            if (optionalTicket.isPresent()) {
                Ticket ticket = optionalTicket.get();
                logger.info("Successfully retrieved ticket with ID: " + ticketId);
                return ResponseEntity.ok(ticket);
            } else {
                logger.warn("No ticket found with ID: " + ticketId);
                String errorMessage = "Ticket with ID " + ticketId + " not found.";
                return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error occurred while retrieving ticket with ID " + ticketId + ": " + e.getMessage(), e);
            return new ResponseEntity("An error occurred while getting the ticket. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a ticket", description = "Returns the created ticket")
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        try {
            logger.info("Attempting to create a new ticket.");

            // Add validation logic if needed
            // For example, check if required fields are present

            Ticket createdTicket = ticketService.createTicket(ticket);
            logger.info("Successfully created a new ticket with ID: " + createdTicket.getId());
            return ResponseEntity.ok(createdTicket);
        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage());
            return new ResponseEntity("A ticket with the same attributes already exists. " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while creating a ticket: " + e.getMessage());
            return new ResponseEntity("An error occurred while creating the ticket. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a ticket by TicketId", description = "Returns the deletion status")
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<String> deleteTicket(@PathVariable("ticketId") Long ticketId) {
        try {
            logger.info("Attempting to delete ticket with ID: " + ticketId);

            Optional<Ticket> optionalTicket = ticketService.getTicket(ticketId);
            if (optionalTicket.isPresent()) {
                ticketService.deleteTicket(ticketId);
                logger.info("Successfully deleted ticket with ID: " + ticketId);
                return ResponseEntity.ok("Ticket with ID " + ticketId + " has been successfully deleted.");
            }
        } catch (Exception e) {
            // Handle other exceptions (e.g., database connection issues, unexpected errors)
            String errorMessage = "An error occurred while trying to delete the ticket with ID " + ticketId + ": " + e.getMessage();
            logger.error(errorMessage, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        String errorMessage = "Ticket with ID " + ticketId + " not found.";
        logger.warn(errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @Operation(summary = "Update Ticket", description = "Partially update a ticket")
    @PatchMapping("/{ticketId}")
    public ResponseEntity<Ticket> partiallyUpdateTicket(@PathVariable("ticketId") Long ticketId, @RequestBody Ticket partialTicket) {
        try {
            logger.info("Attempting to partially update ticket with ID: " + ticketId);

            Optional<Ticket> optionalTicket = ticketService.getTicket(ticketId);
            if (optionalTicket.isPresent()) {
                Ticket updatedTicket = ticketService.partiallyUpdateTicket(optionalTicket.get(), ticketId, partialTicket);
                logger.info("Successfully partially updated ticket with ID: " + ticketId);
                return ResponseEntity.ok(updatedTicket);
            } else {
                String errorMessage = "Ticket with ID " + ticketId + " not found.";
                logger.warn(errorMessage);
                return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("An error occurred while partially updating ticket with ID " + ticketId + ": " + e.getMessage(), e);
            return new ResponseEntity("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
