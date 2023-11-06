package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Email;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService{
        @Autowired
        private JavaMailSender javaMailSender;
        @Value("${spring.mail.username}") private String sender;
        public String sendSimpleMail(Email email)
        {
            try {
                SimpleMailMessage mailMessage  = new SimpleMailMessage();
                mailMessage.setFrom(sender);
//                String[] recipients = email.getRecipient().split(",");
//                mailMessage.setTo(recipients);
//                mailMessage.setText(email.getMsgBody());
//                mailMessage.setSubject(email.getSubject());
                List<String> to=email.getRecipient();
                for (String recipient : to) {
                    mailMessage.setTo(recipient);
                    mailMessage.setText(email.getMsgBody());
                    mailMessage.setSubject(email.getSubject());
                    javaMailSender.send(mailMessage);
                }

                return "Mail Sent Successfully...";
            }
            catch (Exception e) {
                return "Error while Sending Mail";
            }
        }

        // Method 2
        // To send an email with attachment
        public String
        sendMailWithAttachment(Email email)
        {
//            // Creating a mime message
//            MimeMessage mimeMessage
//                    = javaMailSender.createMimeMessage();
//            MimeMessageHelper mimeMessageHelper;
//
//            try {
//
//                // Setting multipart as true for attachments to
//                // be send
//                mimeMessageHelper
//                        = new MimeMessageHelper(mimeMessage, true);
//                mimeMessageHelper.setFrom(sender);
//                mimeMessageHelper.setTo(details.getRecipient());
//                mimeMessageHelper.setText(details.getMsgBody());
//                mimeMessageHelper.setSubject(
//                        details.getSubject());
//
//                // Adding the attachment
//                FileSystemResource file
//                        = new FileSystemResource(
//                        new File(details.getAttachment()));
//
//                mimeMessageHelper.addAttachment(
//                        file.getFilename(), file);
//
//                // Sending the mail
//                javaMailSender.send(mimeMessage);
                return "Mail sent Successfully";
//            }
//
//            // Catch block to handle MessagingException
//            catch (MessagingException e) {
//
//                // Display message when exception occurred
//                return "Error while sending mail!!!";
//            }
        }
    }