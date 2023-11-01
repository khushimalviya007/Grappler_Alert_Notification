package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class AlertDto {

    @NotNull(message = "type is mandatory field")
    private AlertType type;

    @NotBlank(message = "description is mandatory field")
    private String description;

    @NotBlank(message = "channels is mandatory field")
    private String channels;

    @NotEmpty(message = "users is mandatory field")
    private List<User> users;

    @NotNull(message = "ticket is mandatory field")
    private Ticket ticket;

    @NotNull(message = "project is mandatory field")
    private Project project;

    @NotNull(message = "template is mandatory field")
    private Template template;

    @NotNull(message = "rule is mandatory field")
    private Rule rule;
}
