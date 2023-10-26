package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Alert;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.AlertDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.AlertRepositary;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.TicketRepositary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertSevice {

    @Autowired
    AlertRepositary alertRepositary;

    @Autowired
    ModelMapper modelMapper;
    public AlertDto createAlert(AlertDto alertDto) {
        Alert alert = this.modelMapper.map(alertDto, Alert.class);

        Alert savedAlert=alertRepositary.save(alert);
        return this.modelMapper.map(savedAlert, AlertDto.class);

//        User assignedBy = alertRepositary.findById(ticketDto.getAssignedBy().getId())
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", ticketDto.getAssignedBy().getId()));
//        Project project = projectRepositary.findById(ticketDto.getProject().getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", ticketDto.getProject().getId()));


//        List<User> users= new ArrayList<>();
//        for (UserDto userDto : ticketDto.getAssignees()) {
//            User user = userRepositary.findById(userDto.getId())
//                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userDto.getId()));
//            users.add(user);
//        }
//        ticket.setCreationDate(new Date().toString());
//        ticket.setStage(Stages.TODO);
//        ticket.setAssignedBy(assignedBy);
//        ticket.setAssignees(users);
//        ticket.setProject(project);
//        Ticket savedTicket = ticketRepositary.save(ticket);

    }
}
