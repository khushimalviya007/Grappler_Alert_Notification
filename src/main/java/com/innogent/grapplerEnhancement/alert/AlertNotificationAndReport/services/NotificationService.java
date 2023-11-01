package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.*;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.*;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
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


    /**
     * takes ticketDto
     * projectDto and rule dto
     * as input
     * @return Notification
     * */
    public NotificationInfo createNotification(NotificationDtoForCreate notification) {
        Rule rule = ruleRepositary.findById((long)notification.getRule().getId()).orElseThrow(()->new ResourceNotFoundException("Rule","id",notification.getRule().getId()));
        Project project = projectRepositary.findById(notification.getProject().getId()).orElseThrow(()->new ResourceNotFoundException("Project","id",notification.getProject().getId()));;
        Notification note = this.modelMapper.map(notification, Notification.class);

        Ticket ticketFinal=null;

        if (notification.getTicket() != null) {
            Ticket ticket = ticketRepositary.findById(notification.getTicket().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", notification.getTicket().getId()));
            ticketFinal = ticket;
            note.setTicket(ticketFinal);
            note.setProject(ticketFinal.getProject());
        } else {
            note.setProject(project);
        }

        note.setTitle(rule.getEntity()+" "+rule.getTrigger()+" "+rule.getCondition());
        if(ticketFinal==null)
            note.setDescription(project.getName()+" : "+rule.getDescription());
        else
            note.setDescription(ticketFinal.getName()+" : "+rule.getDescription());

        note.setChannels(rule.getChannel());
        note.setCreationDate(LocalDateTime.now());
        note.setRead(false);
        note.setRule(rule);

        String recepient = rule.getRecepient();

        String[] users=recepient.split(",");
        List<User> userList= new ArrayList<>();
        for(String s:users){
            if(s.equalsIgnoreCase("ASSIGNED_TO")){
                if(rule.getEntity().equalsIgnoreCase("PROJECT")){
                    project.getUsers().forEach((user)->userList.add(user));
                }else if(ticketFinal!=null){
                    ticketFinal.getAssignees().forEach((user)->userList.add(user));
                }
            }else if(s.equalsIgnoreCase("ASSIGNED_BY")){
                if(rule.getEntity().equalsIgnoreCase("PROJECT")){
                }else if(ticketFinal!=null){
                    userList.add(ticketFinal.getAssignedBy()) ;
                }
            }else if(s.equalsIgnoreCase("BOTH")){
                if(rule.getEntity().equalsIgnoreCase("PROJECT")){
                    project.getUsers().forEach((user)->userList.add(user));
                }else if(ticketFinal!=null){
                    ticketFinal.getAssignees().forEach((user)->userList.add(user));
                    userList.add(ticketFinal.getAssignedBy()) ;
                }
            }else{
                    User byId = userRepositary.findById(Long.valueOf(s)).orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.valueOf(s)));
                    userList.add(byId);
            }
        }
        note.setUserList(userList);
        Notification save = notificationRepositary.save(note);
        return this.modelMapper.map(save, NotificationInfo.class);
    }

    public NotificationInfo saveIsRead(Long notificationId) {
        Notification notification = notificationRepositary.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", notificationId));
        notification.setRead(true);
        Notification save=notificationRepositary.save(notification);
        return modelMapper.map(save, NotificationInfo.class);
    }


}