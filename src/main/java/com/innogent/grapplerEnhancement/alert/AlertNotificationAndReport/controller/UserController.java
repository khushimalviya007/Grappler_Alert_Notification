package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controller;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.User;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Create a User", description = "Returns the created User")
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody  User user) {
        try {
            if (!user.isValid()) {
                throw new IllegalArgumentException("User object is missing required fields.");
            }

            // Save the user to the database using userRepository.save(user) or your data access method

            return new ResponseEntity(userService.createUser(user),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST) ;
        }
    }
    @Operation(summary = "Delete a User by userId", description = "Returns the deletion status")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId")Long userId){
        return userService.deleteUser(userId);
    }

    @Operation(summary = "update a user by userId", description = "Returns the updated User object status")
    @PatchMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId")Long userId, @RequestBody User user){
        return userService.updateUser(userId,user);
    }
}
