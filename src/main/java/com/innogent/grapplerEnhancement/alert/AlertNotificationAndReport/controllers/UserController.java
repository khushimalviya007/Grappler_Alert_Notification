package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controllers;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.User;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ApiResponse;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ProjectDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.UserDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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


    @Operation(summary = "Get List of  User", description = "Returns the List of Users")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        try {
            logger.info("Attempting to retrieve all users.");

            List<UserDto> users = userService.getAllUsers();
            if (!users.isEmpty()) {
                logger.info("Successfully retrieved all users.");
                return ResponseEntity.ok(users);
            } else {
                logger.warn("No users found.");
                String errorMessage = "User data not found because no data is present.";
                return new ResponseEntity(new ApiResponse(errorMessage,false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error occurred while getting all projects: " + e.getMessage(), e);
            return new ResponseEntity(new ApiResponse(e.getMessage(),false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a User by userId", description = "Return the User if exist")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@Valid @PathVariable("userId") Long userId){
        try {
            logger.info("Attempting to retrieve user with ID: " + userId);
            UserDto userDto = this.userService.getUserById(userId);
            logger.info("Successfully retrieved user with ID: " + userId);
            return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            logger.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving user with ID " + userId + ": " + e.getMessage());
            return new ResponseEntity(new ApiResponse(e.getMessage(),false) , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a User", description = "Returns the created User")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        try {
            logger.info("Attempting to create a new User.");
            UserDto createdUserDto = userService.createUser(userDto);
            logger.info("Successfully created a new user with ID: " + createdUserDto.getId());
            return new ResponseEntity(createdUserDto, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage());
            return new ResponseEntity( new ApiResponse(e.getMessage(),false), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while creating a user: " + e.getMessage());
            return new ResponseEntity( new ApiResponse(e.getMessage(),false), HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "Delete a User by userId", description = "Returns the deletion status")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@Valid @PathVariable("userId") Long userId){
        try {
            logger.info("Attempting to delete user with ID: " + userId);
            userService.deleteUser(userId);
            logger.info("Successfully deleted user with ID: " + userId);
            return new ResponseEntity(new ApiResponse("User deleted successfully",true),HttpStatus.OK);
        }catch (ResourceNotFoundException ex){
            logger.warn(ex.getMessage());
            throw ex;
        }catch (Exception e) {
            // Handle other exceptions (e.g., database connection issues, unexpected errors)
            logger.error( e.getMessage());
            return new ResponseEntity(new ApiResponse(e.getMessage(),false),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Updating a User by userId", description = "Returns the updated user")
    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") Long userId, @RequestBody UserDto userDto){
        try {
            logger.info("Attempting to partially update user with ID: " + userId);
            UserDto updatedUserDto = this.userService.updateUser(userId,userDto);
            logger.info("Successfully partially updated user with ID: " + userId);
            return ResponseEntity.ok(updatedUserDto);
        }
        catch (ResourceNotFoundException e){
            logger.warn(e.getMessage());
            throw e;
        }
        catch (Exception e) {
            logger.error("An error occurred while partially updating User with ID " + userId + ": " + e.getMessage());
            return new ResponseEntity(new ApiResponse(e.getMessage(),false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
