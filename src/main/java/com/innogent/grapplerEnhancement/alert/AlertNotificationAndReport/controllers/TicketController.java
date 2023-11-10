package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controllers;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Ticket;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ApiResponse;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.TicketDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;
    Logger logger = LoggerFactory.getLogger(TicketController.class); // Corrected the logger class name

    @Operation(summary = "Create a ticket", description = "returns the created ticket")
    @PostMapping
    public ResponseEntity createTicket(@Valid @RequestBody TicketDto ticketDto){
        try {
            logger.info("Attempting to create a new ticket.");
            TicketDto ticket = ticketService.createTicket(ticketDto);
            logger.info("Successfully created a new ticket with ID: " + ticket.getId());
            return new ResponseEntity<>(new ApiResponse<>(ticket,"Successfully created a new ticket with ID: " + ticket.getId(), true), HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage(), e); // Log the exception
            return new ResponseEntity<>(new ApiResponse<>(null,e.getMessage(), false), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while creating a ticket: " + e.getMessage(), e); // Log the exception
            return new ResponseEntity<>(new ApiResponse<>(null,e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a ticket by ticketId", description = "Returns the deletion status")
    @DeleteMapping("/{ticketId}")
    public ResponseEntity deleteTicket(@PathVariable("ticketId") Long ticketId){
        try {
            logger.info("Deleted ticket with ID: " + ticketId);
            ticketService.deleteTicket(ticketId);
            return new ResponseEntity<>(new ApiResponse<>("Ticket is deleted of ticketId " + ticketId,"Ticket is deleted of ticketId " + ticketId, true), HttpStatus.OK);

        } catch (ResourceNotFoundException e) {
            logger.error("Resource not found: " + e.getMessage(), e);
            return new ResponseEntity<>(new ApiResponse<>(null,e.getMessage(), false), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("An error occurred while deleting a ticket: " + e.getMessage(), e);
            return new ResponseEntity<>(new ApiResponse<>(null,e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a ticket by ticketId", description = "Returns the updated ticket object status")
    @PatchMapping("/{ticketId}")
    public ResponseEntity updateTicket(@PathVariable("ticketId") Long ticketId, @RequestBody TicketDto ticketDto){
        try {
            logger.info("Attempting to update a ticket.");
            TicketDto updatedTicketDto = ticketService.updateTicket(ticketId, ticketDto);
            logger.info("Successfully updated a ticket with ID: " + updatedTicketDto.getId());
            return new ResponseEntity<>(new ApiResponse<>(updatedTicketDto,"Successfully updated a ticket with ID: " + updatedTicketDto.getId(), true), HttpStatus.OK);

        } catch (ResourceNotFoundException e) {
            logger.error("Resource not found: " + e.getMessage(), e);
            return new ResponseEntity<>(new ApiResponse<>(null,e.getMessage(), false), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage(), e);
            return new ResponseEntity<>(new ApiResponse<>(null,e.getMessage(), false), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while updating a ticket: " + e.getMessage(), e);
            return new ResponseEntity<>(new ApiResponse<>(null,e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a Ticket by TicketId", description = "Returns a Ticket as per the TicketId")
    @GetMapping("/{ticketId}")
    public ResponseEntity getTicketByTicketId(@PathVariable("ticketId") Long ticketId){
        try {
            TicketDto ticketDto = ticketService.getTicketByTicketId(ticketId);
            return new ResponseEntity<>(new ApiResponse<>(ticketDto,"Successfully found a ticket with ID: " + ticketId, true), HttpStatus.OK);

        } catch (ResourceNotFoundException e) {
            logger.error("Resource not found: " + e.getMessage(), e);
            return new ResponseEntity<>(new ApiResponse<>(null,e.getMessage(), false), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving a ticket: " + e.getMessage(), e);
            return new ResponseEntity<>(new ApiResponse<>(null,e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get all Tickets", description = "Returns all the Tickets")
    @GetMapping
    public ResponseEntity getAllTickets(){
        try {
            List<TicketDto> allTicketDto = ticketService.getAllTicket();
            if(allTicketDto.isEmpty())
                return new ResponseEntity<>(new ApiResponse<>(null,"No Tickets found", false), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(new ApiResponse<>(allTicketDto,"Found all Ticket", true), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("An error occurred while retrieving all tickets: " + e.getMessage(), e);
            return new ResponseEntity<>(new ApiResponse<>(null,e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
