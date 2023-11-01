package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controllers;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ApiResponse;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.LoginDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.UserDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/login")
public class LoginController {
    Logger logger = LoggerFactory.getLogger(AlertController.class);
    @Autowired
    LoginService loginService;
    @Operation(summary = "For login purpose", description = "Returns the user if exist otherwise return null")
    @PostMapping
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginDto loginDto){
        try {
            logger.info("Attempting to login.");
            UserDto userDto = loginService.startLogin(loginDto);
            if(userDto!=null){
                logger.info("Successfully login");
                return  new ResponseEntity<>(new ApiResponse(userDto,"Successfully login",true), HttpStatus.OK);
            }
            else{
                logger.info("Email or password is inCorrect!");
                return  new ResponseEntity<>(new ApiResponse(null,"Email or password is inCorrect!",false), HttpStatus.NOT_FOUND);
            }

        }
        catch (Exception e) {
            logger.error("An error occurred while login : " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity( new ApiResponse(null,e.getMessage(),false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
