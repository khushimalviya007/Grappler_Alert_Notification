package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controllers;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Ticket;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ApiResponse;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ProjectDto;
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

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;
    Logger logger = LoggerFactory.getLogger(ProjectController.class);


    @Operation(summary = "Create a ticket", description = "returns the created ticket")
    @PostMapping
    public ResponseEntity<TicketDto> createTicket(@Valid @RequestBody TicketDto ticketDto){
        try {
            logger.info("Attempting to create a new ticket.");
            TicketDto ticket = ticketService.createTicket(ticketDto);
            logger.info("Successfully created a new ticket with ID: " + ticket.getId());
            return new ResponseEntity<TicketDto>(ticket,HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage());
            return new ResponseEntity( new ApiResponse(e.getMessage(),false), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while creating a ticket: " + e.getMessage());
            return new ResponseEntity( new ApiResponse(e.getMessage(),false), HttpStatus.BAD_REQUEST);
        }
    }




    @Operation(summary = "Delete a ticket by ticketId", description = "Returns the deletion status")
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<String> deleteTicket(@PathVariable("ticketId")Long ticketId){
        ticketService.deleteTicket(ticketId);
        return new ResponseEntity(new ApiResponse("Ticket is deleted of ticketId "+ticketId,true),HttpStatus.OK);
    }

    @Operation(summary = "update a ticket by ticketId", description = "Returns the updated ticket object status")
    @PatchMapping("/{ticketId}")
    public ResponseEntity<TicketDto> updateTicket(@PathVariable("ticketId")Long ticketId, @RequestBody TicketDto ticketDto){
        try {
            logger.info("Attempting to update a new project.");
            TicketDto updatedTicketDto = ticketService.updateTicket(ticketId,ticketDto);
            logger.info("Successfully created a new project with ID: " + updatedTicketDto.getId());
            return new ResponseEntity<TicketDto>(updatedTicketDto,HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage());
            return new ResponseEntity( new ApiResponse(e.getMessage(),false), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while creating a ticket: " + e.getMessage());
            return new ResponseEntity( new ApiResponse(e.getMessage(),false), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get a Ticket by TicketId", description = "Returns a Ticket as per the TicketId")
    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDto> getTicketByTicketId(@PathVariable("ticketId")Long ticketId){
        TicketDto ticketDto = ticketService.getTicketByTicketId(ticketId);
        return new ResponseEntity<TicketDto>(ticketDto,HttpStatus.OK);
    }
    @Operation(summary = "Get all Tickets ", description = "Returns all the Tickets ")
    @GetMapping
    public ResponseEntity<List<TicketDto>> getAllTickets(){
        List<TicketDto> allTicketDto = ticketService.getAllTicket();
        if(allTicketDto.isEmpty())
            return new ResponseEntity(new ApiResponse("No Tickets found",false),HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<TicketDto>>(allTicketDto,HttpStatus.OK);
    }



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
