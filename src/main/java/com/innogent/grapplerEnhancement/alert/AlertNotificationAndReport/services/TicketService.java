package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.*;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.*;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
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
    NotificationService notificationService;


    private  static Logger logger= LoggerFactory.getLogger(TicketService.class);

    @Autowired
    AlertSevice alertSevice;

    @Autowired
    RuleService ruleService;

    @Autowired
    AlertNotiService alertNotiService;

    @Autowired
    NotificationRepositary notificationRepositary;

    @Autowired
    AlertRepositary alertRepositary;

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
        ticket.setCreationDate(LocalDateTime.now()); // Use LocalDateTime
        ticket.setStage(Stages.TODO);
        ticket.setAssignedBy(assignedBy);
        ticket.setAssignees(users);
        ticket.setProject(project);
        System.out.println("khushiiiiiiiiiiiii");
        Ticket savedTicket = ticketRepositary.save(ticket);


        List<Rule> ruleList=ruleService.getRule(Trigger.EVENT,"Global","Project","Ticket","add");
        for(Rule rule:ruleList) {
            alertNotiService.createAlertNoti(modelMapper.map(savedTicket,TicketDto.class), modelMapper.map(savedTicket.getProject(),ProjectDto.class),rule);
        }
        return this.modelMapper.map(savedTicket, TicketDto.class);
    }

    public void deleteTicket(Long ticketId) {
        Ticket ticket = ticketRepositary.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", ticketId));
        logger.info("aatemption t o delt e tifkcsfs");

        List<Rule> ruleList=ruleService.getRule(Trigger.EVENT,"Global","Project","Ticket","Delete");
        logger.info("is rulelist is empty "+ruleList.isEmpty());
        for(Rule rule:ruleList) {
            alertNotiService.createAlertNoti(modelMapper.map(ticket,TicketDto.class), modelMapper.map(ticket.getProject(),ProjectDto.class),rule);
        }
        // Get a list of notifications referencing this ticket
        List<Notification> notifications = notificationRepositary.findByTicketId(ticketId);
        // Remove references to the ticket in notifications
        for (Notification notification : notifications) {
            notification.setTicket(null); // Set the reference to null
            notificationRepositary.save(notification);
        }

        // Get a list of alerts referencing this ticket
        List<Alert> alerts = alertRepositary.findByTicketId(ticketId);
        // Remove references to the ticket in alerts
        for (Alert alert : alerts) {
            alert.setTicket(null); // Set the reference to null
            alertRepositary.save(alert);
        }
        // Now you can safely delete the ticket
        ticketRepositary.deleteById(ticketId);
    }

    public TicketDto updateTicket(Long ticketId, TicketDto ticketDto) {
        Ticket nonUpdatedTicket = ticketRepositary.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", ticketId));
        if (ticketDto.getName() != null) {
            nonUpdatedTicket.setName(ticketDto.getName());
            List<Rule> ruleList=ruleService.getRule(Trigger.EVENT,"Global","Ticket","Name","Update");
            for(Rule rule:ruleList) {
                alertNotiService.createAlertNoti(modelMapper.map(nonUpdatedTicket,TicketDto.class), modelMapper.map(nonUpdatedTicket.getProject(),ProjectDto.class),rule);
            }
        }

        if(ticketDto.getStage() != null) {
            nonUpdatedTicket.setStage(ticketDto.getStage());
            List<Rule> ruleList = ruleService.getRule(Trigger.EVENT, "Global", "Ticket", "Stage", "Update");
            for (Rule rule : ruleList) {
                alertNotiService.createAlertNoti(modelMapper.map(nonUpdatedTicket, TicketDto.class), modelMapper.map(nonUpdatedTicket.getProject(), ProjectDto.class), rule);
            }
        }

        if(ticketDto.getAssignees() != null ) {
            ticketDto.getAssignees().stream().forEach((userDto)->{
                User user = userRepositary.findById(userDto.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userDto.getId()));
                if(!nonUpdatedTicket.getAssignees().contains(user)){
                    nonUpdatedTicket.getAssignees().add(user);
                    List<Rule> ruleList = ruleService.getRule(Trigger.EVENT, "Global", "Ticket", "Assignees", "Update");
                    for (Rule rule : ruleList) {
                        alertNotiService.createAlertNoti(modelMapper.map(nonUpdatedTicket, TicketDto.class), modelMapper.map(nonUpdatedTicket.getProject(), ProjectDto.class), rule);
                    }
                }

            });
        }

        if(ticketDto.getEndDate() != null) {
            nonUpdatedTicket.setEndDate(ticketDto.getEndDate());
            List<Rule> ruleList = ruleService.getRule(Trigger.EVENT, "Global", "Ticket", "End Date", "Update");
            for (Rule rule : ruleList) {
                alertNotiService.createAlertNoti(modelMapper.map(nonUpdatedTicket, TicketDto.class), modelMapper.map(nonUpdatedTicket.getProject(), ProjectDto.class), rule);
            }
        }

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