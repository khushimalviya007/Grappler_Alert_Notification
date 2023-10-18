package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controller;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Notification;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NotificationController {


//  Retrieve a list of notifications for a user by UserId
//  public ResponseEntity<List<Notification>> getUserNotifications(@RequestParam Long userId)
    @Operation(summary = "Get List of Notification by UserId", description = "Returns List of Notification as per the UserId")
    @GetMapping("/notification/user/{userId}")
    public String getUserNotifications(@PathVariable("userId")Long userId){
        return "We are getting notification by userId : "+userId;
    }

//  Retrieve a list of notifications for a Ticket by TicketId
//  public ResponseEntity<List<Notification>> getTicketNotifications(@PathVariable("ticketId")Long ticketId)
    @Operation(summary = "Get a List of Notification by TicketId", description = "Returns a List of Notificaion as per the TicketId")
    @GetMapping("/notification/ticket/{ticketId}")
    public String getTicketNotifications(@PathVariable("ticketId")Long ticketId){
        return "We are getting notification by ticketId : "+ticketId;
    }


//    POST /api/notifications: Create a new notification.
    @Operation(summary = "Create a Notification", description = "Returns created Notification")
    @PostMapping("/nofification/create")
  //public Notification createNotification( @RequestBody Notification notification)
    public Notification createNotification(Notification notification){
        return notification;
    }


//    PUT /api/notifications/{id}: Mark a notification as read.
    @Operation(summary = "Mark Notification as read", description = "Returns Notification with readed ")
    @PutMapping("/notification/{id}")
//  public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long id)
    public String markNotificationAsRead(@PathVariable("id") Long id){
        return "Notification is marked as read";
    }

//    @GetMapping("/notification")
////  public List<Notification> getAllNotifications()
//    public String getAllNotifications(){
//        return "all notification";
//    }

}
