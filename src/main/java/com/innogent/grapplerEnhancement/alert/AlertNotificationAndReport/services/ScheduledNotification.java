package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledNotification {
    @Scheduled (cron = "0 */10 * * * *" )
    public void scheduledMethod(){

    }
}
