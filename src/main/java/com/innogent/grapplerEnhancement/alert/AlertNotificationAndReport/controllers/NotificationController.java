package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controllers;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Notification;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.AlertDtoInfo;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ApiResponse;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.NotificationDtoForCreate;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.NotificationInfo;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    Logger logger = LoggerFactory.getLogger(AlertController.class);
    @Autowired
    private NotificationService notificationService;



    @Operation(summary = "Create a Notification", description = "Returns created Notification")
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createNotification(@Valid @RequestBody NotificationDtoForCreate notification){
        try {
            logger.info("Attempting to create a new notification.");
            NotificationInfo notificationInfo = notificationService.createNotification(notification);
            logger.info("Successfully created a new notification");
            return new ResponseEntity<>(new ApiResponse<>(notificationInfo,"Notification is created",true),HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage());
            return new ResponseEntity<>(new ApiResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while creating a notification: " + e.getMessage());
            return new ResponseEntity<>(new ApiResponse<>(null, e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Operation(summary = "Mark Notification as read", description = "Returns Notification with read status ")
    @PatchMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<?>> markNotificationAsRead(@PathVariable("notificationId") Long id){
        try{
            logger.info("Attempting to mark as read notification");
            NotificationInfo notificationInfo=notificationService.saveIsRead(id);
            logger.info("Successfully saved marked as read ");
            return new ResponseEntity<>(new ApiResponse<>(notificationInfo,"Notification is marked as read",true),HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            logger.warn(e.getMessage());
            throw e;
        }
        catch (Exception e){
            logger.error("A problem occurred while marking notification as read: " + e.getMessage());
            return new ResponseEntity<>(new ApiResponse<>(null, e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "All list of notification by userId", description = "Returns the list of notification")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> userNotifiation(@PathVariable("userId") Long userId){
        try{
            logger.info("Attempting to get user notifications");
            List<NotificationInfo> notificationList=notificationService.userNotification(userId);
            logger.info("Successfully got notification list ");
            return new ResponseEntity<>(new ApiResponse<>(notificationList,"found all notification",true),HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            logger.warn(e.getMessage());
            throw e;
        }
        catch (Exception e){
            logger.error("A problem occurred while getting lis of notification : " + e.getMessage());
            return new ResponseEntity<>(new ApiResponse<>(null, e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}