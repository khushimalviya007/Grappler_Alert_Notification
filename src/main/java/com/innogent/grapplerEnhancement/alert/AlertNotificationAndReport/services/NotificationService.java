package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.*;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.*;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    Logger logger= LoggerFactory.getLogger(NotificationService.class);


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
            if(s.equalsIgnoreCase("Assigne_To")){
                if(ticketFinal!=null){
                    ticketFinal.getAssignees().forEach((user)->{
                        if (!userList.contains(user))
                            userList.add(user);
                    });
                } else if(rule.getEntity().equalsIgnoreCase("PROJECT")){
                    project.getUsers().forEach((user)-> {
                                if (!userList.contains(user))
                                    userList.add(user);
                            }
                    );
                }
            }else if(s.equalsIgnoreCase("Assigned_By")){
                if(ticketFinal!=null){
                    if (!userList.contains(ticketFinal.getAssignedBy()))
                        userList.add(ticketFinal.getAssignedBy());
                }
                else if(rule.getEntity().equalsIgnoreCase("PROJECT")){
                }
            }else if(s.equalsIgnoreCase("Both")){
                if(ticketFinal!=null){
                    ticketFinal.getAssignees().forEach((user)->{
                        if (!userList.contains(user))
                            userList.add(user);
                    });
                    if (!userList.contains(ticketFinal.getAssignedBy()))
                        userList.add(ticketFinal.getAssignedBy());
                }
                else  if(rule.getEntity().equalsIgnoreCase("PROJECT")){
                    project.getUsers().forEach((user)->{
                        if (!userList.contains(user))
                            userList.add(user);
                    });
                }
            }
            else{
                System.out.println("inside notification service" + s);
                User user = userRepositary.findById(Long.valueOf(s)).orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.valueOf(s)));
                if (!userList.contains(user))
                    userList.add(user);
            }
        }

        note.setUserList(userList);
        logger.info("userList is "+userList.isEmpty());

        Notification save = notificationRepositary.save(note);
        messagingTemplate.convertAndSend("/topic/notifications", note);

        return this.modelMapper.map(save, NotificationInfo.class);
    }

//    public NotificationInfo saveIsRead(Long notificationId) {
//        Notification notification = notificationRepositary.findById(notificationId)
//                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", notificationId));
//        notification.setRead(true);
//        Notification save=notificationRepositary.save(notification);
//        return modelMapper.map(save, NotificationInfo.class);
//    }

    public NotificationInfo saveIsRead(Long notificationId) {

        Notification notification = notificationRepositary.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", notificationId));
        notification.setRead(true);
        Notification save=notificationRepositary.save(notification);
        return modelMapper.map(save, NotificationInfo.class);
    }


    public List<NotificationInfo> userNotification(Long userId) {
//        User user=userRepositary.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User","id",userId));

        List<Notification> notificationList=notificationRepositary.findAllByUserList_IdOrderByCreationDateDesc(userId);
        List<NotificationInfo> notificationInfoList= notificationList.stream().map(notification -> modelMapper.map(notification,NotificationInfo.class)).toList();
        return notificationInfoList;
    }
}