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
    @Column(nullable = false)

    private String name;

    @Column(nullable = false,name="trigger_type")
    private Trigger trigger=Trigger.EVENT;

    @Column(nullable = false)
    private String scope;

    @Column(nullable = false)
    private String entity;

    @Column(nullable = false ,name="field")
    private String field;

    @Column(nullable = false ,name = "field_condition")
    private String condition;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private String desription;

    @Column(nullable = false)
    private String severity;

    @Column(nullable = false)
    private String recepient;

    @Column(nullable = false)
    private String channel;

    @Column(nullable = false)
    private String creationDate;


    @Column(columnDefinition = "boolean default true")
    private Boolean isEnabled;


    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted=false;

}