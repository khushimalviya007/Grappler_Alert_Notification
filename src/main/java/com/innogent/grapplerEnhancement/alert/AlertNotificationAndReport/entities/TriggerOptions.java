package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class TriggerOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private String entity;

    @Column(nullable = false)
    private String field;

    @Column(nullable = false)
    private String triggerCondition;

}
