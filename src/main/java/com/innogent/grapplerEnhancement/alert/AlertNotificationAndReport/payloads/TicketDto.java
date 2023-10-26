package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Stages;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class TicketDto {
    private Long id;

    @NotBlank(message = "Name field is mandatory")
    private String name;

    private Stages stage;

    @NotNull(message = "Assignees field is mandatory")
    private List<UserDto> assignees;

    @NotNull(message = "Assigned By field is mandatory")
    private UserDto assignedBy;

    private String creationDate;

    private String endDate;
    @NotNull(message = "Project field is mandatory")
    private ProjectDto project;
}
