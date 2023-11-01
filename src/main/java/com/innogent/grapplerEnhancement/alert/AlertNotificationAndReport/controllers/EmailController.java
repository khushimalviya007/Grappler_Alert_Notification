package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controllers;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Email;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody Email email)
    {
        String status = emailService.sendSimpleMail(email);

        return status;
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
            @RequestBody Email email)
    {
        String status
                = emailService.sendMailWithAttachment(email);

        return status;
    }
}
