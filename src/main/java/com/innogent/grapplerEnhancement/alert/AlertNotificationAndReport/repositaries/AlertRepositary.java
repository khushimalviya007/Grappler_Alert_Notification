package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepositary extends JpaRepository<Alert,Long> {
    List<Alert> findByTicketId(Long ticketId);

    List<Alert> findByUserList_Id(Long userId);
}