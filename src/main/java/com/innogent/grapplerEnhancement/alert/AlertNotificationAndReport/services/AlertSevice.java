package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.*;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.AlertDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.AlertDtoInfo;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.AlertResponse;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertSevice {

    @Autowired
    AlertRepositary alertRepositary;

    @Autowired
    NotificationRepositary notificationRepositary;
    @Autowired
    RuleRepositary ruleRepositary;
    @Autowired
    TicketRepositary ticketRepositary;
    @Autowired
    ProjectRepositary projectRepositary;
    @Autowired
    UserRepositary userRepositary;
    @Autowired
    ModelMapper modelMapper;


    public AlertDtoInfo createAlert(AlertDto alertDto) {
        Rule rule = ruleRepositary.findById((long) alertDto.getRule().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Rule", "id", alertDto.getRule().getId()));
        Project project = projectRepositary.findById(alertDto.getProject().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", alertDto.getProject().getId()));

        Alert alert = this.modelMapper.map(alertDto, Alert.class);
        Ticket ticketFinal = null;

        if (alertDto.getTicket() != null) {

            Ticket ticket = ticketRepositary.findById(alertDto.getTicket().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", alertDto.getTicket().getId()));
            ticketFinal = ticket;
            alert.setTicket(ticketFinal);
            alert.setProject(ticketFinal.getProject());

        } else {
            alert.setProject(project);
        }

        String severity = rule.getSeverity();

        if (severity.equalsIgnoreCase("High"))
            alert.setType(AlertType.CRITICAL);
        else if (severity.equalsIgnoreCase("Medium"))
            alert.setType(AlertType.WARNING);
        else
            alert.setType(AlertType.INFORMATIONAL);

        if (ticketFinal == null)
            alert.setDescription(project.getName() + " : " + rule.getDescription());
        else
            alert.setDescription(ticketFinal.getName() + " : " + rule.getDescription());

        alert.setChannels(rule.getChannel());

        // Set the creation_date using the current LocalDateTime formatted as 'YYYY-MM-DD HH:mm:ss'
        alert.setCreationDate(LocalDateTime.now());

        alert.setRule(rule);

        String recepient = rule.getRecepient();

        String[] users = recepient.split(",");
        List<User> userList = new ArrayList<>();
        for (String s : users) {
            if (s.equalsIgnoreCase("ASSIGNE_TO")) {
                if (ticketFinal != null) {
                    ticketFinal.getAssignees().forEach((user) -> {
                        if (!userList.contains(user))
                            userList.add(user);
                    });
                }else if (rule.getEntity().equalsIgnoreCase("PROJECT")) {
                    project.getUsers().forEach((user) -> {
                        if (!userList.contains(user))
                            userList.add(user);
                    });
                }
            } else if (s.equalsIgnoreCase("ASSIGNED_BY")) {
                if (ticketFinal != null) {
                    if (!userList.contains(ticketFinal.getAssignedBy()))
                        userList.add(ticketFinal.getAssignedBy());
                } else if (rule.getEntity().equalsIgnoreCase("PROJECT")) {
                }
            } else if (s.equalsIgnoreCase("BOTH")) {
                if (ticketFinal != null) {
                    ticketFinal.getAssignees().forEach((user) -> {
                        if (!userList.contains(user))
                            userList.add(user);
                    });
                    if (!userList.contains(ticketFinal.getAssignedBy()))
                        userList.add(ticketFinal.getAssignedBy());
                } else if (rule.getEntity().equalsIgnoreCase("PROJECT")) {
                    project.getUsers().forEach((user) -> {
                        if (!userList.contains(user))
                            userList.add(user);
                    });
                }
            } else {
                User byId = userRepositary.findById(Long.valueOf(s))
                        .orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.valueOf(s)));
                userList.add(byId);
            }
        }

        alert.setUserList(userList);
        Alert save = alertRepositary.save(alert);
        return this.modelMapper.map(save, AlertDtoInfo.class);
    }

    public AlertDtoInfo saveResponse(Long alertId, AlertResponse alertResponse) {
        Alert alert = alertRepositary.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alert", "id", alertId));

        alert.setResponse(alertResponse.getResponse());
        alertRepositary.save(alert);
        return modelMapper.map(alert, AlertDtoInfo.class);
    }

    public List<AlertDtoInfo> getUserAlerts(Long userId) {
        List<Alert> alertList = alertRepositary.findByUserList_Id(userId);
        List<AlertDtoInfo> alertDtoInfos = new ArrayList<>();

        alertList.stream().forEach(alert -> {
            alertDtoInfos.add(modelMapper.map(alert, AlertDtoInfo.class));
        });

        return alertDtoInfos;
    }

//    public List<Alert> getUserAlert(Long userId) {
//        return alertRepositary.findAllByUserID(userId);
//    }
}