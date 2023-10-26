package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Rule;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

public class NotificationDtoForCreate {
    private Long id;
    private TicketDto ticketDto;
    @NotNull(message = "Project field is mandatory")
    private ProjectDto projectDto;
    @NotNull(message = "Rule field is mandatory")
    private Rule rule;
}
