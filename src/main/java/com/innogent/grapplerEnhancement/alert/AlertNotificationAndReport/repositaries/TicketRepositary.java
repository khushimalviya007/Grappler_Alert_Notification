package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepositary extends JpaRepository<Ticket,Long> {
}
