package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.*;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.NotificationDtoForCreate;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ProjectDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.RuleDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.TicketDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public NotificationDtoForCreate createNotification(NotificationDtoForCreate notification) {
        Rule ruleById = ruleRepositary.findById((long)notification.getRule().getId()).orElseThrow(()->new ResourceNotFoundException("Rule","id",notification.getRule().getId()));
        Project project = projectRepositary.findById(notification.getProject().getId()).orElseThrow(()->new ResourceNotFoundException("Project","id",notification.getProject().getId()));;
        Optional<Ticket> ticket = ticketRepositary.findById(notification.getTicket().getId());
        Notification note = this.modelMapper.map(notification, Notification.class);
        Ticket ticketFinal=null;
        if(ticket.isPresent()){
            ticketFinal=ticket.get();
            note.setTicket(ticketFinal);
            note.setProject(ticketFinal.getProject());
        }else {
            note.setProject(project);
        }

        note.setRule(ruleById);
        note.setRead(false);

        note.setChannels(ruleById.getChannel());
        note.setDateAndTime(new Date());
        if(ticketFinal==null){

            note.setDescription(project.getName()+" : "+ruleById.getDescription());
        }else {
            note.setDescription(ticketFinal.getName()+" : "+ruleById.getDescription());

        }
        note.setTitle(ruleById.getEntity()+ruleById.getTrigger()+ruleById.getCondition());
        String recepientDescription = ruleById.getRecepient();

        String[] users=recepientDescription.split(",");
        List<User> userList= new ArrayList<>();
        for(String s:users){
            if(s.equalsIgnoreCase("ASSIGNED_TO")){
                if(ruleById.getEntity().equalsIgnoreCase("PROJECT")){
                    project.getUsers().forEach((user)->userList.add(user));
                }else {
                    ticketFinal.getAssignees().forEach((user)->userList.add(user));
                }
            }else if(s.equalsIgnoreCase("ASSIGNED_BY")){
                if(ruleById.getEntity().equalsIgnoreCase("PROJECT")){
                }else {
                    userList.add(ticketFinal.getAssignedBy()) ;
                }
            }else if(s.equalsIgnoreCase("BOTH")){
                if(ruleById.getEntity().equalsIgnoreCase("PROJECT")){
                    project.getUsers().forEach((user)->userList.add(user));
                }else {
                    ticketFinal.getAssignees().forEach((user)->userList.add(user));
                    userList.add(ticketFinal.getAssignedBy()) ;
                }
            }else{
                try {
                    User byId = userRepositary.findById(Long.valueOf(s)).orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.valueOf(s)));
                    userList.add(byId);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        note.setListOfUser(userList);
        Notification save = notificationRepositary.save(note);
        return this.modelMapper.map(save,NotificationDtoForCreate.class);
    }
}