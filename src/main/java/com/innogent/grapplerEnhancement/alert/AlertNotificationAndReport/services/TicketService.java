package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Stages;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Ticket;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.User;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.TicketDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.UserDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.TicketRepositary;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.UserRepositary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    TicketRepositary ticketRepositary;
    @Autowired
    UserRepositary userRepositary;
    @Autowired
    ModelMapper modelMapper;

    public TicketDto createTicket(TicketDto ticketDto)  {

        User assignedBy = userRepositary.findById(ticketDto.getAssignedBy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", ticketDto.getAssignedBy().getId()));
        Ticket ticket = this.modelMapper.map(ticketDto, Ticket.class);
        for (UserDto userDto : ticketDto.getAssignees()) {
            User user = userRepositary.findById(userDto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userDto.getId()));
            ticket.getAssignees().add(user);
        }
        ticket.setCreationDate(new Date().toString());
        ticket.setStage(Stages.TODO);
        ticket.setAssigneedBy(assignedBy);
        Ticket savedTicket = ticketRepositary.save(ticket);
        return this.modelMapper.map(savedTicket, TicketDto.class);
    }

    public ResponseEntity<String> deleteTicket(Long ticketId) {
        ticketRepositary.deleteById(ticketId);
        return ResponseEntity.ok("Ticket is deleted of ticketId "+ticketId);
    }

    public ResponseEntity<Ticket> updateTicket(Long ticketId, Ticket ticket) {
        Optional<Ticket> optionalTicket=ticketRepositary.findById(ticketId);
        return ResponseEntity.ok(ticketRepositary.findById(ticketId).get());

    }


//    public ResponseEntity<Object> getTicketByTicketId(Long ticketId) {
//        Optional<Ticket> optionalProject=ticketRepositary.findById(ticketId);
//        if(optionalProject.isPresent())
//            return ResponseEntity.ok(ticketRepositary.findById(ticketId));
//        else
//            return ResponseEntity.ok("No Ticket exist of ticketId : "+ticketId);
//    }




}
