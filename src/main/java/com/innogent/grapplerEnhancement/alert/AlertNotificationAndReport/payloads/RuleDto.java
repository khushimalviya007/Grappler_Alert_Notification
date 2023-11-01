package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Trigger;
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

    @NotBlank(message = "name  is mandatory field")
    private String name;

//    @NotEmpty(message = "Trigger is mandatory field")
    private Trigger trigger=Trigger.EVENT;

    @NotBlank(message = "Scope is mandatory field")
    private String scope;

    @NotBlank(message = "entity is mandatory field")
    private String entity;

    @NotBlank(message = "Filed is mandatory field")
    private String field;

    @NotBlank(message = "condition is mandatory field")
    private String condition;

    @NotBlank(message = "action is mandatory field")
    private String action;

    @NotBlank(message = "description is mandatory field")
    private String description;

    @NotBlank(message = "severity is mandatory field")
    private String severity;

    @NotBlank(message = "Recepient Description is mandatory field")
    private String recepient;

    @NotBlank(message = "channel is mandatory field")
    private String channel;

    //    @Column(columnDefinition = "boolean default true")
    private Boolean isEnabled=true;
}