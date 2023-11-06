package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Rule;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class NotificationDtoForCreate {

    private TicketDto ticket;

    @NotNull(message = "Project field is mandatory")
    private ProjectDto project;

    @NotNull(message = "Rule field is mandatory")
    private Rule rule;
}