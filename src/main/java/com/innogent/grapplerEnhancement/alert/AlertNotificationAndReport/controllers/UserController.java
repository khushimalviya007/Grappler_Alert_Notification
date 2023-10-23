package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controllers;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.User;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ApiResponse;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.UserDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger= LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @Operation(summary = "Create a User", description = "Returns the created User")
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto) {
        try {
            logger.info("Attempting to create a new user");
            return new ResponseEntity(userService.createUser(userDto), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Updating a User by userId", description = "Returns the updated user")
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid  @RequestBody UserDto userDto,@PathVariable Long userId){
        UserDto updatedUserDto = this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUserDto);

    }
    @Operation(summary = "Delete a User by userId", description = "Returns the deletion status")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId")Long userId){
        userService.deleteUser(userId);

        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully",true),HttpStatus.OK);
    }

    @Operation(summary = "Delete a User by userId", description = "Returns the deletion status")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @Operation(summary = "Delete a User by userId", description = "Returns the deletion status")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Long userId){
        return new ResponseEntity<UserDto>(this.userService.getUserById(userId),HttpStatus.OK);
    }
//
//    @Operation(summary = "update a user by userId", description = "Returns the updated User object status")
//    @PatchMapping("/{userId}")
//    public ResponseEntity<User> updateUser(@PathVariable("userId")Long userId, @RequestBody User user){
//        return userService.updateUser(user,userId);
//    }
}
