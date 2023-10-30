package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controllers;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Notification;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.NotificationDtoForCreate;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private NotificationService notificationService;

//  Retrieve a list of notifications for a user by UserId
//  public ResponseEntity<List<Notification>> getUserNotifications(@RequestParam Long userId)
    @Operation(summary = "Get List of Notification by UserId", description = "Returns List of Notification as per the UserId")
    @GetMapping("/{userId}/notification")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable("userId")Long userId){
//        return new ResponseEntity<>()"We are getting notification by userId : "+userId;
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

//  Retrieve a list of notifications for a Ticket by TicketId
//  public ResponseEntity<List<Notification>> getTicketNotifications(@PathVariable("ticketId")Long ticketId)
    @Operation(summary = "Get a List of Notification by TicketId", description = "Returns a List of Notificaion as per the TicketId")
    @GetMapping("/{ticketId}/notification")
    public ResponseEntity<List<Notification>> getTicketNotifications(@PathVariable("ticketId")Long ticketId){
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }


//    POST /api/notifications: Create a new notification.
    @Operation(summary = "Create a Notification", description = "Returns created Notification")
    @PostMapping("/notification")
  //public Notification createNotification( @RequestBody Notification notification)
    public ResponseEntity<NotificationDtoForCreate> createNotification(NotificationDtoForCreate notification){
        notificationService.createNotification(notification);
        return ResponseEntity.ok(notification);
    }


//    PUT /api/notifications/{id}: Mark a notification as read.
    @Operation(summary = "Mark Notification as read", description = "Returns Notification with readed ")
    @PatchMapping("/notification/{id}")
//  public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long id)
    public ResponseEntity<String> markNotificationAsRead(@PathVariable("id") Long id){
        return ResponseEntity.ok("Notification is marked as read");
    }

//    @GetMapping("/notification")
////  public List<Notification> getAllNotifications()
//    public String getAllNotifications(){
//        return "all notification";
//    }

}