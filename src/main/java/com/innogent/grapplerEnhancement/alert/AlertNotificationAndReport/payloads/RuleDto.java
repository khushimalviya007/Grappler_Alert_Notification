package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Sources;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class RuleDto {

    private int id;

//    @NotEmpty(message = "Source is mandatory field")
    private Sources sources=Sources.EVENT;

    @NotBlank(message = "Scope is mandatory field")
    private String scope;

    @NotBlank(message = "identity is mandatory field")
    private String identity;

    @NotBlank(message = "trigger is mandatory field")
    private String trigger;

    @NotBlank(message = "condition is mandatory field")
    private String condition;

    @NotBlank(message = "action is mandatory field")
    private String action;

    @NotBlank(message = "description is mandatory field")
    private String description;

    @NotBlank(message = "severity is mandatory field")
    private String severity;

    @NotBlank(message = "Recepient Description is mandatory field")
    private String recepientDescription;

    @NotBlank(message = "channel is mandatory field")
    private String channel;

//    @Column(columnDefinition = "boolean default true")
    private Boolean isEnabled=true;
}





