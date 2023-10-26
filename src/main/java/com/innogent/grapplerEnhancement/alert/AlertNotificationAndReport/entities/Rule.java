package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private Sources sources=Sources.EVENT;

    @Column(nullable = false)
    private String scope;

    @Column(nullable = false)
    private String identity;

    @Column(nullable = false ,name="trigger_field")
    private String trigger;

    @Column(nullable = false ,name = "trigger_condition")
    private String condition;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private String desription;

    @Column(nullable = false)
    private String severity;

    @Column(nullable = false)
    private String recepientDescription;

    @Column(nullable = false)
    private String channel;

    @Column(nullable = false)
    private String creationDate;


    @Column(columnDefinition = "boolean default true")
    private Boolean isEnabled;


    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted=false;

}
