package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepositary extends JpaRepository<Notification,Long> {
    public List<Notification> findAllByUserList_IdOrderByCreationDateDesc(Long userId);

    List<Notification> findByTicketId(Long ticketId);
}