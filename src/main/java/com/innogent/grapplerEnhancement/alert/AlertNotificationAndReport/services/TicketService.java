package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateTimeKeyDeserializer;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Ticket;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.TicketRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    TicketRepositary ticketRepositary;

    public ResponseEntity<Ticket> createTicket(Ticket ticket) {
        return ResponseEntity.ok(ticketRepositary.save(ticket));
    }

    public ResponseEntity<String> deleteTicket(Long ticketId) {
        ticketRepositary.deleteById(ticketId);
        return ResponseEntity.ok("Ticket is deleted of ticketId "+ticketId);
    }

    public ResponseEntity<Ticket> updateTicket(Long ticketId, Ticket ticket) {
        Optional<Ticket> optionalTicket=ticketRepositary.findById(ticketId);
        return ResponseEntity.ok(ticketRepositary.findById(ticketId).get());
    }


    public ResponseEntity<Object> getTicketByTicketId(Long ticketId) {
        Optional<Ticket> optionalProject=ticketRepositary.findById(ticketId);
        if(optionalProject.isPresent())
            return ResponseEntity.ok(ticketRepositary.findById(ticketId));
        else
            return ResponseEntity.ok("No Ticket exist of ticketId : "+ticketId);
    }

}
