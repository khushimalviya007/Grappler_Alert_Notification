package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Email;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Rule;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlertNotiService {

    @Autowired
    private static Logger logger = LoggerFactory.getLogger(AlertNotiService.class);

    @Autowired
    NotificationService notificationService;
    @Autowired
    AlertSevice alertSevice;
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    Email email;
    @Autowired
    UserService userService;

    public void createAlertNoti(TicketDto ticketDto, ProjectDto projectDto, Rule rule) {
        if (rule.getAction().equalsIgnoreCase("Notification")) {

            String[] channel = rule.getChannel().split(",");
            logger.info(rule.getChannel());
            logger.info("working on split");
            for (String s : channel) {

                if (s.equalsIgnoreCase("In_app")) {
                    logger.info("try to add notificaton");
                    NotificationDtoForCreate notiDto = new NotificationDtoForCreate(ticketDto, projectDto, rule);
                    notificationService.createNotification(notiDto);
                    logger.info("created noti");
                }
                if (s.equalsIgnoreCase("Email")) {
                    logger.info("trying to send mail");
                    List<String> recepients = new ArrayList<>();
                    String[] recepient = rule.getRecepient().split(",");
                    for (String string : recepient) {
                        if (string.equalsIgnoreCase("Assigne_To")) {
                            for (UserDto s1 : ticketDto.getAssignees()) {
                                recepients.add(s1.getEmail());
                            }
                        } else if (string.equalsIgnoreCase("Assigned_By")) {
                            recepients.add(String.valueOf(ticketDto.getAssignedBy()));
                        } else if (string.equalsIgnoreCase("Both")) {
                            for (UserDto s1 : ticketDto.getAssignees()) {
                                recepients.add(s1.getEmail());
                            }
                            UserDto user = ticketDto.getAssignedBy();
                            recepients.add(user.getEmail());
                        } else {
                            Long i = Long.parseLong(string);
                            UserDto userDto = userService.getUserById(i);
                            recepients.add(userDto.getEmail());
                        }
                    }
                    email.setRecipient(recepients);
                    email.setSubject(rule.getName());
                    email.setMsgBody(rule.getDescription());
                    System.out.println(recepients);
                    System.out.println(email);
                    emailService.sendSimpleMail(email);
                    logger.info("mail created");
                }
            }
        }

        if (rule.getAction().equalsIgnoreCase("Alert")) {

            String[] channel = rule.getChannel().split(",");
            for (String s : channel) {
                if (s.equalsIgnoreCase("In_app")) {

                    AlertDto alertDto = new AlertDto(ticketDto, projectDto, rule);
                    alertSevice.createAlert(alertDto);
                }

                if (s.equalsIgnoreCase("Email")) {
                    logger.info("trying to send mail");
                    List<String> recepients = new ArrayList<>();
                    String[] recepient = rule.getRecepient().split(",");
                    for (String string : recepient) {
                        if (string.equalsIgnoreCase("Assigne To")) {
                            for (UserDto s1 : ticketDto.getAssignees()) {
                                recepients.add(s1.getEmail());
                            }
                        } else if (string.equalsIgnoreCase("Assigned By")) {
                            recepients.add(String.valueOf(ticketDto.getAssignedBy()));
                        } else if (string.equalsIgnoreCase("Both")) {
                            for (UserDto s1 : ticketDto.getAssignees()) {
                                recepients.add(s1.getEmail());
                            }
                            UserDto user = ticketDto.getAssignedBy();
                            recepients.add(user.getEmail());
                        } else {
                            long i = Long.parseLong(string);
                            UserDto userDto = userService.getUserById(i);
                            recepients.add(userDto.getEmail());
                        }
                    }
                    email.setRecipient(recepients);
                    email.setSubject(rule.getName());
                    email.setMsgBody(rule.getDescription());
                    System.out.println(recepients);
                    System.out.println(email);
                    emailService.sendSimpleMail(email);
                    logger.info("mail created");
                }
            }
        }
    }
}