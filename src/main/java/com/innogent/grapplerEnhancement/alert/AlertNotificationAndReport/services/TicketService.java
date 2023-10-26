package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Stages;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Ticket;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.User;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ApiResponse;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.TicketDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.UserDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.ProjectRepositary;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.TicketRepositary;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.UserRepositary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    TicketRepositary ticketRepositary;
    @Autowired
    UserRepositary userRepositary;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ProjectRepositary projectRepositary;
    public TicketDto createTicket(TicketDto ticketDto)  {

        User assignedBy = userRepositary.findById(ticketDto.getAssignedBy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", ticketDto.getAssignedBy().getId()));
        Project project = projectRepositary.findById(ticketDto.getProject().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", ticketDto.getProject().getId()));

        Ticket ticket = this.modelMapper.map(ticketDto, Ticket.class);
        List<User> users= new ArrayList<>();
        for (UserDto userDto : ticketDto.getAssignees()) {
            User user = userRepositary.findById(userDto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userDto.getId()));
            users.add(user);
        }
        ticket.setCreationDate(new Date().toString());
        ticket.setStage(Stages.TODO);
        ticket.setAssignedBy(assignedBy);
        ticket.setAssignees(users);
        ticket.setProject(project);
        Ticket savedTicket = ticketRepositary.save(ticket);
        return this.modelMapper.map(savedTicket, TicketDto.class);
    }

    public void deleteTicket(Long ticketId) {
        Ticket ticket = ticketRepositary.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", ticketId));
        ticketRepositary.delete(ticket);
    }

    public TicketDto updateTicket(Long ticketId, TicketDto ticketDto) {
        Ticket nonUpdatedTicket = ticketRepositary.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", ticketId));
        if (ticketDto.getName() != null)
            nonUpdatedTicket.setName(ticketDto.getName());

        if(ticketDto.getStage() != null)
            nonUpdatedTicket.setStage(ticketDto.getStage());

        if(ticketDto.getAssignees() != null ) {
            ticketDto.getAssignees().stream().forEach((userDto)->{
                User user = userRepositary.findById(userDto.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userDto.getId()));
                if(!nonUpdatedTicket.getAssignees().contains(user)){
                    nonUpdatedTicket.getAssignees().add(user);
                }

            });
        }

        if(ticketDto.getEndDate() != null)
            nonUpdatedTicket.setEndDate(ticketDto.getEndDate());

        Ticket savedTicket = ticketRepositary.save(nonUpdatedTicket);

        return this.modelMapper.map(savedTicket, TicketDto.class);

    }

    public TicketDto getTicketByTicketId(Long ticketId) {
        Ticket ticket = ticketRepositary.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", ticketId));
        return this.modelMapper.map(ticket,TicketDto.class);
    }


    public List<TicketDto> getAllTicket() {
        List<Ticket> all = ticketRepositary.findAll();
        return all.stream().map((ticket)->this.modelMapper.map(ticket,TicketDto.class)).collect(Collectors.toList());
    }
}
