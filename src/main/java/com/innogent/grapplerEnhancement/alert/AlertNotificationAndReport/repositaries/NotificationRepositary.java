package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepositary extends JpaRepository<Notification,Long> {
}
