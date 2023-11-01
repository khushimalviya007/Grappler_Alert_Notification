package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Email;

public interface EmailService {
        String sendSimpleMail(Email email);
        String sendMailWithAttachment(Email email);
}
